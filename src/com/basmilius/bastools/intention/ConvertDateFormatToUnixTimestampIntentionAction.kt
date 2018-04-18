package com.basmilius.bastools.intention

import com.basmilius.bastools.core.util.EditorUtils
import com.basmilius.bastools.core.util.strtotime.strtotime
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.PhpPsiElementFactory

/**
 * Class ConvertDateFormatToUnixTimestampIntentionAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.intention
 * @since 1.4.0
 */
class ConvertDateFormatToUnixTimestampIntentionAction: BTIntentionAction("Convert to Unix Timestamp...")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun invoke(project: Project, editor: Editor, file: PsiFile)
	{
		val timestamps = this.getDateStrings(editor, file)

		// TODO(Bas): Show editor hint that no unix timestamps were found.
		if (timestamps.isEmpty())
			return

		EditorUtils.writeAction(project) {
			timestamps.forEach {
				it.element.parent.replace(PhpPsiElementFactory.createPhpPsiFromText(project, PhpElementTypes.NUMBER, "${it.unixTimestamp}"))
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
		return this.getDateStrings(editor, file).isNotEmpty()
	}

	/**
	 * Gets all selected unix timestamp values in the current editor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun getDateStrings(editor: Editor, file: PsiFile): Array<ToConvert>
	{
		val caretModel = editor.caretModel
		val results: ArrayList<ToConvert> = arrayListOf()

		for (i in (caretModel.caretCount - 1) downTo 0)
		{
			val caret = caretModel.allCarets[i]
			val element = file.findElementAt(caret.offset) ?: continue
			val text = strtotime(element.text) ?: continue

			results.add(ToConvert(element, text))
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
	 * @package com.basmilius.bastools.intention.ConvertDateFormatToUnixTimestampIntentionAction
	 * @since 1.4.0
	 */
	private data class ToConvert(val element: PsiElement, val unixTimestamp: Number)

}
