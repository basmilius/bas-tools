package com.basmilius.ps.bastools.framework.all.completion

import com.basmilius.ps.bastools.framework.base.completion.BaseCompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class AllCompletionContributor
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.all.completion
 */
class AllCompletionContributor : BaseCompletionContributor()
{

	/**
	 * AllCompletionContributor Constructor
	 *
	 * @author Bas Milius
	 */
	init
	{
		this.extend(CompletionType.BASIC, FunctionCallAllArgumentsCompletionProvider())
	}

}
