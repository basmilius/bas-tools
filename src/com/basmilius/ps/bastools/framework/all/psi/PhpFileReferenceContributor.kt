package com.basmilius.ps.bastools.framework.all.psi

import com.basmilius.ps.bastools.framework.all.completion.BasicFunctionCompletionProvider
import com.basmilius.ps.bastools.util.FileUtils
import com.basmilius.ps.bastools.util.PhpUtils
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.PhpLanguage
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.NewExpressionImpl
import com.intellij.util.PlatformUtils

/**
 * Class PhpFileReferenceContributor
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.all.psi
 */
class PhpFileReferenceContributor: PsiReferenceContributor()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun registerReferenceProviders(registrar: PsiReferenceRegistrar)
	{
		if (PlatformUtils.isPhpStorm())
		{
            registrar.registerReferenceProvider(PlatformPatterns.psiElement(StringLiteralExpression::class.java).withLanguage(PhpLanguage.INSTANCE), ReferenceProvider())
        }
	}

	/**
	 * Class ReferenceProvider
	 *
	 * @author Bas Milius
	 * @package com.basmilius.ps.bastools.framework.all.psi.ReferenceProvider
	 */
	private class ReferenceProvider: PsiReferenceProvider()
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference>
		{
			if (element.context !is ParameterList)
				return arrayOf()

			val parameterList = element.context

			if (parameterList == null || !(parameterList.context is FunctionReference || parameterList.context is MethodReference || parameterList.context is NewExpressionImpl))
				return arrayOf()

			val functionName = PhpUtils.getCanonicalFunctionName(element.parent.parent)
			val parameterIndex = PhpUtils.getParameterIndex(element)

			if (BasicFunctionCompletionProvider.CompletionTokens.FileOtherFunctions.contains("$functionName:$parameterIndex:f"))
				return arrayOf(GenericFileReference(element as StringLiteralExpression, FileUtils.TYPE_FILE))

			if (BasicFunctionCompletionProvider.CompletionTokens.FileOtherFunctions.contains("$functionName:$parameterIndex:d"))
				return arrayOf(GenericFileReference(element as StringLiteralExpression, FileUtils.TYPE_DIRECTORY))

			if (BasicFunctionCompletionProvider.CompletionTokens.FileOtherFunctions.contains("$functionName:$parameterIndex"))
				return arrayOf(GenericFileReference(element as StringLiteralExpression, FileUtils.TYPE_ALL))

			return arrayOf()
		}

	}

}
