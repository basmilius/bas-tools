/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

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
