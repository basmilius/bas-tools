package com.basmilius.bastools.language.cappuccino.elements.manipulators

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoTagWithFileReference
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator

/**
 * Class CappuccinoFileReferenceManipulator
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements.manipulators
 */
class CappuccinoFileReferenceManipulator: AbstractElementManipulator<CappuccinoTagWithFileReference>()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun handleContentChange(element: CappuccinoTagWithFileReference, range: TextRange, newContent: String): CappuccinoTagWithFileReference
	{
		element.changeFileName(newContent)

		return element
	}

}
