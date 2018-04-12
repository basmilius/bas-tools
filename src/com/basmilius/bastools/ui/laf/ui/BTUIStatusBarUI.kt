/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.ui

import com.intellij.openapi.wm.impl.status.StatusBarUI
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.*
import javax.swing.JComponent
import javax.swing.border.Border

/**
 * Class BTUIStatusBarUI
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.ui
 * @since 1.4.0
 */
class BTUIStatusBarUI: StatusBarUI()
{

	/**
	 * Companion Object BTUIStatusBarUI
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.ui.laf.ui
	 * @since 1.4.0
	 */
	companion object
	{

		private val MinSize = Dimension(100, 30)

		private val MaxSize = Dimension(Int.MAX_VALUE, 30)

		private val painter = Painter()

	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getMinimumSize(component: JComponent) = MinSize

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getMaximumSize(component: JComponent) = MaxSize

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paint(graphics: Graphics, component: JComponent)
	{
		val bounds = component.bounds
		painter.paintBorder(component, graphics, 0, 0, bounds.width, bounds.height)
	}

	/**
	 * Class Painter
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.ui.laf.ui.BTUIStatusBarUI
	 * @since 1.4.0
	 */
	private class Painter: Border
	{

		/**
		 * {@inheritdoc}
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		override fun getBorderInsets(component: Component): Insets = JBUI.insets(0)

		/**
		 * {@inheritdoc}
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		override fun isBorderOpaque() = true

		/**
		 * {@inheritdoc}
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		override fun paintBorder(component: Component, graphics: Graphics, x: Int, y: Int, width: Int, height: Int)
		{
			val g2d = graphics.create() as Graphics2D
			val background = UIUtil.getPanelBackground()
			val border = JBColor(Color(50, 52, 56), Color(50, 52, 56))

			g2d.color = background
			g2d.fillRect(0, 0, width, height)

			g2d.color = border
			g2d.drawLine(0, 0, width, 0)

			g2d.dispose()
		}

	}

}
