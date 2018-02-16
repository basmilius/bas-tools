/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.formatter

import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings

/**
 * Class CappuccinoFormatterOptions
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.formatter
 */
class CappuccinoFormatterOptions(container: CodeStyleSettings): CustomCodeStyleSettings("Cappuccino", container)
{

	val SPACES_INSIDE_DELIMITERS = true
	val SPACES_INSIDE_VARIABLE_DELIMITERS = true

}
