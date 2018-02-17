/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action.code

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import org.jetbrains.plugins.scss.SCSSFileType

/**
 * Class CappuccinoNewFileAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.action
 * @since 1.3.1
 */
class SCSSNewFileAction: CreateFileFromTemplateAction("SCSS File", "Create a new SCSS File", SCSSFileType.SCSS.icon), DumbAware
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.1
	 */
	override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder)
	{
		val fileType = SCSSFileType.SCSS

		builder.setTitle("New SCSS File")
		builder.addKind("${fileType.name} File", fileType.icon, "${fileType.name} File.${fileType.defaultExtension}")
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.1
	 */
	override fun getActionName(directory: PsiDirectory, newName: String, templateName: String) = "Create a new SCSS File $newName"

}
