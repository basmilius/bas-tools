/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all.psi

import com.basmilius.bastools.core.util.FileUtils
import com.basmilius.bastools.core.util.PhpUtils
import com.basmilius.bastools.core.util.PlatformPatternsUtils
import com.basmilius.bastools.framework.all.completion.BasicFunctionCompletionProvider
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.PhpLanguage
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.NewExpressionImpl

/**
 * Class PhpFileReferenceContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.psi
 * @since 1.2.0
 */
class PhpFileReferenceContributor: PsiReferenceContributor()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	override fun registerReferenceProviders(registrar: PsiReferenceRegistrar)
	{
		registrar.registerReferenceProvider(PlatformPatternsUtils.psiElement(StringLiteralExpression::class).withLanguage(PhpLanguage.INSTANCE), ReferenceProvider())
	}

	/**
	 * Class ReferenceProvider
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.all.psi.ReferenceProvider
	 * @since 1.2.0
	 */
	private class ReferenceProvider: PsiReferenceProvider()
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.2.0
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
