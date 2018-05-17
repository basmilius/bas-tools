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
import com.intellij.ide.IdeTooltipManager
import javassist.ClassClassPath
import javassist.ClassPool

/**
 * Class IdeTooltipPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class IdeTooltipPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		val classPool = ClassPool(true)
		classPool.insertClassPath(ClassClassPath(IdeTooltipManager::class.java))

		val classIdeTooltip = classPool.get("com.intellij.ide.IdeTooltip")
		val getBorderColorMethod = classIdeTooltip.getDeclaredMethod("getBorderColor")
//		val getBorderInsetsMethod = classIdeTooltip.getDeclaredMethod("getBorderInsets")
		val color = BTUI.Colors.OutlineColor

		getBorderColorMethod.setBody("{ return new java.awt.Color(${color.red}, ${color.green}, ${color.blue}); }")
//		getBorderInsetsMethod.setBody("{ return com.intellij.util.ui.JBUI.insets(24); }")

		classIdeTooltip.toClass()
	}

}
