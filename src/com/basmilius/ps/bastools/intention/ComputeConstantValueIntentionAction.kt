package com.basmilius.ps.bastools.intention

import com.basmilius.math.mxparser.Expression
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.impl.AssignmentExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.BinaryExpressionImpl

/**
 * Class ComputeConstantValueIntentionAction
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.intention
 */
class ComputeConstantValueIntentionAction : IntentionAction
{

	/**
	 * Returns TRUE if this intention should be available in write action.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	override fun startInWriteAction() : Boolean
	{
		return false
	}

	/**
	 * Gets the family name.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	override fun getFamilyName() : String
	{
		return "Bas Tools - Intentions"
	}

	/**
	 * Returns TRUE if the intention should be available.
	 *
	 * @param project Project
	 * @param editor Editor?
	 * @param file PsiFile?
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	override fun isAvailable(project : Project, editor : Editor?, file : PsiFile?) : Boolean
	{
		if (editor == null)
			return false

		if (file == null)
			return false

		val caretModel = editor.caretModel

		for (i in (caretModel.caretCount - 1) downTo 0)
		{
			val caret = caretModel.allCarets[i]
			val psi = file.findElementAt(caret.offset)
			var canWalk = true
			var expression : PsiElement? = PsiTreeUtil.getParentOfType(psi, BinaryExpressionImpl::class.java)

			if (expression == null)
				expression = PsiTreeUtil.getPrevSiblingOfType(psi, BinaryExpressionImpl::class.java)

			if (expression == null)
			{
				expression = PsiTreeUtil.getPrevSiblingOfType(psi, AssignmentExpressionImpl::class.java)
				expression = PsiTreeUtil.findChildOfType(expression, BinaryExpressionImpl::class.java)
			}

			if (expression != null)
			{
				while (canWalk)
				{
					val parent = PsiTreeUtil.getParentOfType(expression, BinaryExpressionImpl::class.java)
					if (parent != null)
					{
						expression = parent
						continue
					}

					canWalk = false
				}

				val expressionText = expression!!.text

				if (expressionText.isEmpty())
					return false
			}
			else
				return false
		}

		return true
	}

	/**
	 * Gets the intention label.
	 *
	 * @author Bas Milius
	 */
	override fun getText() : String
	{
		return "Compute Constant Value"
	}

	/**
	 * Invoked when the intention is invoked.
	 *
	 * @param project Project
	 * @param editor Editor?
	 * @param file PsiFile?
	 *
	 * @author Bas Milius
	 */
	override fun invoke(project : Project, editor : Editor?, file : PsiFile?)
	{
		if (editor == null)
			return

		if (file == null)
			return

		val caretModel = editor.caretModel

		for (i in (caretModel.caretCount - 1) downTo 0)
		{
			val caret = caretModel.allCarets[i]
			val psi = file.findElementAt(caret.offset)
			var canWalk = true
			var expression : PsiElement? = PsiTreeUtil.getParentOfType(psi, BinaryExpressionImpl::class.java)

			if (expression == null)
				expression = PsiTreeUtil.getPrevSiblingOfType(psi, BinaryExpressionImpl::class.java)

			if (expression == null)
			{
				expression = PsiTreeUtil.getPrevSiblingOfType(psi, AssignmentExpressionImpl::class.java)
				expression = PsiTreeUtil.findChildOfType(expression, BinaryExpressionImpl::class.java)
			}

			if (expression != null)
			{
				while (canWalk)
				{
					val parent = PsiTreeUtil.getParentOfType(expression, BinaryExpressionImpl::class.java)
					if (parent != null)
					{
						expression = parent
						continue
					}

					canWalk = false
				}

				val expressionText = expression!!.text

				try
				{
					val expressionCalculator = Expression(expressionText)
					val computed = expressionCalculator.calculate().toString()

					if (computed != "NaN")
					{
						var result = computed
						if (result.endsWith(".0"))
							result = result.replace(".0", "")

						ApplicationManager.getApplication().runWriteAction { editor.document.replaceString(expression!!.textOffset, expression!!.textOffset + expressionText.length, result) }
					}
				}
				catch (err : Exception)
				{
				}

			}
		}
	}

}
