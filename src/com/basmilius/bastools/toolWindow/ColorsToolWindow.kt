/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

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

	private val root = com.intellij.ui.components.panels.VerticalBox()

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
