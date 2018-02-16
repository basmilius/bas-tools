/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.elements.manipulators

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementFactory
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoVerbatimStatement
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator

/**
 * Class CappuccinoVerbatimStatementManipulator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements.manipulators
 */
class CappuccinoVerbatimStatementManipulator: AbstractElementManipulator<CappuccinoVerbatimStatement>()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun handleContentChange(element: CappuccinoVerbatimStatement, range: TextRange, newContent: String): CappuccinoVerbatimStatement
	{
		val currText = element.node.chars
		val newText = StringBuilder()

		newText.append(currText.subSequence(0, range.startOffset)).append(newContent).append(currText.subSequence(range.endOffset, currText.length))

		val replacement = CappuccinoElementFactory.createPsiElement(element.project, newText.toString(), CappuccinoElementTypes.VERBATIM_STATEMENT)

		return if (replacement != null) element.replace(replacement) as CappuccinoVerbatimStatement else element
	}

}
