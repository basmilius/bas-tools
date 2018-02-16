/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

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
