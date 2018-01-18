package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.CappuccinoFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.Nullable

/**
 * Object CappuccinoElementFactory
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
object CappuccinoElementFactory
{

	/**
	 * Creates a new PSI Element.
	 *
	 * @param project Project
	 * @param text String
	 * @param type IElementType
	 *
	 * @return PsiElement
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	@Nullable
	fun createPsiElement(project: Project, text: String, type: IElementType): PsiElement?
	{
		val psiFile = PsiFileFactory.getInstance(project).createFileFromText("DUMMY_.${CappuccinoFileType.Instance.defaultExtension}", CappuccinoFileType.Instance, text, System.currentTimeMillis(), false)
		val result = Ref<PsiElement>()

		psiFile.accept(object: PsiRecursiveElementVisitor()
		{
			override fun visitElement(element: PsiElement)
			{
				if (result.get() == null)
				{
					if (element.node.elementType === type)
						result.set(element)
					else
						super.visitElement(element)
				}
			}
		})

		return result.get()
	}

}
