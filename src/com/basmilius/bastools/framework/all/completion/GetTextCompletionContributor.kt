package com.basmilius.bastools.framework.all.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class GetTextCompletionContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.completion
 * @since 1.2.0
 */
class GetTextCompletionContributor: BaseCompletionContributor()
{

	/**
	 * GetTextCompletionContributor Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	init
	{
		this.extend(CompletionType.BASIC, GetTextCompletionProvider())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	override fun isAvailable(): Boolean = false

}
