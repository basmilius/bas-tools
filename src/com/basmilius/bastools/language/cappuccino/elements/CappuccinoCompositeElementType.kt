/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.CappuccinoLanguage
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.CompositeElement
import com.intellij.psi.tree.ICompositeElementType
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

/**
 * Class CappuccinoCompositeElementType
 *
 * @constructor
 * @param debugName String
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
open class CappuccinoCompositeElementType(@NonNls debugName: String): IElementType(debugName, CappuccinoLanguage.Instance), ICompositeElementType
{

	/**
	 * Creates a PSI element by type.
	 *
	 * @param node ASTNode
	 *
	 * @return PsiElement
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun createPsiElement(node: ASTNode): PsiElement
	{
		val type = node.elementType

		return when
		{
			type == CappuccinoElementTypes.BLOCK_TAG -> CappuccinoBlockTag(node)
			type == CappuccinoElementTypes.EXTENDS_TAG -> CappuccinoExtendsTag(node)
			CappuccinoElementTypes.TAGS_WITH_FILE_REF.contains(type) -> CappuccinoTagWithFileReference(node)
			type == CappuccinoElementTypes.VERBATIM_STATEMENT -> CappuccinoVerbatimStatement(node)
			else -> CappuccinoCompositeElement(node)
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun createCompositeNode(): ASTNode = CompositeElement(this)

}
