package com.basmilius.ps.bastools.framework.all.psi

import com.basmilius.ps.bastools.util.FileUtils
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceBase
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression

/**
 * Class GenericFileReference
 *
 * @constructor
 * @param element StringLiteralExpression
 * @param type Int
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.all.psi
 */
class GenericFileReference(element: StringLiteralExpression, type: Int): PsiReferenceBase<PsiElement>(element), PsiReference
{

	private val fileName: String = element.text.substring(element.valueRange.startOffset, element.valueRange.endOffset)
	private val fileType: Int = type

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun resolve(): PsiElement?
	{
		val filesByName = FileUtils.getRelativeFilesByName(this.element.containingFile, this.fileType)

		return filesByName[this.fileName]
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getVariants(): Array<Any>
	{
		val results = ArrayList<LookupElement>()
		val filesByName = FileUtils.getRelativeFilesByName(this.element.containingFile, this.fileType)

		for ((key, value) in filesByName)
		{
			results.add(GenericFileLookupElement(key, value))
		}

		return results.toTypedArray()
	}

}
