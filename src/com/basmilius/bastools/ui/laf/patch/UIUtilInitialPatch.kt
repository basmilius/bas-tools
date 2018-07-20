/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.patch

/**
 * Class UIUtilInitialPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.5.0
 */
class UIUtilInitialPatch: IUIPatch
{

	override fun patch()
	{
//		val classPool = ClassPool(true)
//		classPool.insertClassPath(ClassClassPath(com.intellij.util.ui.Animator::class.java))
//
//		classPool.classLoader.loadClass("com.intellij.util.ui.UIUtil")
//
//		val classUIUtil = classPool.get("com.intellij.util.ui.UIUtil")
//		classUIUtil.stopPruning(true)
//
//		if (classUIUtil.isFrozen)
//			classUIUtil.defrost()
//
//		val methodGetBorderColor = classUIUtil.getDeclaredMethod("getBorderColor")
//		methodGetBorderColor.setBody("{ return new java.awt.Color(42, 43, 49); }")
//
//		HotSwapAgent.redefine(UIUtil::class.java, classUIUtil)
	}

}
