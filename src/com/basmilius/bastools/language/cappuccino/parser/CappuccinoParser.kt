package com.basmilius.bastools.language.cappuccino.parser

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

/**
 * Class CappuccinoParser
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.parser
 */
class CappuccinoParser: PsiParser, CappuccinoElementTypes
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun parse(root: IElementType, builder: PsiBuilder) = CappuccinoPsiBuilder(builder).buildPsiTree(root)

}