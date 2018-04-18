package com.basmilius.bastools.intention

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * Class BTIntentionAction
 *
 * @constructor
 * @param displayName {String}
 * @param startInWriteAction {Boolean}
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.intention
 * @since 1.4.0
 */
abstract class BTIntentionAction(private val displayName: String, private val startInWriteAction: Boolean = false): IntentionAction
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getFamilyName() = "Bas Tools - Intentions"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getText() = this.displayName

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun startInWriteAction() = this.startInWriteAction

	/**
	 * Invokes this intention.
	 *
	 * @param project {Project}
	 * @param editor {Editor}
	 * @param file {PsiFile}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	abstract override fun invoke(project: Project, editor: Editor, file: PsiFile)

	/**
	 * Returns TRUE if this intention is available to invoke.
	 *
	 * @param project {Project}
	 * @param editor {Editor}
	 * @param file {PsiFile}
	 *
	 * @return {Boolean}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	abstract override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean

}
