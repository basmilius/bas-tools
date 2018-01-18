package com.basmilius.bastools.framework.all.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class AllCompletionContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.completion
 */
class AllCompletionContributor: BaseCompletionContributor()
{

	/**
	 * AllCompletionContributor Constructor
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	init
	{
		this.extend(CompletionType.BASIC, FunctionCallAllArgumentsCompletionProvider())
		this.extend(CompletionType.BASIC, BasicFunctionCompletionProvider())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isAvailable() = true

}
