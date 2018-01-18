package com.basmilius.bastools.language.cappuccino.action

import com.basmilius.bastools.language.cappuccino.CappuccinoFileType
import com.basmilius.bastools.resource.Icons
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

/**
 * Class CappuccinoNewFileAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.action
 * @since 1.3.1
 */
class CappuccinoNewFileAction: CreateFileFromTemplateAction("Cappuccino Template", "Creates a new Cappuccino Template File", Icons.CappuccinoFile), DumbAware
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.1
	 */
	override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder)
	{
		val fileType = CappuccinoFileType.Instance

		builder.setTitle("New Cappuccino Template")
		builder.addKind("${fileType.name} Template", fileType.icon, "${fileType.name} File.${fileType.defaultExtension}")
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.1
	 */
	override fun getActionName(directory: PsiDirectory, newName: String, templateName: String) = "Create a new Cappuccino Template File $newName"

}
