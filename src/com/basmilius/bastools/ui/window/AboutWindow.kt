package com.basmilius.bastools.ui.window

import com.intellij.ui.ColorUtil
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.panels.HorizontalBox
import com.intellij.ui.components.panels.VerticalBox
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import com.intellij.util.ui.JBUI
import java.awt.Font
import javax.swing.JFrame
import javax.swing.Spring
import javax.swing.SpringLayout

/**
 * Class AboutWindow
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.window
 * @since 1.4.0
 */
class AboutWindow: JFrame("About Bas Tools")
{

	init
	{
		val root = HorizontalBox()
		root.border = JBEmptyBorder(24, 24, 24, 24)
		root.size = JBDimension(548, 390)

		val leftPane = VerticalBox()
		val rightPane = VerticalBox()

		// TODO(Bas): Make constraints work... somhow?
		root.add(leftPane, SpringLayout.Constraints(Spring.constant(0), Spring.constant(0), Spring.constant(150), Spring.constant(390)))
		root.add(rightPane, SpringLayout.Constraints(Spring.constant(0), Spring.constant(0), Spring.constant(350), Spring.constant(390)))

		this.contentPane = root

		leftPane.add(JBLabel("Logo here!"))

		val basTools = JBLabel("Bas Tools 1.4.0")
		basTools.horizontalAlignment = JBLabel.CENTER
		basTools.foreground = ColorUtil.fromHex("#929da5")
		basTools.font = Font("Helvetica", 700, JBUI.scale(24))

		rightPane.add(basTools)
	}

	fun showWindow()
	{
		this.background = ColorUtil.fromHex("#262a2d")
		this.isResizable = false
		this.size = JBDimension(540, 390)
		this.setLocationRelativeTo(null)
		this.isVisible = true
	}

}

fun main(args: Array<String>)
{
	val about = AboutWindow()
	about.showWindow()
}
