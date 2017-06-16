package com.basmilius.ps.bastools.component

import com.intellij.psi.codeStyle.CodeStyleScheme
import com.intellij.psi.impl.source.codeStyle.CodeStyleSchemeImpl

abstract class BTCodeStyleScheme(name: String, isDefault: Boolean, private val parentScheme: CodeStyleScheme?) : CodeStyleSchemeImpl(name, isDefault, parentScheme)
{

	/**
	 * Gets the parent scheme.
	 */
	fun <T : CodeStyleSchemeImpl> getParentScheme(implementation: Class<T>): T
	{
		return implementation.cast(this.parentScheme)
	}

}
