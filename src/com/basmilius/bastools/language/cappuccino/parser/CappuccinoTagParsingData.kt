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
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.parser
 */
data class CappuccinoTagParsingData(val tagType: IElementType, val isShort: Boolean, val name: String?)
