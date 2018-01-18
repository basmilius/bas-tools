package com.basmilius.bastools.language.cappuccino

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Class CappuccinoPairedBraceMatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoPairedBraceMatcher: PairedBraceMatcher
{

	/**
	 * Companion Object CappuccinoPairedBraceMatcher
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino
	 */
	companion object
	{

		val Pairs = arrayOf(BracePair(CappuccinoTokenTypes.LBRACE, CappuccinoTokenTypes.RBRACE, false), BracePair(CappuccinoTokenTypes.LBRACE_SQ, CappuccinoTokenTypes.RBRACE_SQ, false), BracePair(CappuccinoTokenTypes.LBRACE_CURL, CappuccinoTokenTypes.RBRACE_CURL, false), BracePair(CappuccinoTokenTypes.PRINT_BLOCK_START, CappuccinoTokenTypes.PRINT_BLOCK_END, true), BracePair(CappuccinoTokenTypes.STATEMENT_BLOCK_START, CappuccinoTokenTypes.STATEMENT_BLOCK_END, true))

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	@NotNull
	override fun getPairs(): Array<BracePair> = Pairs

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isPairedBracesAllowedBeforeType(@NotNull lbraceType: IElementType, @Nullable tokenType: IElementType?): Boolean = true

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int = openingBraceOffset

}
