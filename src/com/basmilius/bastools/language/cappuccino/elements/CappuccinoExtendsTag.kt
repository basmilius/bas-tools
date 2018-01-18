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
