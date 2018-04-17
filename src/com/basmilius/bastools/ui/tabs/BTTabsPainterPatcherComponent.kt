/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.tabs

import com.basmilius.bastools.core.util.ReflectionUtils
import com.intellij.openapi.application.ex.ApplicationManagerEx
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.ui.tabs.JBTabsPosition
import com.intellij.ui.tabs.TabInfo
import com.intellij.ui.tabs.impl.JBEditorTabs
import com.intellij.ui.tabs.impl.JBEditorTabsPainter
import com.intellij.util.ReflectionUtil
import com.intellij.util.messages.MessageBusConnection
import com.intellij.util.ui.UIUtil
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.expr.ExprEditor
import javassist.expr.MethodCall
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import java.awt.Component
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Class BTTabsPainterPatcherComponent
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.tabs
 * @since 1.0.0
 */
class BTTabsPainterPatcherComponent: ApplicationComponent, FileEditorManagerListener
{

	private lateinit var connection: MessageBusConnection

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun initComponent()
	{
		this.connection = ApplicationManagerEx.getApplicationEx().messageBus.connect()
		this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this)

		try
		{
			val classPool = ClassPool(true)
			classPool.insertClassPath(ClassClassPath(TabInfo::class.java))

			val classTabLabel = classPool.get("com.intellij.ui.tabs.impl.TabLabel")

			val createLabelMethod = classTabLabel.getDeclaredMethod("createLabel")
			val getInsetsMethod = classTabLabel.getDeclaredMethod("getInsets")
			val getPreferredSizeMethod = classTabLabel.getDeclaredMethod("getPreferredSize")

			createLabelMethod.instrument(object: ExprEditor()
			{

				override fun edit(call: MethodCall)
				{
					val methodCall = "${call.className}::${call.methodName}"

					when(methodCall)
					{
						"com.intellij.util.ui.JBUI::emptyInsets" -> call.replace("{ \$_ = com.intellij.util.ui.JBUI.insets(0, 3, 5, 3); }")
						"com.intellij.ui.SimpleColoredComponent::setIconTextGap" -> call.replace("{ \$1 = com.intellij.util.ui.JBUI.scale(9); \$_ = \$proceed($$); }")
					}
				}

			})

			getInsetsMethod.setBody("{ return com.intellij.util.ui.JBUI.insets(0, 6); }")

			getPreferredSizeMethod.instrument(object: ExprEditor()
			{

				override fun edit(call: MethodCall)
				{
					val methodCall = "${call.className}::${call.methodName}"

					when(methodCall)
					{
						"com.intellij.ui.tabs.TabsUtil::getTabsHeight" -> call.replace("{ \$_ = com.intellij.util.ui.JBUI.scale(30); }")
					}
				}

			})

			classTabLabel.toClass()
		}
		catch (err: Exception)
		{
			err.printStackTrace()
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun disposeComponent()
	{
		this.connection.disconnect()
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun selectionChanged(event: FileEditorManagerEvent)
	{
		val editor = event.newEditor ?: return

		var component: Component? = editor.component

		while (component != null)
		{
			if (component is JBEditorTabs)
			{
				this.patchPainter(component)
				return
			}

			component = component.parent
		}
	}

	/**
	 * Patches the painter using reflection.
	 *
	 * @param tabs Tabs component.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun patchPainter(tabs: JBEditorTabs)
	{
		val painter = ReflectionUtil.getField(JBEditorTabs::class.java, tabs, JBEditorTabsPainter::class.java, "myDarkPainter")

		if (painter is BTTabsPainter)
			return

		val tabsPainter = BTTabsPainter(tabs)
		val proxy = Enhancer.create(BTTabsPainter::class.java, MethodInterceptor { _, method, objects, _ ->
			var result = method.invoke(tabsPainter, *objects)

			if (method.name == "paintSelectionAndBorder")
				this@BTTabsPainterPatcherComponent.paintSelectionAndBorder(objects, tabsPainter)
			else if (method.name == "getEmptySpaceColor")
				result = UIUtil.getPanelBackground()

			result
		}) as BTTabsPainter

		ReflectionUtils.setField(JBEditorTabs::class, tabs, JBEditorTabsPainter::class, "myDarkPainter", proxy)
	}

	/**
	 * Paints the selection and border of a task.
	 *
	 * @param objects Array<Any>
	 * @param painter BTTabsPainter
	 *
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	@Throws(ClassNotFoundException::class, NoSuchFieldException::class, IllegalAccessException::class)
	private fun paintSelectionAndBorder(objects: Array<Any>, painter: BTTabsPainter)
	{
		val g = objects[0] as Graphics2D
		val rect = objects[1] as Rectangle

		val tabsComponent = painter.tabsComponent
		val position = tabsComponent.tabsPosition

		tabsComponent.border = null
		tabsComponent.jbTabs.presentation.setPaintBorder(0, 0, 0, 0)

		val x = rect.x
		val y = rect.y
		val height = rect.height

		painter.fillSelectionAndBorder(g, x, y, rect.width, height)

		if (position == null)
			return

		when (position)
		{
			JBTabsPosition.bottom -> g.fillRect(rect.x, rect.y, rect.width, 0)
			JBTabsPosition.top -> g.fillRect(rect.x, rect.y + rect.height, rect.width, 0)
			JBTabsPosition.left -> g.fillRect(rect.x, rect.y, 0, rect.height)
			JBTabsPosition.right -> g.fillRect(rect.x + rect.width, rect.y, 0, rect.height)
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getComponentName() = "Bas Tools: Tabs UI Patcher"

}
