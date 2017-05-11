package com.basmilius.ps.bastools.framework.identity.intention;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

// TODO(Bas): Figure out how intentions work.

public class ReplaceWpActionWithIdentityActionIntention extends PsiElementBaseIntentionAction
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void invoke (@NotNull final Project project, final Editor editor, @NotNull final PsiElement psiElement) throws IncorrectOperationException
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isAvailable (@NotNull final Project project, final Editor editor, @NotNull final PsiElement psiElement)
	{
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nls
	@NotNull
	@Override
	public final String getFamilyName ()
	{
		return "Convert WordPress add_action to Identity::action.";
	}

}
