package com.basmilius.bastools.toolWindow

import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class ColorsToolWindow: BaseToolWindowFactory()
{

	private val root = JPanel(GridLayoutManager(8, 2))

	init
	{
		val openColorPicker = JButton("Color Picker")
		val launchColorPipette = JButton("Color Pipette")

		this.root.border = EmptyBorder(12, 12, 12, 12)

		this.root.add(openColorPicker, GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, Dimension(0, 0), Dimension(100, 36), Dimension(999, 999)))
		this.root.add(launchColorPipette, GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, Dimension(0, 0), Dimension(100, 36), Dimension(999, 999)))
	}

	override fun createWindowContent(): JComponent
	{
		return this.root
	}

}
