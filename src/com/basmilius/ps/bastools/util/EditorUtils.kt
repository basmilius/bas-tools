package com.basmilius.ps.bastools.util

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.*
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.project.Project

/**
 * Object EditorUtils
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.util
 */
object EditorUtils
{

	/**
	 * Inserts or replaces text in the editor.
	 *
	 * @param editor  Editor instance.
	 * @param project Project instance.
	 * @param str     String you want to add/replace.
	 *
	 * @author Bas Milius
	 */
	@Throws(Exception::class)
	fun insertOrReplaceMultiCaret(editor : Editor, project : Project, str : String)
	{
		val wca = object : WriteCommandAction.Simple<Unit>(project)
		{

			@Throws(Throwable::class)
			override fun run()
			{
				val document = editor.document
				val caret = editor.caretModel
				val selection = editor.selectionModel

				if (selection.hasSelection())
				{
					val starts = selection.blockSelectionStarts
					val ends = selection.blockSelectionEnds
					val length = starts.size - 1

					for (i in length downTo 0)
					{
						document.replaceString(starts[i], ends[i], str)
					}
				}
				else if (caret.caretCount > 0)
				{
					for (c in caret.allCarets)
					{
						document.insertString(c.offset, str)
						c.moveToOffset(c.offset + 7)
					}
				}
				else
				{
					throw Exception("No carets found!")
				}
			}

		}
		wca.execute()
	}

	/**
	 * Switches to a specific tab.
	 *
	 * @param project Project
	 * @param context DataContext
	 * @param index Int
	 */
	fun switchToTab(project : Project, context : DataContext, index : Int)
	{
		val editorManager = FileEditorManagerEx.getInstanceEx(project)
		val currentWindow = EditorWindow.DATA_KEY.getData(context) ?: editorManager.currentWindow
		val files = currentWindow.files
		val fileIndex = if (index == -1) (files.size - 1) else index

		if (fileIndex >= 0 && fileIndex < files.size)
		{
			editorManager.openFile(files[fileIndex], true)
		}
	}

}
