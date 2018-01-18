package com.basmilius.bastools.language.cappuccino.parser

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoTag
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType

/**
 * Class CappuccinoPsiBuilder
 *
 * @constructor
 * @param builder PsiBuilder
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.parser
 */
class CappuccinoPsiBuilder(private val builder: PsiBuilder): CappuccinoElementTypes
{

	/**
	 * Companion Object CappuccinoPsiBuilder
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.parser
	 */
	companion object
	{

		private val TAG_NAME_TO_TYPE_MAP = HashMap<String, IElementType>()

		/**
		 * CappuccinoPsiBuilder Constructor.
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		init
		{
			CappuccinoElementTypes.TAGS.types
					.filterIsInstance<CappuccinoTag>()
					.forEach { TAG_NAME_TO_TYPE_MAP.put(it.tagName, it) }

			TAG_NAME_TO_TYPE_MAP.put("from", CappuccinoElementTypes.IMPORT_TAG)
		}

		/**
		 * Gets the mapped tag type.
		 *
		 * @param tokenText String?
		 *
		 * @return IElementType
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		fun getTagType(tokenText: String?): IElementType
		{
			return TAG_NAME_TO_TYPE_MAP[tokenText] ?: return CappuccinoElementTypes.TAG
		}

	}

	/**
	 * Builds the actual PSI Tree.
	 *
	 * @param root IElementType
	 *
	 * @return ASTNode
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun buildPsiTree(root: IElementType): ASTNode
	{
		val marker = this.builder.mark()

		while (!this.builder.eof())
			when
			{
				this.builder.tokenType == CappuccinoTokenTypes.STATEMENT_BLOCK_START -> this.parseStatement(null, setOf())
				this.builder.tokenType == CappuccinoTokenTypes.PRINT_BLOCK_START -> this.parsePrintBlock()
				else -> this.builder.advanceLexer()
			}

		marker.done(root)
		return this.builder.treeBuilt
	}

	/**
	 * Parses a literal.
	 *
	 * @param type IElementType
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun parseLiteral(type: IElementType)
	{
		val literalMarker = this.builder.mark()
		this.builder.advanceLexer()

		while (!this.builder.eof() && this.builder.tokenType != CappuccinoTokenTypes.PRINT_BLOCK_END)
		{
			if (this.builder.tokenType == type)
			{
				this.builder.advanceLexer()
				literalMarker.done(CappuccinoElementTypes.LITERAL)
				return
			}

			when
			{
				this.builder.tokenType == CappuccinoTokenTypes.LBRACE_CURL -> this.parseLiteral(CappuccinoTokenTypes.RBRACE_CURL)
				this.builder.tokenType == CappuccinoTokenTypes.LBRACE_SQ -> this.parseLiteral(CappuccinoTokenTypes.RBRACE_SQ)
				else -> this.builder.advanceLexer()
			}
		}

		literalMarker.drop()
		return this.builder.error("Unclosed literal")
	}

	/**
	 * Parses a print block.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun parsePrintBlock()
	{
		assert(this.builder.tokenType == CappuccinoTokenTypes.PRINT_BLOCK_START)

		val printBlockMarker = this.builder.mark()
		this.builder.advanceLexer()

		while (!this.builder.eof())
		{
			if (this.builder.tokenType == CappuccinoTokenTypes.PRINT_BLOCK_END)
			{
				this.builder.advanceLexer()
				break
			}

			when
			{
				this.builder.tokenType == CappuccinoTokenTypes.LBRACE_CURL -> this.parseLiteral(CappuccinoTokenTypes.RBRACE_CURL)
				this.builder.tokenType == CappuccinoTokenTypes.LBRACE_SQ -> this.parseLiteral(CappuccinoTokenTypes.RBRACE_SQ)
				else -> this.builder.advanceLexer()
			}
		}

		printBlockMarker.done(CappuccinoElementTypes.PRINT_BLOCK)
	}

	/**
	 * Parses a statement.
	 *
	 * @param definition CappuccinoBlockStatements.StatementDefinition
	 * @param withIdentifierOnly Boolean
	 *
	 * @return IElementType
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun parseStatement(definition: CappuccinoBlockStatements.StatementDefinition, withIdentifierOnly: Boolean): IElementType
	{
		var childStatementType: IElementType? = null
		val statementType = definition.statementType

		if (definition.mayBeShort && !withIdentifierOnly)
			return statementType

		val currentMarker = this.builder.mark()

		while (!this.builder.eof())
		{
			if (definition.isTerminatedBefore(childStatementType))
			{
				currentMarker.rollbackTo()
				return statementType
			}

			if (definition.isTerminatedOn(childStatementType))
			{
				currentMarker.drop()
				return statementType
			}

			currentMarker.drop()
			this.builder.mark()

			when
			{
				this.builder.tokenType == CappuccinoTokenTypes.STATEMENT_BLOCK_START -> childStatementType = this.parseStatement(null, definition.endTagTypes)?.tagType
				this.builder.tokenType == CappuccinoTokenTypes.PRINT_BLOCK_START -> this.parsePrintBlock()
				else -> this.builder.advanceLexer()
			}
		}

		currentMarker.drop()
		return statementType
	}

	/**
	 * Parses a statement.
	 *
	 * @param name String
	 * @param endName String?
	 * @param types Set<IElementType>
	 *
	 * @return IElementType?
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun parseStatement(name: String, endName: String?, types: Set<IElementType>): IElementType?
	{
		var childStatementData: CappuccinoTagParsingData? = null
		var currentMarker = this.builder.mark()

		while (!this.builder.eof())
		{
			if (childStatementData != null && childStatementData.tagType == CappuccinoElementTypes.TAG && childStatementData.name == "end$name")
			{
				currentMarker.drop()
				return CappuccinoElementTypes.CAPPUCCINO_STATEMENT
			}

			if (childStatementData != null && (endName != null && endName == childStatementData.name || types.contains(childStatementData.tagType)))
			{
				currentMarker.rollbackTo()
				return null
			}

			currentMarker.drop()
			currentMarker = this.builder.mark()

			when
			{
				this.builder.tokenType == CappuccinoTokenTypes.STATEMENT_BLOCK_START -> childStatementData = this.parseStatement(name, setOf())
				this.builder.tokenType == CappuccinoTokenTypes.PRINT_BLOCK_START -> this.parsePrintBlock()
				else -> this.builder.advanceLexer()
			}
		}

		currentMarker.drop()
		return null
	}

	/**
	 * Parses a statement.
	 *
	 * @param name String?
	 * @param types Set<IElementType>
	 *
	 * @return CappuccinoTagParsingData?
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun parseStatement(name: String?, types: Set<IElementType>): CappuccinoTagParsingData?
	{
		val statementMarker = this.builder.mark()
		val tagData = this.parseTag()

		if (tagData != null)
		{
			val statementDefinition = CappuccinoBlockStatements.getStatementDefinitionByStartTag(tagData.tagType)

			if (statementDefinition != null)
			{
				val type = this.parseStatement(statementDefinition, tagData.isShort)
				statementMarker.done(type)
				return CappuccinoTagParsingData(type, tagData.isShort, tagData.name)
			}

			if (!tagData.name.startsWith("end"))
			{
				val type = this.parseStatement(tagData.name, name, types)

				if (type != null)
				{
					statementMarker.done(type)
					return CappuccinoTagParsingData(type, false, tagData.name)
				}
			}
		}

		statementMarker.drop()
		return tagData
	}

	/**
	 * Parses a tag.
	 *
	 * @return CappuccinoTagParsingData?
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun parseTag(): CappuccinoTagParsingData?
	{
		assert(this.builder.tokenType == CappuccinoTokenTypes.STATEMENT_BLOCK_START)

		val marker = this.builder.mark()

		this.builder.advanceLexer()

		if (this.builder.tokenType != CappuccinoTokenTypes.TAG_NAME)
		{
			marker.error("A block must start with a tag name!")
			return null
		}

		val name = this.builder.tokenText!!
		val tagType = getTagType(name)
		var isShortBlock = true

		this.builder.advanceLexer()

		if (this.builder.tokenType == CappuccinoTokenTypes.IDENTIFIER)
			this.builder.advanceLexer()

		while (this.builder.tokenType != CappuccinoTokenTypes.STATEMENT_BLOCK_END && !this.builder.eof())
		{
			isShortBlock = false
			this.builder.advanceLexer()

			when
			{
				this.builder.tokenType == CappuccinoTokenTypes.LBRACE_CURL -> this.parseLiteral(CappuccinoTokenTypes.RBRACE_CURL)
				this.builder.tokenType == CappuccinoTokenTypes.LBRACE_SQ -> this.parseLiteral(CappuccinoTokenTypes.RBRACE_SQ)
			}
		}

		if (this.builder.tokenType == CappuccinoTokenTypes.STATEMENT_BLOCK_END)
			this.builder.advanceLexer()

		marker.done(tagType)
		return CappuccinoTagParsingData(tagType, isShortBlock, name)
	}

}
