/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.elements

import com.intellij.psi.tree.IElementType

/**
 * Class CappuccinoTag
 *
 * @constructor
 * @param tagName String
 * @param isStructural Boolean
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
class CappuccinoTag(val tagName: String, val isStructural: Boolean): CappuccinoCompositeElementType("${tagName.toUpperCase()}_TAG")
{

	/**
	 * Companion Object CappuccinoTag
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.elements
	 */
	companion object
	{

		fun isStructural(elementType: IElementType): Boolean = elementType is CappuccinoTag && elementType.isStructural

	}

}
