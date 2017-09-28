package com.basmilius.bastools.language.cappuccino

import com.basmilius.bastools.language.cappuccino.formatter.CappuccinoFormatterOptions
import com.basmilius.bastools.util.EditorUtils
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettingsManager

/**
 * Class CappuccinoTypedHandler
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoTypedHandler: TypedHandlerDelegate()
{

	/**
	 * Companion Object CappuccinoTypedHandler
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino
	 */
	companion object
	{

		/**
		 * Returns TRUE if a closing sequence is needed.
		 *
		 * @param editor Editor
		 * @param afterOffset Int
		 * @param openingChar Char
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		fun isClosingSequenceNeeded(editor: Editor, afterOffset: Int, openingChar: Char): Boolean
		{
			val closingChar = if (openingChar == '{') 125.toChar() else openingChar
			val docCharSequence = editor.document.charsSequence

			for (offset in afterOffset until docCharSequence.length)
			{
				val currChar = docCharSequence[offset]
				val nextChar = if (offset + 1 < docCharSequence.length) docCharSequence[offset + 1] else 0.toChar()

				if (currChar == '{' && nextChar == openingChar)
					return true

				if (currChar == closingChar && nextChar == '}')
					return false
			}

			return false
		}

		/**
		 * Returns TRUE if whitespace is required.
		 *
		 * @param project Project
		 * @param char Char
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		fun isWhitespaceRequired(project: Project, char: Char): Boolean
		{
			val settings = CodeStyleSettingsManager.getInstance(project).currentSettings
			val options = settings.getCustomSettings(CappuccinoFormatterOptions::class.java)

			if (options == null || options !is CappuccinoFormatterOptions)
				return false

			return when (char)
			{
				'%' -> options.SPACES_INSIDE_DELIMITERS
				'{' -> options.SPACES_INSIDE_VARIABLE_DELIMITERS
				else -> false
			}
		}

		/**
		 * Types a string and moves the caret.
		 *
		 * @param editor Editor
		 * @param str String
		 * @param caretShift Int
		 *
		 * @author Bas Milius
		 */
		fun typeInStringAndMoveCaret(editor: Editor, str: String, caretShift: Int)
		{
			EditorModificationUtil.insertStringAtCaret(editor, str, true, caretShift)
		}

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun beforeCharTyped(c: Char, project: Project, editor: Editor, file: PsiFile, fileType: FileType): Result = when
	{
		file.viewProvider !is CappuccinoFileViewProvider -> Result.CONTINUE

		c != '{' && c != '%' && c != '#' -> Result.CONTINUE

		else ->
		{
			val offset = editor.caretModel.offset
			val charBefore = EditorUtils.getCharAt(editor.document, offset - 1)
			val charAfter = EditorUtils.getCharAt(editor.document, offset)

			if (charBefore == '{')
			{
				val buf = StringBuilder()
				val space = if (isWhitespaceRequired(project, c)) " " else ""

				buf.append(c).append(space)

				if (isClosingSequenceNeeded(editor, offset, c))
				{
					buf.append(space).append(if (c == '{') '}' else c)

					if (charAfter != '}')
						buf.append('}')
				}

				typeInStringAndMoveCaret(editor, buf.toString(), 1 + space.length)

				val documentManager = PsiDocumentManager.getInstance(project)
				documentManager.commitDocument(editor.document)

				Result.STOP
			}
			else
			{
				Result.CONTINUE
			}
		}
	}

}
