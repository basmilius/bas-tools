package com.basmilius.bastools.intention

import com.basmilius.math.Expression
import com.intellij.codeInsight.hint.HintManager
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.ParenthesizedExpression
import com.jetbrains.php.lang.psi.elements.impl.AssignmentExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.BinaryExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.ConcatenationExpressionImpl

/**
 * Class ComputeConstantValueIntentionAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.intention
 * @since 1.0.3
 */
class ComputeConstantValueIntentionAction: IntentionAction
{

	/**
	 * Returns TRUE if this intention should be available in write action.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.3
	 */
	override fun startInWriteAction() = false

	/**
	 * Gets the family name.
	 *
	 * @return String
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.3
	 */
	override fun getFamilyName() = "Bas Tools - Intentions"

	/**
	 * Returns TRUE if the intention should be available.
	 *
	 * @param project Project
	 * @param editor Editor?
	 * @param file PsiFile?
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.3
	 */
	override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean
	{
		if (editor == null)
			return false

		if (file == null)
			return false

		return this.run(editor, file, false)
	}

	/**
	 * Gets the intention label.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getText() = "Compute Constant Value"

	/**
	 * Invoked when the intention is invoked.
	 *
	 * @param project Project
	 * @param editor Editor?
	 * @param file PsiFile?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.3
	 */
	override fun invoke(project: Project, editor: Editor?, file: PsiFile?)
	{
		if (editor == null)
			return

		if (file == null)
			return

		this.run(editor, file)
	}

	/**
	 * Runs the compute constant value handler.
	 *
	 * @param editor Editor
	 * @param file PsiFile
	 * @param run Boolean
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.3
	 */
	private fun run(editor: Editor, file: PsiFile, run: Boolean = true): Boolean
	{
		val caretModel = editor.caretModel

		for (i in (caretModel.caretCount - 1) downTo 0)
		{
			val caret = caretModel.allCarets[i]
			val psi = file.findElementAt(caret.offset)
			var canWalk = true
			var expression: PsiElement? = PsiTreeUtil.getParentOfType(psi, BinaryExpressionImpl::class.java)

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
						if (parent is ConcatenationExpressionImpl && expression is BinaryExpressionImpl)
						{
							canWalk = false
							continue
						}

						expression = parent
						continue
					}

					canWalk = false
				}

				if (expression?.parent != null && expression.parent is ParenthesizedExpression)
				{
					expression = expression.parent
				}

				val expressionText = expression!!.text

				if (run)
				{
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
					catch (err: Exception)
					{
					}
				}
				else if (expressionText.isEmpty())
				{
					return false
				}
			}
			else if (!run)
			{
				return false
			}
		}

		if (run)
		{
			val pluralValueValues = if (caretModel.caretCount > 1) "Values" else "Value"
			HintManager.getInstance().showInformationHint(editor, "Computed Constant $pluralValueValues!")
		}

		return true
	}

}
