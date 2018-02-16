/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.elements.manipulators

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoTagWithFileReference
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator

/**
 * Class CappuccinoFileReferenceManipulator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements.manipulators
 */
class CappuccinoFileReferenceManipulator: AbstractElementManipulator<CappuccinoTagWithFileReference>()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun handleContentChange(element: CappuccinoTagWithFileReference, range: TextRange, newContent: String): CappuccinoTagWithFileReference
	{
		element.changeFileName(newContent)

		return element
	}

}
