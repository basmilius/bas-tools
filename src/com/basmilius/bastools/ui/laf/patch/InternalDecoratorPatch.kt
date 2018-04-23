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
import com.intellij.openapi.wm.impl.ToolWindowImpl
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

/**
 * Class InternalDecoratorPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class InternalDecoratorPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		val classPool = ClassPool(true)
		classPool.insertClassPath(ClassClassPath(ToolWindowImpl::class.java))

		val color = BTUI.Colors.OutlineColor

		val classInnerPanelBorder = classPool.get("com.intellij.openapi.wm.impl.InternalDecorator\$InnerPanelBorder")
		val paintBorderMethod = classInnerPanelBorder.getDeclaredMethod("paintBorder")

		paintBorderMethod.instrument(object: ExprEditor()
		{

			override fun edit(call: MethodCall)
			{
				val methodCall = "${call.className}::${call.methodName}"

				when (methodCall)
				{
					"java.awt.Graphics::setColor" -> call.replace("{ \$1 = new java.awt.Color(${color.red}, ${color.green}, ${color.blue}); \$proceed($$); }")
				}
			}

		})

		classInnerPanelBorder.toClass()
	}

}
