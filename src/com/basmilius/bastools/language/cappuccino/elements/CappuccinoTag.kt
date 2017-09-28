package com.basmilius.bastools.language.cappuccino.elements

import com.intellij.psi.tree.IElementType

/**
 * Class CappuccinoTag
 *
 * @constructor
 * @param tagName String
 * @param isStructural Boolean
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
class CappuccinoTag(val tagName: String, val isStructural: Boolean): CappuccinoCompositeElementType("${tagName.toUpperCase()}_TAG")
{

	/**
	 * Companion Object CappuccinoTag
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.elements
	 */
	companion object
	{

		fun isStructural(elementType: IElementType): Boolean = elementType is CappuccinoTag && elementType.isStructural

	}

}
