package com.basmilius.bastools.ui.laf

import com.basmilius.bastools.core.util.StaticPatcher
import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.openapi.editor.colors.EditorColors
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil
import javax.swing.UIManager

/**
 * Class BasToolsLaf
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.ui.laf
 */
class BasToolsLaf: DarculaLaf()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun getDescription() = "Better dark theme for IntelliJ IDEA."

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun getName() = "BasTools Darcula"

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun getPrefix() = "bastools"

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun parseValue(key: String, value: String): Any?
	{
		System.out.println(String.format("UI: %s (%s)", key, value))

		return super.parseValue(key, value)
	}

	fun patchUI()
	{
		this.patchUIUtil()
	}

	private fun patchUIUtil()
	{
		val color = UIManager.getColor("Panel.background")

		StaticPatcher.setFinalStatic(UIUtil::class.java, "CONTRAST_BORDER_COLOR", color)
		StaticPatcher.setFinalStatic(UIUtil::class.java, "BORDER_COLOR", color)
		StaticPatcher.setFinalStatic(UIUtil::class.java, "AQUA_SEPARATOR_FOREGROUND_COLOR", color)

		StaticPatcher.setFinalStatic(UIUtil::class.java, "BORDER_COLOR", JBColor(0xff0000, 0xff0000))
		StaticPatcher.setFinalStatic(UIUtil::class.java, "CONTRAST_BORDER_COLOR", JBColor(0x2c3134, 0x2c3134))

		StaticPatcher.setFinalStatic(UIUtil::class.java, "SIDE_PANEL_BACKGROUND", JBColor(0x292d30, 0x292d30))

		StaticPatcher.setFinalStatic(UIUtil::class.java, "AQUA_SEPARATOR_FOREGROUND_COLOR", JBColor(0x3A3F43, 0x3A3F43))
		StaticPatcher.setFinalStatic(UIUtil::class.java, "AQUA_SEPARATOR_BACKGROUND_COLOR", JBColor(0x3A3F43, 0x3A3F43))
	}

}
