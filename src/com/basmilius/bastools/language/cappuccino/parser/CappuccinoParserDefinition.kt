package com.basmilius.bastools.language.cappuccino.parser

import com.basmilius.bastools.language.cappuccino.CappuccinoFile
import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoCompositeElementType
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.basmilius.bastools.language.cappuccino.lexer.CappuccinoLexerAdapter
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet

/**
 * Class CappuccinoParserDefinition
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.parser
 */
class CappuccinoParserDefinition: ParserDefinition
{

	/**
	 * Companion Object CappuccinoParserDefinition
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.parser
	 */
	companion object
	{

		val FILE_NODE_TYPE = CappuccinoElementTypes.CAPPUCCINO_FILE
		val COMMENT = TokenSet.create(CappuccinoTokenTypes.COMMENT)
		val STRING_LITERALS = CappuccinoTokenTypes.STRING_LITERALS
		val WHITE_SPACE = TokenSet.create(CappuccinoTokenTypes.WHITE_SPACE)

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createElement(node: ASTNode): PsiElement
	{
		val type = node.elementType

		if (type !is CappuccinoCompositeElementType)
			throw AssertionError("Unknown type: $type")

		return type.createPsiElement(node)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createFile(viewProvider: FileViewProvider) = CappuccinoFile(viewProvider)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createLexer(project: Project) = CappuccinoLexerAdapter()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createParser(project: Project) = CappuccinoParser()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getCommentTokens() = COMMENT

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getFileNodeType() = FILE_NODE_TYPE

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getStringLiteralElements() = STRING_LITERALS

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getWhitespaceTokens() = WHITE_SPACE

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode) = ParserDefinition.SpaceRequirements.MAY

}
