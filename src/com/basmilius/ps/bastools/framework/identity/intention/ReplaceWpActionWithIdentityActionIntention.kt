package com.basmilius.ps.bastools.framework.identity.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import org.jetbrains.annotations.Nls

/**
 * Class ReplaceWpActionWithIdentityActionIntention
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.intention
 */
class ReplaceWpActionWithIdentityActionIntention : PsiElementBaseIntentionAction()
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	@Throws(IncorrectOperationException::class)
	override fun invoke(project : Project, editor : Editor, psiElement : PsiElement)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun isAvailable(project : Project, editor : Editor, psiElement : PsiElement) : Boolean
	{
		return false
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	@Nls
	override fun getFamilyName() : String
	{
		return "Converts WordPress add_action to Identity::action."
	}

}
