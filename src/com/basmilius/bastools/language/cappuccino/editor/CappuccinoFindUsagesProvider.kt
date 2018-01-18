package com.basmilius.bastools.language.cappuccino.editor

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockTag
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement

/**
 * Class CappuccinoFindUsagesProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.editor
 */
class CappuccinoFindUsagesProvider: FindUsagesProvider
{

	/**
	 * Companion Object CappuccinoFindUsagesProvider
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.editor
	 */
	companion object
	{

		/**
		 * Gets the name of a PSI Element.
		 *
		 * @param element PsiElement
		 *
		 * @return String?
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		fun getName(element: PsiElement): String? = if (element is PsiNamedElement) element.name else element.text

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getWordsScanner(): WordsScanner? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getNodeText(element: PsiElement, useFullName: Boolean) = getName(element)!!

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getDescriptiveName(element: PsiElement): String = getName(element)!!

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getType(element: PsiElement): String
	{
		if (element is CappuccinoBlockTag)
			return "Block"

		return element.node.elementType.toString()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getHelpId(element: PsiElement): String? = "reference.dialogs.findUsages.other"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun canFindUsagesFor(element: PsiElement) = element is CappuccinoBlockTag && getName(element) != null

}
