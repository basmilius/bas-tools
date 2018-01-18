package com.basmilius.bastools.core.util

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.*
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * Object EditorUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 */
object EditorUtils
{

	/**
	 * Gets a char at a given offset.
	 *
	 * @param document Document
	 * @param offset Int
	 *
	 * @return Char
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun getCharAt(document: Document, offset: Int) = if (offset < document.textLength && offset >= 0) document.charsSequence[offset] else '\u0000'

	/**
	 * Inserts or replaces text in the editor.
	 *
	 * @param editor  Editor instance.
	 * @param project Project instance.
	 * @param str     String you want to add/replace.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	@Throws(Exception::class)
	fun insertOrReplaceMultiCaret(editor: Editor, project: Project, str: String)
	{
		val wca = object: WriteCommandAction.Simple<Unit>(project)
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
	 * Executes a write action in a simple way.
	 *
	 * @param project Project
	 * @param file PsiFile
	 * @param command () -> Unit
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.1
	 */
	fun writeAction(project: Project, file: PsiFile, command: () -> Unit)
	{
		val writer = object: WriteCommandAction.Simple<Unit>(project, file)
		{

			override fun run()
			{
				command()
			}

		}

		writer.execute()
	}

}
