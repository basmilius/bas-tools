/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.Token

/**
 * Data Class FunctionParameter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.miscellaneous
 */
data class FunctionParameter(val tokens: ArrayList<Token>, val paramStr: String, val fromIndex: Int, val toIndex: Int)
