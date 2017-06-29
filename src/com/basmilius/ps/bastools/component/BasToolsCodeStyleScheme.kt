package com.basmilius.ps.bastools.component

import com.intellij.psi.codeStyle.CodeStyleScheme
import com.intellij.psi.impl.source.codeStyle.CodeStyleSchemeImpl

/**
 * CLass BasToolsCodeStyleScheme
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.component
 */
abstract class BasToolsCodeStyleScheme(name: String, isDefault: Boolean, private val parentScheme: CodeStyleScheme?) : CodeStyleSchemeImpl(name, isDefault, parentScheme)
{

	/**
	 * Gets the parent scheme.
	 *
	 * @author Bas Milius
	 */
	fun <T : CodeStyleSchemeImpl> getParentScheme(implementation: Class<T>): T
	{
		return implementation.cast(this.parentScheme)
	}

}
