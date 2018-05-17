/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.patch

import com.intellij.openapi.wm.impl.IdeBackgroundUtil
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

/**
 * Class IdePanePanelPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class IdePanePanelPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		val classPool = ClassPool(true)
		classPool.insertClassPath(ClassClassPath(IdeBackgroundUtil::class.java))

		val classIdePanePanel = classPool.get("com.intellij.openapi.wm.impl.IdePanePanel")
		val getBackgroundMethod = classIdePanePanel.getDeclaredMethod("getBackground")

		getBackgroundMethod.instrument(object: ExprEditor()
		{

			override fun edit(call: MethodCall)
			{
				val methodCall = "${call.className}::${call.methodName}"

				println(methodCall)

				if (methodCall != "com.intellij.openapi.wm.impl.IdeBackgroundUtil::getIdeBackgroundColor")
					return

				call.replace("{ \$_ = javax.swing.UIManager.getColor(\"window\"); }")
			}

		})

		classIdePanePanel.toClass()
	}

}
