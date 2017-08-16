package com.basmilius.ps.bastools.ui.component

import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.openapi.util.SystemInfo
import com.intellij.util.ObjectUtils
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.Font
import java.awt.Graphics
import javax.swing.AbstractButton
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.UIManager
import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.ComponentUI

class ButtonComponent : DarculaButtonUI()
{

	init
	{
		println("ButtonComponent")
	}

	override fun installDefaults(b : AbstractButton?)
	{
		super.installDefaults(b)

		if (b === null)
			return

		println("installDefaults")

		val background = ObjectUtils.notNull(UIManager.getColor("Button.bastools.background"), ColorUIResource(0x3c3f41))
		b.background = background
		b.font = b.font.deriveFont(Font.BOLD, JBUI.scale(13.0f))
	}

	override fun update (g : Graphics, c : JComponent)
	{
		super.update(g, c)
	}

	companion object
	{

		fun createUI (c : JComponent) : ComponentUI
		{
			return ButtonComponent()
		}

		fun isHelpButton (button : JComponent) : Boolean
		{
			return (SystemInfo.isMac || UIUtil.isUnderDarcula() || UIUtil.isUnderWin10LookAndFeel()) && button is JButton && button.getClientProperty("JButton.buttonType") === "help"
		}

	}

}
