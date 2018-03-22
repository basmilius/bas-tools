/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action.editor

import com.basmilius.bastools.core.util.CodeFragmentPictureUtils
import com.intellij.codeInsight.hint.EditorFragmentComponent
import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.Transferable

/**
 * Class CodeShotAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.textEditor
 * @since 1.1.0
 */
class CodeShotAction: AnAction("Code Shot"), ClipboardOwner
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val project = aae.project ?: return
		val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
		val document = editor.document
		val selectionModel = editor.selectionModel

		if (selectionModel.blockSelectionStarts.size >= 2)
		{
			HintManager.getInstance().showErrorHint(editor, "CodeShot is not available with multiple selections at this moment.")
			return
		}

		val start = selectionModel.selectionStart
		val end = selectionModel.selectionEnd
		val startLine = document.getLineNumber(start)
		val endLine = document.getLineNumber(end) + 1

		selectionModel.removeSelection()

		val fragment = EditorFragmentComponent.createEditorFragmentComponent(editor, startLine, endLine, false, false)

		try
		{
			val clipboard = Toolkit.getDefaultToolkit().systemClipboard
			val picture = CodeFragmentPictureUtils.createBufferedImage(fragment)
			val transferable = CodeFragmentPictureUtils.TransferableImage(picture)

			clipboard.setContents(transferable, this)
		}
		catch (err: Exception)
		{
			err.printStackTrace()
		}

		selectionModel.setSelection(start, end)

		HintManager.getInstance().showInformationHint(editor, "Successfully took a CodeShot!")
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun update(aae: AnActionEvent)
	{
		val project = aae.getData(LangDataKeys.PROJECT)
		val editor = aae.getData(LangDataKeys.EDITOR)

		aae.presentation.isEnabled = project != null && editor != null && editor.selectionModel.hasSelection()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun lostOwnership(clipboard: Clipboard?, contents: Transferable?)
	{
	}

}
