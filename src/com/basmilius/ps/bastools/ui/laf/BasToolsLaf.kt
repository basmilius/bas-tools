package com.basmilius.ps.bastools.ui.laf

import com.intellij.ide.ui.laf.darcula.DarculaLaf

/**
 * Class BasToolsLaf
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.ui.laf
 */
class BasToolsLaf : DarculaLaf()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getPrefix() : String
	{
		return "bastools"
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun parseValue(key : String, value : String) : Any?
	{
		// println("$key => $value")
		return super.parseValue(key, value)
	}

}
