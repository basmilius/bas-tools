package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.io.FileUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*

/**
 * Class CappuccinoTagWithFileReference
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
open class CappuccinoTagWithFileReference(node: ASTNode): CappuccinoCompositeElement(node)
{

	/**
	 * Changes the filename.
	 *
	 * @param newName String
	 *
	 * @author Bas Milius
	 */
	fun changeFileName(newName: String)
	{
		val fileNameElement = this.findFileNameElement() ?: return
		val newNameElement = CappuccinoElementFactory.createPsiElement(this.project, "{% block '$newName' %}", CappuccinoTokenTypes.STRING_TEXT) ?: return

		fileNameElement.replace(newNameElement)
	}

	/**
	 * Finds the filename element.
	 *
	 * @return PsiElement?
	 *
	 * @author Bas Milius
	 */
	@Nullable
	protected fun findFileNameElement(): PsiElement?
	{
		val children = this.getNonWhitespaceChildren()

		return if (children.size < 6 || children[2].node.elementType !== children[4].node.elementType || children[2].node.elementType !== CappuccinoTokenTypes.DOUBLE_QUOTE && children[2].node.elementType !== CappuccinoTokenTypes.SINGLE_QUOTE || children[5].node.elementType !== CappuccinoTokenTypes.STATEMENT_BLOCK_END && children[5].node.elementType !== CappuccinoTokenTypes.IDENTIFIER) null else children[3]
	}

	/**
	 * Gets non-whitespace children.
	 *
	 * @return Array<PsiElement>
	 *
	 * @author Bas Milius
	 */
	private fun getNonWhitespaceChildren(): Array<PsiElement>
	{
		val result = LinkedList<PsiElement>()

		var firstChild = this.firstChild

		while (firstChild != null)
		{
			if (firstChild !is PsiWhiteSpace)
				result.add(firstChild)

			firstChild = firstChild.nextSibling
		}

		return result.toTypedArray()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@NotNull
	override fun getNavigationElement(): PsiElement
	{
		val fileNameElement = this.findFileNameElement()

		if (fileNameElement != null)
			return fileNameElement

		return this
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@NotNull
	override fun getReferences(): Array<out PsiReference>
	{
		val fileNameElement = this.findFileNameElement()

		return if (fileNameElement == null)
			PsiReference.EMPTY_ARRAY
		else
			FileReferenceSet(FileUtil.toSystemIndependentName(fileNameElement.text), this, this.textOffset - this.textRange.startOffset, null as PsiReferenceProvider?, true).allReferences
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getTextOffset() = this.navigationElement.textRange.startOffset

}
