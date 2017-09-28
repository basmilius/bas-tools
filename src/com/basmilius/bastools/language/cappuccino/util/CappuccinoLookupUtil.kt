package com.basmilius.bastools.language.cappuccino.util

import com.basmilius.bastools.language.cappuccino.CappuccinoFile
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockTag
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoExtendsTag
import com.intellij.openapi.util.Condition
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiRecursiveElementVisitor

/**
 * Object CappuccinoLookupUtil
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.util
 */
object CappuccinoLookupUtil
{

	/**
	 * Tries to find the base tag.
	 *
	 * @param file PsiFile
	 * @param tagName String
	 *
	 * @return CappuccinoBlockTag
	 *
	 * @author Bas Milius
	 */
	fun findBaseTag(file: PsiFile, tagName: String): CappuccinoBlockTag
	{
		val foundElement = arrayOfNulls<PsiElement>(1)
		val finder = object: ElementFinder(Condition { element ->
			if (element is CappuccinoBlockTag)
			{
				val targetIdentifier = element.findIdentifier()

				targetIdentifier != null && targetIdentifier.text == tagName
			}
			else
			{
				false
			}
		})
		{

			override fun handleMatch(element: PsiElement?): Boolean
			{
				foundElement[0] = element

				return true
			}

		}

		lookupExtends(file, finder, 0)

		return foundElement[0] as CappuccinoBlockTag
	}

	/**
	 * Tries to find an extends tag.
	 *
	 * @param file PsiFile
	 *
	 * @return CappuccinoExtendsTag?
	 *
	 * @author Bas Milius
	 */
	private fun findExtendsTag(file: PsiFile): CappuccinoExtendsTag?
	{
		val foundElement = arrayOfNulls<PsiElement>(1)
		val finder = object: ElementFinder(Condition { element -> element is CappuccinoExtendsTag })
		{

			override fun handleMatch(element: PsiElement?): Boolean
			{
				foundElement[0] = element
				return true
			}

		}

		file.accept(finder)

		return if (foundElement[0] is CappuccinoExtendsTag) foundElement[0] as CappuccinoExtendsTag else null
	}

	/**
	 * Lookup for extend blocks.
	 *
	 * @param file PsiFile
	 * @param finder ElementFinder
	 *
	 * @author Bas Milius
	 */
	fun lookupExtends(file: PsiFile, finder: ElementFinder)
	{
		lookupExtends(file, finder, 0)
	}

	/**
	 * Lookup for extend blocks.
	 *
	 * @param file PsiFile
	 * @param finder ElementFinder
	 * @param currLookupDepth Int
	 *
	 * @author Bas Milius
	 */
	private fun lookupExtends(file: PsiFile, finder: ElementFinder, currLookupDepth: Int)
	{
		if (currLookupDepth >= 5)
			return

		val extendsTag = findExtendsTag(file) ?: return

		val extendsTagReferences = extendsTag.references
		val var6 = extendsTagReferences.size

		for (var7 in 0 until var6)
		{
			val tagRef = extendsTagReferences[var7]
			val resolvedExtends = tagRef.resolve()

			if (resolvedExtends == null || resolvedExtends !is CappuccinoFile)
				continue

			val targetFile = resolvedExtends as CappuccinoFile?
			targetFile!!.accept(finder)

			if (finder.done)
				return

			lookupExtends(targetFile, finder, currLookupDepth + 1)
		}
	}

	/**
	 * Class ElementFinder
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.util.CappuccinoLookupUtil
	 */
	abstract class ElementFinder(private val condition: Condition<PsiElement>): PsiRecursiveElementVisitor()
	{

		var done: Boolean = false

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun visitElement(element: PsiElement?)
		{
			if (this.done)
				return

			if (this.condition.value(element))
				this.done = this.handleMatch(element)
			else
				super.visitElement(element)
		}

		/**
		 * Handles a PSI element and returns TRUE if it's a match.
		 *
		 * @param element PsiElement?
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		abstract fun handleMatch(element: PsiElement?): Boolean

	}

}
