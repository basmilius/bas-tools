/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.elements.manipulators

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockTag
import com.intellij.openapi.util.TextRange
import com.intellij.psi.ElementManipulator

/**
 * Class CappuccinoBlockTagManipulator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements.manipulators
 */
class CappuccinoBlockTagManipulator: ElementManipulator<CappuccinoBlockTag>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getRangeInElement(element: CappuccinoBlockTag): TextRange
	{
		throw RuntimeException("Not Implemented! -CappuccinoBlockTagManipulator")
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun handleContentChange(element: CappuccinoBlockTag, newContent: String?): CappuccinoBlockTag? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun handleContentChange(element: CappuccinoBlockTag, range: TextRange, newContent: String): CappuccinoBlockTag
	{
		element.setName(newContent)

		return element
	}

}
