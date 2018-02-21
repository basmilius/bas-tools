/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.border

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonPainter
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.MacUIUtil
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.Path2D
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D
import javax.swing.JComponent

/**
 * Class BTUIButtonBorder
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.border
 * @since 1.4.0
 */
class BTUIButtonBorder: DarculaButtonPainter()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paintBorder(component: Component, graphics: Graphics, x: Int, y: Int, width: Int, height: Int)
	{
		val g = graphics.create() as Graphics2D

		if (component !is JComponent)
			return

		try
		{
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, if (MacUIUtil.USE_QUARTZ) RenderingHints.VALUE_STROKE_PURE else RenderingHints.VALUE_STROKE_NORMALIZE)

			val r = Rectangle(x, y, width, height)
			JBInsets.removeFrom(r, JBUI.insets(1))

			g.translate(r.x, r.y)

			val diam = JBUI.scale(22f) // HELP_BUTTON_DIAMETER in ButtonUI
			val arc = JBUI.scale(3.0f)
			val lw = DarculaUIUtil.lw(g)
			val bw = DarculaUIUtil.bw()

			if (component.hasFocus())
			{
				if (UIUtil.isHelpButton(component))
					DarculaUIUtil.paintFocusOval(g, (r.width - diam) / 2 + lw, (r.height - diam) / 2 + lw, diam - lw, diam - lw)
				else
					DarculaUIUtil.paintFocusBorder(g, r.width, r.height, arc, true)
			}
			else if (!UIUtil.isHelpButton(component))
			{
				this.paintShadow(g, r)
			}

			val color = this.getBorderColor(component)
			g.paint = Color(color.red, color.green, color.blue, if (DarculaButtonUI.isDefaultButton(component)) 150 else 180)

			if (UIUtil.isHelpButton(component))
			{
				g.draw(Ellipse2D.Float((r.width - diam) / 2f, (r.height - diam) / 2f, diam, diam))
			}
			else
			{
				val border = Path2D.Float(Path2D.WIND_EVEN_ODD)
				border.append(RoundRectangle2D.Float(bw, bw, r.width - bw * 2f, r.height - bw * 2f, arc, arc), false)
				border.append(RoundRectangle2D.Float(bw + lw, bw + lw, r.width - (bw + lw) * 2, r.height - (bw + lw) * 2, arc - lw, arc - lw), false)

				g.fill(border)
			}
		}
		finally
		{
			g.dispose()
		}
	}

	/**
	 * Paints a shadow.
	 *
	 * @param g Graphics2D
	 * @param r Rectangle
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun paintShadow(g: Graphics2D, r: Rectangle)
	{
		g.color = Color.BLACK

		val composite = g.composite
		g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f)

		val bw = DarculaUIUtil.bw()
		g.fill(Rectangle2D.Float(bw, r.height - bw, r.width - bw * 2f, JBUI.scale(2f)))

		g.composite = composite
	}

}
