package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.Token

/**
 * Data Class FunctionParameter
 *
 * @author Bas Milius
 * @package com.basmilius.math.miscellaneous
 */
data class FunctionParameter(val tokens: ArrayList<Token>, val paramStr: String, val fromIndex: Int, val toIndex: Int)
