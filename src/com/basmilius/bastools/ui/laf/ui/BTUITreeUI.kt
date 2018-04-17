/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.ui

import com.basmilius.bastools.resource.Icons
import com.intellij.openapi.util.Conditions
import com.intellij.ui.JBColor
import com.intellij.util.IconUtil
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.tree.WideSelectionTreeUI
import java.awt.Color
import java.awt.Graphics
import java.awt.Insets
import java.awt.Rectangle
import javax.swing.JComponent
import javax.swing.JViewport
import javax.swing.UIManager
import javax.swing.tree.TreePath

/**
 * Class BTUITreeUI
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.ui
 * @since 1.4.0
 */
class BTUITreeUI: WideSelectionTreeUI(true, Conditions.alwaysFalse())
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun installUI(c: JComponent?)
	{
		super.installUI(c)

		this.tree.rowHeight = JBUI.scale(30)
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getCollapsedIcon() = IconUtil.darker(IconUtil.scale(Icons.ChevronRight, 0.8), 5)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getExpandedIcon() = IconUtil.darker(IconUtil.scale(Icons.ChevronDown, 0.8), 5)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paintRow(graphics: Graphics, clipBounds: Rectangle, insets: Insets, bounds: Rectangle, path: TreePath?, row: Int, isExpanded: Boolean, hasBeenExpanded: Boolean, isLeaf: Boolean)
	{
		val containerWidth = if (this.tree.parent is JViewport) this.tree.parent.width else this.tree.width
		val offsetX = if (this.tree.parent is JViewport) (this.tree.parent as JViewport).viewPosition!!.x else 0

		if (path != null)
		{
			val selected = this.tree.isPathSelected(path)
			val leafGraphics = graphics.create()

			leafGraphics.clip = clipBounds

			if (selected)
			{
				leafGraphics.color = UIManager.getColor("bastools.selectionBackground")
				leafGraphics.fillRect(offsetX, bounds.y, containerWidth, bounds.height)
			}

			super.paintRow(leafGraphics, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf)

			leafGraphics.dispose()
		}
		else
		{
			super.paintRow(graphics, clipBounds, insets, bounds, null, row, isExpanded, hasBeenExpanded, isLeaf)
		}
	}

}
