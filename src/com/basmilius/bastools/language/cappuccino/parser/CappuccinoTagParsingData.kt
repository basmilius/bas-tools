/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.parser

import com.intellij.psi.tree.IElementType

/**
 * Data Class CappuccinoTagParsingData
 *
 * @constructor
 * @param tagType IElementType
 * @param isShort Boolean
 * @param name String?
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.parser
 */
data class CappuccinoTagParsingData(val tagType: IElementType, val isShort: Boolean, val name: String)
