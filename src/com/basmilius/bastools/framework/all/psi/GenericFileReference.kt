/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all.psi

import com.basmilius.bastools.core.util.FileUtils
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
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.psi
 * @since 1.2.0
 */
class GenericFileReference(element: StringLiteralExpression, type: Int): PsiReferenceBase<PsiElement>(element), PsiReference
{

	private val fileName: String = element.text.substring(element.valueRange.startOffset, element.valueRange.endOffset)
	private val fileType: Int = type

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	override fun resolve(): PsiElement?
	{
		val filesByName = FileUtils.getRelativeFilesByName(this.element.containingFile, this.fileType)

		return filesByName[this.fileName]
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
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
