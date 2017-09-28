package com.basmilius.bastools.language.cappuccino

import com.basmilius.bastools.language.cappuccino.lexer.CappuccinoLexerAdapter
import com.intellij.lexer.Lexer
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.search.IndexPatternBuilder
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import org.jetbrains.annotations.NotNull

/**
 * Class CappuccinoIndexPatternBuilder
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoIndexPatternBuilder: IndexPatternBuilder
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getCommentEndDelta(p0: IElementType?) = 2

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getCommentStartDelta(p0: IElementType?) = 2

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getCommentTokenSet(@NotNull file: PsiFile): TokenSet = TokenSet.create(CappuccinoTokenTypes.COMMENT)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getIndexingLexer(file: PsiFile): Lexer? = if (file is CappuccinoFile) CappuccinoLexerAdapter() else null

}
