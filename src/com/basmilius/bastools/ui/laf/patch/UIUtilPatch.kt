/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.patch

import com.basmilius.bastools.core.util.StaticPatcher
import com.basmilius.bastools.ui.tabs.BTTabsPainter
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil
import javax.swing.UIManager

/**
 * Class UIUtilPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class UIUtilPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		val panelBackgroundColor = UIManager.getColor("Panel.background")

		StaticPatcher.setFinalStatic(UIUtil::class, "CONTRAST_BORDER_COLOR", panelBackgroundColor)
		StaticPatcher.setFinalStatic(UIUtil::class, "BORDER_COLOR", panelBackgroundColor)
		StaticPatcher.setFinalStatic(UIUtil::class, "AQUA_SEPARATOR_FOREGROUND_COLOR", panelBackgroundColor)

		StaticPatcher.setFinalStatic(UIUtil::class, "BORDER_COLOR", JBColor(0xff0000, 0xff0000))
		StaticPatcher.setFinalStatic(UIUtil::class, "CONTRAST_BORDER_COLOR", JBColor(0x2c3134, 0x2c3134))

		StaticPatcher.setFinalStatic(UIUtil::class, "SIDE_PANEL_BACKGROUND", JBColor(0x292d30, 0x292d30))

		StaticPatcher.setFinalStatic(UIUtil::class, "AQUA_SEPARATOR_FOREGROUND_COLOR", JBColor(0x2c2d32, 0x2c2d32))
		StaticPatcher.setFinalStatic(UIUtil::class, "AQUA_SEPARATOR_BACKGROUND_COLOR", JBColor(0x2c2d32, 0x2c2d32))

		StaticPatcher.setFinalStatic(UIUtil::class, "SIDE_PANEL_BACKGROUND", BTTabsPainter.FocusColor)
	}

}
