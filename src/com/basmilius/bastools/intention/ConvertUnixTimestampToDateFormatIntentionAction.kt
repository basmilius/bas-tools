package com.basmilius.bastools.intention

import com.basmilius.bastools.core.util.EditorUtils
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import java.lang.Double.parseDouble
import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * Class ConvertUnixTimestampToDateFormatIntentionAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.intention
 * @since 1.4.0
 */
class ConvertUnixTimestampToDateFormatIntentionAction: BTIntentionAction("Convert to Date Format...")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun invoke(project: Project, editor: Editor, file: PsiFile)
	{
		val timestamps = this.getUnixTimestamp(editor, file)

		// TODO(Bas): Show editor hint that no unix timestamps were found.
		if (timestamps.isEmpty())
			return

		val formatPattern = Messages.showInputDialog(project, "What date format should we use?", "Date format", Messages.getQuestionIcon(), "yyyy-MM-dd HH:mm:ss", null)
		val format = SimpleDateFormat(formatPattern)

		EditorUtils.writeAction(project) {
			timestamps.forEach {
				val formatted = format.format(Timestamp(it.unixTimestamp.toLong() * 1000L))
				it.element.parent.replace(PhpPsiElementFactory.createPhpPsiFromText(project, PhpElementTypes.STRING, "\"$formatted\""))
			}
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean
	{
		return this.getUnixTimestamp(editor, file).isNotEmpty()
	}

	/**
	 * Gets all selected unix timestamp values in the current editor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun getUnixTimestamp(editor: Editor, file: PsiFile): Array<ToConvert>
	{
		val caretModel = editor.caretModel
		val results: ArrayList<ToConvert> = arrayListOf()

		for (i in (caretModel.caretCount - 1) downTo 0)
		{
			val caret = caretModel.allCarets[i]
			val element = file.findElementAt(caret.offset) ?: continue
			val text = element.text

			try
			{
				results.add(ToConvert(element, parseDouble(text)))
			}
			catch (err: Exception)
			{
				continue
			}
		}

		return results.toTypedArray()
	}

	/**
	 * Data Class ToConvert
	 *
	 * @constructor
	 * @param element {PsiElement}
	 * @param unixTimestamp {Number}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.intention.ConvertUnixTimestampToDateFormatIntentionAction
	 * @since 1.4.0
	 */
	private data class ToConvert(val element: PsiElement, val unixTimestamp: Number)

}
