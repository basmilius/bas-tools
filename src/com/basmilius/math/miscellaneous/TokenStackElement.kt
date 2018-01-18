package com.basmilius.math.miscellaneous

/**
 * Data Class TokenStackElement
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.miscellaneous
 */
data class TokenStackElement(var tokenIndex: Int, var tokenId: Int, var tokenTypeId: Int, var tokenLevel: Int, var precedingFunction: Boolean)
