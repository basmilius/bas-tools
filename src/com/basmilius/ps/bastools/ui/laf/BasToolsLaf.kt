package com.basmilius.ps.bastools.ui.laf

import com.intellij.ide.ui.laf.darcula.DarculaLaf

class BasToolsLaf : DarculaLaf()
{

	/**
	 * {@inheritdoc}
	 */
	override fun getPrefix(): String
	{
		return "bastools"
	}

}
