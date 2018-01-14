package com.basmilius.bastools.language.cappuccino

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement

/**
 * Class CappuccinoGoToCappyContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 * @since 1.3.0
 */
class CappuccinoGoToCappyContributor: GotoDeclarationHandler
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, editor: Editor): Array<PsiElement>?
	{
		return null
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun getActionText(context: DataContext) = "Goto Cappuccino template"

}
