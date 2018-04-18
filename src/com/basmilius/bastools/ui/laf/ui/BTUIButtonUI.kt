/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.ui

import com.basmilius.bastools.core.util.ColorUtil.withAlpha
import com.intellij.icons.AllIcons
import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.ui.JBColor
import com.intellij.util.ObjectUtils
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import sun.swing.SwingUtilities2
import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.RoundRectangle2D
import javax.swing.AbstractButton
import javax.swing.JComponent
import javax.swing.UIManager
import javax.swing.plaf.ColorUIResource

/**
 * Class BTUIButtonUI
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.ui
 * @since 1.4.0
 */
class BTUIButtonUI: DarculaButtonUI()
{

	companion object
	{

		val ButtonRadius = JBUI.scale(9f)
		val HelpButtonDiameter = JBUI.scale(22f)

		val TextColor = JBColor(Color(255, 255, 255, 150), Color(255, 255, 255, 150))

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paintDecorations(g: Graphics2D, component: JComponent): Boolean
	{
		val rect = Rectangle(component.size)

		if (UIUtil.isHelpButton(component))
		{
			g.paint = UIUtil.getGradientPaint(0f, 0f, this.buttonColorStart, 0f, rect.height.toFloat(), this.buttonColorEnd)

			val x = rect.x + (rect.width - HelpButtonDiameter) / 2
			val y = rect.y + (rect.height - HelpButtonDiameter) / 2

			g.fill(Ellipse2D.Float(x, y, HelpButtonDiameter, HelpButtonDiameter))

			AllIcons.Actions.Help.paintIcon(component, g, (x + JBUI.scale(3)).toInt(), (y + JBUI.scale(3)).toInt())

			return false
		}
		else
		{
			val g2d = g.create() as Graphics2D

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)

			val backgroundColor = component.getClientProperty("JButton.backgroundColor") as? Color
			val startColor = if (component.isEnabled) this.buttonColorStart else this.buttonColorStart.withAlpha(128)
			val endColor = if (component.isEnabled) this.buttonColorEnd else this.buttonColorEnd.withAlpha(128)
			val startColorDefault = if (component.isEnabled) this.defaultButtonColorStart else this.defaultButtonColorStart.withAlpha(128)
			val endColorDefault = if (component.isEnabled) this.defaultButtonColorEnd else this.defaultButtonColorEnd.withAlpha(128)

			g2d.paint = when
			{
				backgroundColor != null -> backgroundColor
				isDefaultButton(component) -> UIUtil.getGradientPaint(0f, 0f, startColorDefault, 0f, rect.height.toFloat(), endColorDefault)
				else -> UIUtil.getGradientPaint(0f, 0f, startColor, 0f, rect.height.toFloat(), endColor)
			}

			val bw = DarculaUIUtil.bw()

			g2d.fill(RoundRectangle2D.Float(bw, bw, rect.width - bw * 2, rect.height - bw * 2, ButtonRadius, ButtonRadius))
			g2d.dispose()

			return true
		}
	}

	override fun paintText(g: Graphics, component: JComponent, rectangle: Rectangle, text: String)
	{
		if (UIUtil.isHelpButton(component))
			return

		val button = component as? AbstractButton ?: return
		val model = button.model
		val metrics = SwingUtilities2.getFontMetrics(component, g)
		val mnemonicIndex = if (DarculaLaf.isAltPressed()) button.displayedMnemonicIndex else -1

		g.color = TextColor

		if (model.isEnabled)
			SwingUtilities2.drawStringUnderlineCharAt(component, g, text, mnemonicIndex, rectangle.x + this.textShiftOffset, rectangle.y + metrics.ascent + this.textShiftOffset)
		else
			this.paintDisabledText(g, text, component, rectangle, metrics)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getButtonColorStart(): Color
	{
		return ObjectUtils.notNull(UIManager.getColor("Button.darcula.startColor"), ColorUIResource(0x414648))
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getButtonColorEnd(): Color
	{
		return ObjectUtils.notNull(UIManager.getColor("Button.darcula.endColor"), ColorUIResource(0x414648))
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getDefaultButtonColorStart(): Color
	{
		return ObjectUtils.notNull(UIManager.getColor("Button.darcula.selection.defaultStartColor"), ColorUIResource(0x233143))
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getDefaultButtonColorEnd(): Color
	{
		return ObjectUtils.notNull(UIManager.getColor("Button.darcula.selection.defaultEndColor"), ColorUIResource(0x233143))
	}

}
