package com.basmilius.ps.bastools.framework.base.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class BaseCompletionContributor
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.base.completion
 */
abstract class BaseCompletionContributor: CompletionContributor()
{

	/**
	 * Adds completions.
	 *
	 * @param type CompletionType
	 * @param completionProvider BaseCompletionProvider
	 *
	 * @author Bas Milius
	 */
	fun extend(type: CompletionType, completionProvider: BaseCompletionProvider)
	{
		completionProvider.setContributor(this)
		this.extend(type, completionProvider.getPlace(), completionProvider);
	}

}
