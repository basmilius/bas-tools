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
import com.intellij.ui.CaptionPanel
import com.intellij.ui.JBColor
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

/**
 * Class CaptionPanelPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class CaptionPanelPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		val classPool = ClassPool(true)
		classPool.insertClassPath(ClassClassPath(CaptionPanel::class.java))

		val classPopupBorder = classPool.get("com.intellij.ui.PopupBorder")
		val classPopupBorderBaseBorder = classPopupBorder.declaredClasses.find { it.simpleName == "PopupBorder\$BaseBorder" }

		if (classPopupBorderBaseBorder != null)
		{
			val getBorderInsetsMethod = classPopupBorderBaseBorder.getDeclaredMethod("getBorderInsets")

			getBorderInsetsMethod.setBody("{ return com.intellij.util.ui.JBUI.insets(6, 9); }")

			classPopupBorderBaseBorder.toClass()
		}

		val classSimpleColoredComponent = classPool.get("com.intellij.ui.SimpleColoredComponent")
		val constructorMethodSimpleColoredComponent = classSimpleColoredComponent.getDeclaredConstructor(arrayOf())

		constructorMethodSimpleColoredComponent.insertAfter("this.myIconTextGap = com.intellij.util.ui.JBUI.scale(6);")

		classSimpleColoredComponent.toClass()

		val classTitlePanel = classPool.get("com.intellij.ui.TitlePanel")
		val constructorMethod = classTitlePanel.getDeclaredConstructor(arrayOf(
				classPool.get("javax.swing.Icon"),
				classPool.get("javax.swing.Icon")
		))

		constructorMethod.instrument(object: ExprEditor()
		{

			override fun edit(call: MethodCall)
			{
				val methodCall = "${call.className}::${call.methodName}"

				when (methodCall)
				{
					"com.intellij.util.ui.JBUI\$Borders::empty" -> call.replace("{ \$1 = 6; \$2 = 12; \$3 = 6; \$4 = 12; \$_ = \$proceed($$); }")
					"javax.swing.JLabel::setHorizontalAlignment" -> call.replace("{ \$1 = javax.swing.SwingConstants.LEFT; \$_ = \$proceed($$); }")
				}
			}

		})

		classTitlePanel.toClass()

		ReflectionUtils.setFinalStatic(JBColor::class, "LIGHT_GRAY", BTUI.Colors.OutlineColor.asJBColor())

		ReflectionUtils.setFinalStatic(CaptionPanel::class, "CNT_ACTIVE_BORDER_COLOR", BTUI.Colors.OutlineColor.asJBColor())
		ReflectionUtils.setFinalStatic(CaptionPanel::class, "TOP_FLICK_ACTIVE", BTUI.Colors.BackgroundColor.asJBColor())
		ReflectionUtils.setFinalStatic(CaptionPanel::class, "TOP_FLICK_PASSIVE", BTUI.Colors.BackgroundColor.asJBColor())
		ReflectionUtils.setFinalStatic(CaptionPanel::class, "BOTTOM_FLICK_ACTIVE", BTUI.Colors.OutlineColor.asJBColor())
		ReflectionUtils.setFinalStatic(CaptionPanel::class, "BOTTOM_FLICK_PASSIVE", BTUI.Colors.OutlineColor.asJBColor())
		ReflectionUtils.setFinalStatic(CaptionPanel::class, "CNT_ACTIVE_COLOR", BTUI.Colors.BackgroundColor.asJBColor())
		ReflectionUtils.setFinalStatic(CaptionPanel::class, "BND_ACTIVE_COLOR", BTUI.Colors.BackgroundColor.asJBColor())
	}

}
