package com.basmilius.bastools.framework.base.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class BaseCompletionContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base.completion
 */
abstract class BaseCompletionContributor: CompletionContributor()
{

	/**
	 * Adds completions.
	 *
	 * @param type CompletionType
	 * @param completionProvider BaseCompletionProvider
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun extend(type: CompletionType, completionProvider: BaseCompletionProvider)
	{
		if (!this.isAvailable())
			return

		completionProvider.setContributor(this)

		this.extend(type, completionProvider.getPlace(), completionProvider)
	}

	/**
	 * Returns TRUE if this completion provider should be available.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	abstract fun isAvailable (): Boolean

}
