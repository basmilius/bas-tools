package com.basmilius.bastools.language.cappuccino.formatter

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.basmilius.bastools.language.cappuccino.parser.CappuccinoBlockStatements
import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.xml.XmlFormattingPolicy
import com.intellij.xml.template.formatter.AbstractXmlTemplateFormattingModelBuilder
import com.intellij.xml.template.formatter.TemplateLanguageBlock

/**
 * Class CappuccinoBlock
 *
 * @constructor
 * @param builder CappuccinoFormattingModelBuilder
 * @param node ASTNode
 * @param wrap Wrap?
 * @param alignment Alignment?
 * @param settings CodeStyleSettings
 * @param xmlFormattingPolicy XmlFormattingPolicy
 * @param indent Indent
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.formatter
 */
class CappuccinoBlock(builder: AbstractXmlTemplateFormattingModelBuilder, node: ASTNode, wrap: Wrap?, alignment: Alignment?, settings: CodeStyleSettings, xmlFormattingPolicy: XmlFormattingPolicy, indent: Indent?): TemplateLanguageBlock(builder, node, wrap, alignment, settings, xmlFormattingPolicy, indent)
{

	/**
	 * Companion Object CappuccinoBlock
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.formatter
	 */
	companion object
	{

		/**
		 * Creates a single space if {@see addSpace} is TRUE.
		 *
		 * @param addSpace Boolean
		 *
		 * @return Spacing
		 *
		 * @author Bas Milius
		 */
		private fun createSingleSpace(addSpace: Boolean): Spacing
		{
			val spaces = if (addSpace) 1 else 0

			return Spacing.createSpacing(spaces, spaces, 0, false, 0)
		}

	}

	private val options = settings.getCustomSettings(CappuccinoFormatterOptions::class.java)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getChildAttributes(newChildIndex: Int): ChildAttributes
	{
		if (this.myNode.elementType == CappuccinoTokenTypes.COMMENT)
			return ChildAttributes(Indent.getNoneIndent(), null)

		return ChildAttributes(Indent.getNormalIndent(), null)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getChildIndent(childNode: ASTNode): Indent
	{
		val type = this.myNode.elementType
		val firstChildNode = this.myNode.firstChildNode
		val childType = childNode.elementType

		if (type == CappuccinoElementTypes.IF_STATEMENT)
		{
			if (childType != CappuccinoElementTypes.IF_TAG && childType != CappuccinoElementTypes.ELSE_STATEMENT && childType != CappuccinoElementTypes.ELSE_TAG && childType != CappuccinoElementTypes.ENDIF_TAG && childType != CappuccinoElementTypes.ELSEIF_STATEMENT)
				return Indent.getNormalIndent()
		}
		else if (type == CappuccinoElementTypes.ELSE_STATEMENT)
		{
			if (childType != CappuccinoElementTypes.ELSE_TAG && childType != CappuccinoElementTypes.ENDIF_TAG && childType != CappuccinoElementTypes.ENDFOR_TAG)
				return Indent.getNormalIndent()
		}
		else if (type == CappuccinoElementTypes.ELSEIF_STATEMENT)
		{
			if (childType != CappuccinoElementTypes.ELSE_TAG && childType != CappuccinoElementTypes.ELSE_STATEMENT && childType != CappuccinoElementTypes.ENDIF_TAG && childType != CappuccinoElementTypes.ELSEIF_STATEMENT && childType != CappuccinoElementTypes.ELSEIF_TAG)
				return Indent.getNormalIndent()
		}
		else if (type == CappuccinoElementTypes.FOR_STATEMENT)
		{
			if (childType != CappuccinoElementTypes.FOR_TAG && childType != CappuccinoElementTypes.ENDFOR_TAG && childType != CappuccinoElementTypes.ELSE_STATEMENT)
				return Indent.getNormalIndent()
		}
		else if (type == CappuccinoElementTypes.LITERAL && firstChildNode != null)
		{
			if (firstChildNode.elementType == CappuccinoTokenTypes.LBRACE_CURL && childType != CappuccinoTokenTypes.LBRACE_CURL && childType != CappuccinoTokenTypes.RBRACE_CURL)
				return Indent.getNormalIndent()
			else if (childType != CappuccinoTokenTypes.LBRACE_SQ && childType != CappuccinoTokenTypes.RBRACE_SQ)
				return Indent.getNormalIndent()
		}
		else if (type == CappuccinoElementTypes.CAPPUCCINO_STATEMENT)
		{
			if (childType != CappuccinoElementTypes.TAG)
				return Indent.getNormalIndent()
		}
		else if (CappuccinoBlockStatements.isBlockStatement(type))
		{
			val definition = CappuccinoBlockStatements.getStatementDefinitionByType(type)

			if (definition != null && !definition.isStartTag(childType) && !definition.isEndTag(childType))
				return Indent.getNormalIndent()
		}

		return Indent.getNoneIndent()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getSpacing(adjacentBlock: TemplateLanguageBlock): Spacing? = Spacing.getReadOnlySpacing()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getSpacing(child1: Block?, child2: Block): Spacing?
	{
		if (child1 !is ASTBlock)
			return null

		if (child2 !is ASTBlock)
			return null

		val leftNode = child1.node
		val rightNode = child2.node
		val leftType = leftNode.elementType
		val rightType = rightNode.elementType

		if (leftType != CappuccinoTokenTypes.STATEMENT_BLOCK_START && rightType != CappuccinoTokenTypes.STATEMENT_BLOCK_END)
			return if (leftType != CappuccinoTokenTypes.PRINT_BLOCK_START && rightType != CappuccinoTokenTypes.PRINT_BLOCK_END) null else createSingleSpace(this.options.SPACES_INSIDE_VARIABLE_DELIMITERS)

		return createSingleSpace(this.options.SPACES_INSIDE_DELIMITERS)
	}

}
