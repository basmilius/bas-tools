/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression

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
		if (sourceElement == null)
			return null

		val project = editor.project ?: return null

		val renderMethod = PsiTreeUtil.getContextOfType(sourceElement, MethodReference::class.java) ?: return null

		if (renderMethod.name != "render")
			return null

		if (renderMethod.parameterList == null)
			return null

		val firstParameter = renderMethod.parameterList!!.parameters[0] as? StringLiteralExpression ?: return null
		val templateName = firstParameter.contents.replace("@([a-z0-9]+)\\/".toRegex(), "") // TODO(Bas): Add support for filesystem namespaces.

		val cappuccinoFiles = FilenameIndex.getAllFilesByExt(project, "cappy")
				.filter { it.path.endsWith(templateName) }
				.mapNotNull { PsiManager.getInstance(project).findFile(it) }

		return cappuccinoFiles.toTypedArray()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun getActionText(context: DataContext) = "Goto Cappuccino template"

}
