/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.miscellaneous

import com.basmilius.math.Argument
import com.basmilius.math.Expression
import com.basmilius.math.parsertokens.ConstantValue

/**
 * Class ArgumentParameter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.miscellaneous
 */
class ArgumentParameter
{

	var argument: Argument? = null
	var initialValue = Double.NaN
	var initialType = ConstantValue.NaN
	var presence = Expression.NOT_FOUND
	var index = -1

}
