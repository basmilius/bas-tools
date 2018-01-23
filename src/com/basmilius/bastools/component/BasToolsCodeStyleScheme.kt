package com.basmilius.bastools.component

import com.intellij.psi.codeStyle.CodeStyleScheme
import com.intellij.psi.impl.source.codeStyle.CodeStyleSchemeImpl

/**
 * CLass BasToolsCodeStyleScheme
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component
 * @since 1.1.0
 */
abstract class BasToolsCodeStyleScheme(name: String, isDefault: Boolean, private val parentScheme: CodeStyleScheme?): CodeStyleSchemeImpl(name, isDefault, parentScheme)
{

	/**
	 * Gets the parent scheme.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun <T: CodeStyleSchemeImpl> getParentScheme(implementation: Class<T>): T
	{
		return implementation.cast(this.parentScheme)
	}

}
