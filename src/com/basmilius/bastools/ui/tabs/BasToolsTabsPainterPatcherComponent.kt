package com.basmilius.bastools.ui.tabs

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

/**
 * Class BasToolsTabsPainterPatcherComponent
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.tabs
 * @since 1.0.0
 */
class BasToolsTabsPainterPatcherComponent: ApplicationComponent, FileEditorManagerListener
{

	/**
	 * Companion Object BasToolsTabsPainterPatcherComponent
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.ui.tabs
	 * @since 1.0.0
	 */
	companion object
	{

		val HighlightColor = JBColor(Color(120, 147, 105), Color(120, 147, 105))
		const val HighlightThickness = 2
		val BackgroundColor = JBColor(Color(45, 49, 53), Color(45, 49, 53))

	}

	private var connection: MessageBusConnection? = null

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun initComponent()
	{
		this.connection = ApplicationManagerEx.getApplicationEx().messageBus.connect()
		this.connection!!.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this)
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun disposeComponent()
	{
		this.connection!!.disconnect()
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
	 * @param component Tabs component.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
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
	 * @param objects Array<Any>
	 * @param borderColor Color
	 * @param borderThickness Int
	 * @param painter BasToolsTabsPainter
	 *
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	@Throws(ClassNotFoundException::class, NoSuchFieldException::class, IllegalAccessException::class)
	private fun paintSelectionAndBorder(objects: Array<Any>, borderColor: Color, borderThickness: Int, painter: BasToolsTabsPainter)
	{
		val g = objects[0] as Graphics2D
		val rect = objects[1] as Rectangle
		val tabColor = objects[4] as? Color ?: return

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

		if (position == null)
			return

		when (position)
		{
			JBTabsPosition.bottom -> g.fillRect(rect.x, rect.y, rect.width, borderThickness)
			JBTabsPosition.top -> g.fillRect(rect.x, rect.y + rect.height - borderThickness, rect.width, borderThickness)
			JBTabsPosition.left -> g.fillRect(rect.x, rect.y, borderThickness, rect.height)
			JBTabsPosition.right -> g.fillRect(rect.x + rect.width - borderThickness, rect.y, borderThickness, rect.height)
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
