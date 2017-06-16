package com.basmilius.ps.bastools.framework.identity.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import org.jetbrains.annotations.Nls

// TODO(Bas): Figure out how intentions work.
class ReplaceWpActionWithIdentityActionIntention : PsiElementBaseIntentionAction()
{

	/**
	 * {@inheritDoc}
	 */
	@Throws(IncorrectOperationException::class)
	override fun invoke(project: Project, editor: Editor, psiElement: PsiElement)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	override fun isAvailable(project: Project, editor: Editor, psiElement: PsiElement): Boolean
	{
		return false
	}

	/**
	 * {@inheritDoc}
	 */
	@Nls
	override fun getFamilyName(): String
	{
		return "Convert WordPress add_action to Identity::action."
	}

}
