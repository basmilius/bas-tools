/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.elements

import com.intellij.lang.ASTNode

/**
 * Class CappuccinoExtendsTag
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
class CappuccinoExtendsTag(node: ASTNode): CappuccinoTagWithFileReference(node)
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>-
	 */
	override fun toString(): String
	{
		val fileNameElement = this.findFileNameElement() ?: return "ExtendsTag('?')"

		return "ExtendsTag('${fileNameElement.text}')"
	}

}
