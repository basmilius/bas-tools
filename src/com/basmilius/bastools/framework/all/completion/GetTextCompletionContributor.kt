package com.basmilius.bastools.framework.all.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class GetTextCompletionContributor
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.framework.all.completion
 */
class GetTextCompletionContributor: BaseCompletionContributor()
{

	/**
	 * GetTextCompletionContributor Constructor.
	 *
	 * @author Bas Milius
	 */
	init
	{
		this.extend(CompletionType.BASIC, GetTextCompletionProvider())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun isAvailable(): Boolean = true

}
