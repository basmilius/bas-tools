/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.patch

import com.basmilius.bastools.ui.BTUI
import com.intellij.openapi.wm.impl.AltStateManager
import javassist.ClassClassPath
import javassist.ClassPool

/**
 * Class IdeBackgroundUtilPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.1
 */
class IdeBackgroundUtilPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		val classPool = ClassPool(true)
		classPool.insertClassPath(ClassClassPath(AltStateManager::class.java))

		val classIdeTooltip = classPool.get("com.intellij.openapi.wm.impl.IdeBackgroundUtil")
		val getBorderColorMethod = classIdeTooltip.getDeclaredMethod("getIdeBackgroundColor")
		val color = BTUI.Colors.BackgroundColor

		getBorderColorMethod.setBody("{ return new java.awt.Color(${color.red}, ${color.green}, ${color.blue}); }")

		classIdeTooltip.toClass()
	}

}
