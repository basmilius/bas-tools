package com.basmilius.bastools.framework.identity.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionContributor
import com.basmilius.bastools.framework.base.completion.BaseCompletionProvider
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

/**
 * Class CoreCompletionContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.completion
 * @since 1.1.0
 */
class CoreCompletionContributor: BaseCompletionContributor()
{

	/**
	 * CoreCompletionContributor Constructor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	init
	{
		this.extend(CompletionType.BASIC, PluginCallProvider())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun isAvailable() = true

	/**
	 * Class PluginCallProvider
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	internal inner class PluginCallProvider: BaseCompletionProvider()
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun isAvailable() = true

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
		{
//			val func = PsiTreeUtil.getContextOfType(parameters.originalPosition, Method::class.java) ?: return
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun getPlace() = PlatformPatterns.psiElement()!!

	}

}
