package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.CappuccinoLanguage
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

/**
 * Class CappuccinoCompositeElement
 *
 * @constructor
 * @param node ASTNode
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
open class CappuccinoCompositeElement(node: ASTNode): ASTWrapperPsiElement(node)
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getLanguage() = CappuccinoLanguage.Instance

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getNode() = super.getNode()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun toString() = super.getNode().elementType.toString()

}
