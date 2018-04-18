/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.border

import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonPainter
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.*
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
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getBorderInsets(component: Component): Insets
	{
		val btn = component as? JComponent ?: return JBUI.insets(0)

		return when
		{
			UIUtil.isHelpButton(btn) -> JBUI.insets(3)
			DarculaButtonUI.isComboButton(btn) -> JBUI.insets(9)
			else -> JBUI.insets(9, 18)
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paintBorder(component: Component, graphics: Graphics, x: Int, y: Int, width: Int, height: Int)
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paintShadow(g: Graphics2D, r: Rectangle)
	{
	}

}
