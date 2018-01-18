package com.basmilius.math.miscellaneous

import com.basmilius.math.Expression

/**
 * Class IterativeOperatorParameters
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.miscellaneous
 */
class IterativeOperatorParameters(functionParameters: List<FunctionParameter>)
{

	var indexParam: FunctionParameter = functionParameters[0]
	var fromParam: FunctionParameter = functionParameters[1]
	var toParam: FunctionParameter = functionParameters[2]
	var funParam: FunctionParameter = functionParameters[3]
	var deltaParam: FunctionParameter? = null
	var fromExp: Expression? = null
	var toExp: Expression? = null
	var funExp: Expression? = null
	var deltaExp: Expression? = null
	var from: Double = 0.0
	var to: Double = 0.0
	var delta: Double = 0.0
	var withDelta: Boolean = false

	init
	{
		deltaParam = null
		withDelta = false

		if (functionParameters.size == 5)
		{
			deltaParam = functionParameters[4]
			withDelta = true
		}
	}

}
