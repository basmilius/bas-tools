package com.basmilius.ps.bastools.framework.identity.completion

import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.Method

/**
 * Class CoreCompletionContributor
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.completion
 */
class CoreCompletionContributor : CompletionContributor()
{
	/**
	 * CoreCompletionContributor Constructor
	 *
	 * @author Bas Milius
	 */
	init
	{
		this.extend(CompletionType.BASIC, PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(PhpElementTypes.METHOD_REFERENCE)), PluginCallProvider())
	}

	/**
	 * Class PluginCallProvider
	 *
	 * @author Bas Milius
	 */
	internal inner class PluginCallProvider : CompletionProvider<CompletionParameters>()
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun addCompletions(parameters : CompletionParameters, context : ProcessingContext, results : CompletionResultSet)
		{
			val func = PsiTreeUtil.getContextOfType(parameters.originalPosition, Method::class.java) ?: return

		}

	}

}
