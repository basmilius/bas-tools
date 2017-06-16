package com.basmilius.ps.bastools.ui.tabs

import com.intellij.openapi.application.ex.ApplicationManagerEx
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.ui.JBColor
import com.intellij.ui.tabs.JBTabsPosition
import com.intellij.ui.tabs.impl.JBEditorTabs
import com.intellij.ui.tabs.impl.JBEditorTabsPainter
import com.intellij.util.ReflectionUtil
import com.intellij.util.messages.MessageBusConnection
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import java.awt.*

class BasToolsTabsPainterPatcherComponent : ApplicationComponent, FileEditorManagerListener
{

	companion object
	{

		val HighlightColor = JBColor(Color(105, 203, 156, 255), Color(105, 203, 156, 255))
		val HighlightThickness = 2
		val BackgroundColor = JBColor(Color(45, 49, 53, 255), Color(45, 49, 53, 255))

	}

	private var connection: MessageBusConnection? = null

	/**
	 * {@inheritDoc}
	 */
	override fun initComponent()
	{
		this.connection = ApplicationManagerEx.getApplicationEx().messageBus.connect()
		this.connection!!.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this)
	}

	/**
	 * {@inheritDoc}
	 */
	override fun disposeComponent()
	{
		this.connection!!.disconnect()
	}

	/**
	 * {@inheritDoc}
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
	 * @param component Tabs component.
	 */
	private fun patchPainter(component: JBEditorTabs)
	{
		val painter = ReflectionUtil.getField(JBEditorTabs::class.java, component, JBEditorTabsPainter::class.java, "myDarkPainter")

		if (painter is BasToolsTabsPainter)
			return

		val tabsPainter = BasToolsTabsPainter(component)
		val proxy = Enhancer.create(BasToolsTabsPainter::class.java, MethodInterceptor { _, method, objects, _ ->
			val result = method.invoke(tabsPainter, *objects)

			if (method.name == "paintSelectionAndBorder")
			{
				this@BasToolsTabsPainterPatcherComponent.paintSelectionAndBorder(objects, HighlightColor, HighlightThickness, tabsPainter)
			}

			result
		}) as BasToolsTabsPainter

		ReflectionUtil.setField(JBEditorTabs::class.java, component, JBEditorTabsPainter::class.java, "myDarkPainter", proxy)
	}

	/**
	 * Paints the selection and border of a task.
	 *
	 * @param objects         Method parameters.
	 * @param borderColor     Highlight color.
	 * @param borderThickness Highlight thickness.
	 * @param painter         Tab painter.
	 *
	 * @throws ClassNotFoundException Exception.
	 * @throws NoSuchFieldException   Exception.
	 * @throws IllegalAccessException Exception.
	 */
	@Throws(ClassNotFoundException::class, NoSuchFieldException::class, IllegalAccessException::class)
	private fun paintSelectionAndBorder(objects: Array<Any>, borderColor: Color, borderThickness: Int, painter: BasToolsTabsPainter)
	{
		val g = objects[0] as Graphics2D
		val rect = objects[1] as Rectangle
		val tabColor = objects[4] as Color

		val tabsComponent = painter.tabsComponent
		val position = tabsComponent.tabsPosition

		tabsComponent.border = null
		tabsComponent.jbTabs.presentation.setPaintBorder(0, 0, 0, 0)

		val x = rect.x
		val y = rect.y
		val height = rect.height

		painter.fillSelectionAndBorder(g, tabColor, x, y, rect.width, height)
		g.color = borderColor
		g.stroke = BasicStroke(0f)

		if (position == JBTabsPosition.bottom)
		{
			g.fillRect(rect.x, rect.y, rect.width, borderThickness)
		}
		else if (position == JBTabsPosition.top)
		{
			g.fillRect(rect.x, rect.y + rect.height - borderThickness, rect.width, borderThickness)
		}
		else if (position == JBTabsPosition.left)
		{
			g.fillRect(rect.x, rect.y, borderThickness, rect.height)
		}
		else if (position == JBTabsPosition.right)
		{
			g.fillRect(rect.x + rect.width - borderThickness, rect.y, borderThickness, rect.height)
		}
	}

}
