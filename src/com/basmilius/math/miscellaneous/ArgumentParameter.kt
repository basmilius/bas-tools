package com.basmilius.math.miscellaneous

import com.basmilius.math.Argument
import com.basmilius.math.Expression
import com.basmilius.math.parsertokens.ConstantValue

/**
 * Class ArgumentParameter
 *
 * @author Bas Milius
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
