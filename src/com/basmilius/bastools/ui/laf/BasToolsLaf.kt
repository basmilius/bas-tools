package com.basmilius.bastools.ui.laf

import com.basmilius.bastools.core.util.StaticPatcher
import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil
import javax.swing.UIManager

/**
 * Class BasToolsLaf
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf
 * @since 1.0.0
 */
class BasToolsLaf: DarculaLaf()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getDescription() = "Better dark theme for IntelliJ IDEA."

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getName() = "BasTools Darcula"

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getPrefix() = "bastools"

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun parseValue(key: String, value: String): Any?
	{
//		System.out.println(String.format("UI: %s (%s)", key, value))

		// TODO(Bas): Find out what properties we should override.

		return super.parseValue(key, value)
	}

	/**
	 * Start patching the UI.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun patchUI()
	{
		this.patchUIUtil()
	}

	/**
	 * Patches the UI with utils.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
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
