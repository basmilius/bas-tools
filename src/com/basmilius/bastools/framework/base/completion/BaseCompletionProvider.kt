/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.base.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

/**
 * Class BaseCompletionProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base.completion
 * @since 1.1.0
 */
abstract class BaseCompletionProvider: CompletionProvider<CompletionParameters>()
{

	private var _contributor: BaseCompletionContributor? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	abstract override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)

	/**
	 * Gets the contributor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun getContributor(): BaseCompletionContributor?
	{
		return this._contributor;
	}

	/**
	 * Returns TRUE if this completion provider should be available.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	abstract fun isAvailable(): Boolean;

	/**
	 * Gets the place.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	abstract fun getPlace(): ElementPattern<out PsiElement>

	/**
	 * Sets the contributor.
	 *
	 * @param contributor BaseCompletionContributor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun setContributor(contributor: BaseCompletionContributor)
	{
		this._contributor = contributor;
	}

}
