/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.folding

import com.basmilius.bastools.language.cappuccino.CappuccinoFile
import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoTag
import com.basmilius.bastools.language.cappuccino.parser.CappuccinoBlockStatements
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType

/**
 * Class CappuccinoFoldingBuilder
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.intellij.lang.folding.CustomFoldingBuilder
 */
class CappuccinoFoldingBuilder: CustomFoldingBuilder()
{

	/**
	 * Companion Object CappuccinoFoldingBuilder
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.intellij.lang.folding.CustomFoldingBuilder
	 */
	companion object
	{

		/**
		 * Collects the folding regions for an {@see element}.
		 *
		 * @param element PsiElement
		 * @param descriptors ArrayList<FoldingDescriptor>
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		private fun collectCappuccinoFoldingRegions(element: PsiElement, descriptors: MutableList<FoldingDescriptor>)
		{
			var child: PsiElement? = element.firstChild
			while (child != null)
			{
				val childType = child.node.elementType
				if (isFoldable(childType))
				{
					val lastChild = child.lastChild
					if (lastChild != null && (CappuccinoTag.isStructural(lastChild.node.elementType) || lastChild.node.elementType === CappuccinoElementTypes.TAG) || isMultilineComment(child))
						descriptors.add(FoldingDescriptor(child, child.textRange))
				}

				collectCappuccinoFoldingRegions(child, descriptors)
				child = child.nextSibling
			}
		}

		/**
		 * Gets placeholder text for the {@see topNode}.
		 *
		 * @param topNode ASTNode
		 *
		 * @return String
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		private fun getBlockPlaceholderText(topNode: ASTNode): String
		{
			if (topNode.elementType == CappuccinoTokenTypes.COMMENT)
				return getFoldedComment(topNode)

			var child = topNode.firstChildNode

			if (child.elementType !is CappuccinoTag)
				return "???"

			val buffer = StringBuilder()

			child = child.firstChildNode
			while (child != null && child.elementType !== CappuccinoTokenTypes.STATEMENT_BLOCK_END)
			{
				buffer.append(child.text)
				child = child.treeNext
			}

			buffer.append("...%}")

			return buffer.toString()
		}

		/**
		 * Gets the folded comment text.
		 *
		 * @param commentNode ASTNode
		 *
		 * @return String
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		private fun getFoldedComment(commentNode: ASTNode): String
		{
			val commentText = commentNode.chars
			val buffer = StringBuilder()
			var previous = 0.toChar()

			for (i in 0 until commentText.length)
			{
				val c = commentText[i]
				if (c == '\n')
				{
					if (buffer.length > 2)
						break
				}
				else
				{
					if (c == '}' && previous == '#')
					{
						buffer.deleteCharAt(buffer.length - 1)
						break
					}

					buffer.append(c)
				}

				previous = c
			}

			buffer.append("...")
			buffer.append("#}")

			return buffer.toString()
		}

		/**
		 * Returns TRUE if {@see nodeType} is foldable.
		 *
		 * @param nodeType IElementType
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		private fun isFoldable(nodeType: IElementType): Boolean
		{
			if (nodeType != CappuccinoElementTypes.ELSE_STATEMENT && nodeType != CappuccinoElementTypes.ELSEIF_STATEMENT)
				return if (nodeType == CappuccinoTokenTypes.COMMENT) true else CappuccinoBlockStatements.isBlockStatement(nodeType)

			return false
		}

		/**
		 * Returns TRUE if the {@see comment} is multiline.
		 *
		 * @param comment PsiElement
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		private fun isMultilineComment(comment: PsiElement): Boolean
		{
			if (comment.node.elementType == CappuccinoTokenTypes.COMMENT && comment.textContains('\n'))
				return StringUtil.countChars(comment.node.chars, '\n') > 1

			return false
		}

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun buildLanguageFoldRegions(descriptors: MutableList<FoldingDescriptor>, root: PsiElement, document: Document, quick: Boolean)
	{
		if (root !is CappuccinoFile)
			return

		collectCappuccinoFoldingRegions(root, descriptors)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange) = if (isFoldable(node.elementType)) getBlockPlaceholderText(node) else "..."

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isRegionCollapsedByDefault(node: ASTNode) = false

}
