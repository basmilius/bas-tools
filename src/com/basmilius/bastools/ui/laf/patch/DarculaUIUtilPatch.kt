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
import com.intellij.ide.ui.laf.darcula.DarculaLaf
import javassist.ClassClassPath
import javassist.ClassPool

/**
 * Class DarculaUIUtilPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class DarculaUIUtilPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		val classPool = ClassPool(true)
		classPool.insertClassPath(ClassClassPath(DarculaLaf::class.java))

		val classDarculaUIUtil = classPool.get("com.intellij.ide.ui.laf.darcula.DarculaUIUtil")
		val getOutline = classDarculaUIUtil.getDeclaredMethod("getOutlineColor")
		val color = BTUI.Colors.OutlineColor

		getOutline.setBody("{ return new java.awt.Color(${color.red}, ${color.green}, ${color.blue}); }")

		classDarculaUIUtil.toClass()
	}

}
