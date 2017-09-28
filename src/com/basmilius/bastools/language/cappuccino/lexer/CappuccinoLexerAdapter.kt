package com.basmilius.bastools.language.cappuccino.lexer

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.TokenSet

/**
 * Class CappuccinoLexerAdapter
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.lexer
 */
class CappuccinoLexerAdapter: MergingLexerAdapter(FlexAdapter(CappuccinoLexer()), TokensToMerge)
{

	/**
	 * Class CappuccinoLexerAdapter
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.lexer
	 */
	companion object
	{

		val TokensToMerge = TokenSet.create(CappuccinoTokenTypes.COMMENT, CappuccinoTokenTypes.WHITE_SPACE, CappuccinoTokenTypes.TEMPLATE_HTML_TEXT, CappuccinoTokenTypes.VERBATIM_CONTENT)

	}

}
