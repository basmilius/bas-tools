package com.basmilius.bastools.language.cappuccino.elements.manipulators

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockTag
import com.intellij.openapi.util.TextRange
import com.intellij.psi.ElementManipulator

/**
 * Class CappuccinoBlockTagManipulator
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements.manipulators
 */
class CappuccinoBlockTagManipulator: ElementManipulator<CappuccinoBlockTag>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getRangeInElement(element: CappuccinoBlockTag): TextRange
	{
		throw RuntimeException("Not Implemented! -CappuccinoBlockTagManipulator")
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun handleContentChange(element: CappuccinoBlockTag, newContent: String?): CappuccinoBlockTag? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun handleContentChange(element: CappuccinoBlockTag, range: TextRange, newContent: String): CappuccinoBlockTag
	{
		element.setName(newContent)

		return element
	}

}
