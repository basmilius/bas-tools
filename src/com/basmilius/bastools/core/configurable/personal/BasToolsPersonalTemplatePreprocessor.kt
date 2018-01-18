package com.basmilius.bastools.core.configurable.personal

import com.intellij.codeInsight.template.impl.TemplatePreprocessor
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

/**
 * Class BasToolsPersonalTemplatePreprocessor
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.3.0
 */
class BasToolsPersonalTemplatePreprocessor: TemplatePreprocessor
{

	override fun preprocessTemplate(editor: Editor, file: PsiFile, caretOffset: Int, textToInsert: String, templateText: String)
	{
		System.out.println(textToInsert)
		System.out.println(templateText)
	}

}
