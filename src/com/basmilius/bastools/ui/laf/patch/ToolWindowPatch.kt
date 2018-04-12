/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.patch

import com.intellij.openapi.wm.impl.ToolWindowImpl
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

/**
 * Class ToolWindowPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class ToolWindowPatch: IUIPatch
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

		val classToolWindowHeader = classPool.get("com.intellij.openapi.wm.impl.ToolWindowHeader")

		val constructorMethod = classToolWindowHeader.declaredConstructors.first()
		val getPreferredSizeMethod = classToolWindowHeader.getDeclaredMethod("getPreferredSize")

		constructorMethod.instrument(object: ExprEditor()
		{

			override fun edit(call: MethodCall)
			{
				if (call.className != "javax.swing.BorderFactory" || call.methodName != "createEmptyBorder")
					return

				call.replace("\$_ = javax.swing.BorderFactory.createEmptyBorder(1, 0, 1, 0);")
			}

		})

		getPreferredSizeMethod.instrument(object: ExprEditor()
		{

			override fun edit(call: MethodCall)
			{
				if (call.className != "com.intellij.ui.tabs.TabsUtil" || call.methodName != "getTabsHeight")
					return

				call.replace("\$_ = com.intellij.util.ui.JBUI.scale(31);")
			}

		})

		classToolWindowHeader.toClass()
	}

}
