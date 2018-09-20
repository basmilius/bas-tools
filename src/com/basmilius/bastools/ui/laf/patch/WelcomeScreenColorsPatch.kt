/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.patch

import com.basmilius.bastools.core.util.ReflectionUtils
import com.basmilius.bastools.ui.BTUI
import com.intellij.openapi.wm.impl.welcomeScreen.WelcomeScreenColors

/**
 * Class WelcomeScreenColorsPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class WelcomeScreenColorsPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch() // Future stuff.
	{
//		// These two for the topmost "Welcome to <product name>"
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "WELCOME_HEADER_BACKGROUND", BTUI.Colors.BackgroundColor.asJBColor())
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "WELCOME_HEADER_FOREGROUND", BTUI.Colors.BackgroundColor.asJBColor())
//
//		// This is for border around recent projects, action cards and also lines separating header and footer from main contents.
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "BORDER_COLOR", BTUI.Colors.BackgroundColor.asJBColor())
//
//		// This is for circle around hovered (next) icon
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "GROUP_ICON_BORDER_COLOR", BTUI.Colors.BackgroundColor.asJBColor())
//
//		// These two are for footer (Full product with build #, small letters)
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "FOOTER_BACKGROUND", BTUI.Colors.BackgroundColor.asJBColor())
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "FOOTER_FOREGROUND", BTUI.Colors.BackgroundColor.asJBColor())
//
//		// There two are for caption of Recent Project and Action Cards
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "CAPTION_BACKGROUND", BTUI.Colors.BackgroundColor.asJBColor())
//		ReflectionUtils.setFinalStatic(WelcomeScreenColors::class, "CAPTION_FOREGROUND", BTUI.Colors.BackgroundColor.asJBColor())
	}

}
