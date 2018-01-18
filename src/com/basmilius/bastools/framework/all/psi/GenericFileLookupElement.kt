package com.basmilius.bastools.framework.all.psi

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileSystemItem

/**
 * Class GenericFileLookupElement
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.psi
 */
class GenericFileLookupElement: LookupElement
{

	private val fileName: String
	private val psiFile: PsiFileSystemItem
	private var element: PsiElement? = null
	private var insertHandler: InsertHandler<LookupElement>? = null

	/**
	 * GenericFileLookupElement Constructor.
	 *
	 * @param fileName String
	 * @param psiFile PsiFileSystemItem
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor (fileName: String, psiFile: PsiFileSystemItem): super()
	{
		this.fileName = fileName
		this.psiFile = psiFile
	}

	/**
	 * GenericFileLookupElement Constructor.
	 *
	 * @param fileName String
	 * @param psiFile PsiFileSystemItem
	 * @param element PsiElement
	 * @param insertHandler InsertHandler<LookupElement>
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(fileName: String, psiFile: PsiFileSystemItem, element: PsiElement, insertHandler: InsertHandler<LookupElement>): super()
	{
		this.fileName = fileName
		this.psiFile = psiFile
		this.element = element
		this.insertHandler = insertHandler
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getLookupString(): String
	{
		return this.fileName
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getObject(): Any
	{
		return this.element ?: super.getObject()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun handleInsert(context: InsertionContext)
	{
		if (this.insertHandler == null)
			return

		this.insertHandler?.handleInsert(context, this)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun renderElement(presentation: LookupElementPresentation?)
	{
		presentation?.itemText = this.lookupString
		presentation?.icon = this.psiFile.getIcon(0)
	}

}
