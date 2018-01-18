package com.basmilius.bastools.language.cappuccino.lexer

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.parser.CappuccinoKeywords
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.TokenSet

/**
 * Class CappuccinoLexerAdapter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.lexer
 */
class CappuccinoLexerAdapter: MergingLexerAdapter(FlexAdapter(_CappuccinoLexer(null)), TokensToMerge), CappuccinoKeywords
{

	/**
	 * Class CappuccinoLexerAdapter
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.lexer
	 */
	companion object
	{

		val TokensToMerge = TokenSet.create(CappuccinoTokenTypes.COMMENT, CappuccinoTokenTypes.WHITE_SPACE, CappuccinoTokenTypes.TEMPLATE_HTML_TEXT, CappuccinoTokenTypes.VERBATIM_CONTENT)

	}

}
