/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.renderer

/**
 * Data Class LineInfo
 *
 * @constructor
 * @param number Int
 * @param begin Int
 * @param end Int
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.renderer
 * @since 1.4.0
 */
data class LineInfo(val number: Int, var begin: Int, var end: Int)
