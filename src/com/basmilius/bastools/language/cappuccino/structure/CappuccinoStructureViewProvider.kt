package com.basmilius.bastools.language.cappuccino.structure

import com.basmilius.bastools.language.cappuccino.CappuccinoFile
import com.basmilius.bastools.language.cappuccino.CappuccinoLanguage
import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement as IdeStructureViewTreeElement
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.ide.structureView.impl.StructureViewComposite
import com.intellij.ide.structureView.impl.TemplateLanguageStructureViewBuilder
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.util.PlatformIcons
import javax.swing.Icon

/**
 * Class CappuccinoStructureViewProvider
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.structure
 */
class CappuccinoStructureViewProvider: PsiStructureViewFactory
{

	/**
	 * Companion Object CappuccinoStructureViewProvider
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.structure
	 */
	companion object
	{

		/**
		 * Gets the statement identifier.
		 *
		 * @param statementElement PsiElement
		 * @param tagType IElementType
		 *
		 * @return String
		 *
		 * @author Bas Milius
		 */
		private fun getStatementIdentifier(statementElement: PsiElement, tagType: IElementType?): String
		{
			var child: PsiElement? = statementElement.firstChild

			while (child != null)
			{
				if (child.node.elementType === tagType)
				{
					child = child.firstChild

					while (child != null)
					{
						val childType = child.node.elementType
						if (childType === CappuccinoTokenTypes.IDENTIFIER || childType === CappuccinoTokenTypes.STRING_TEXT)
							return child.text

						child = child.nextSibling
					}

					return "..."
				}

				child = child.nextSibling
			}

			return "?"
		}

		/**
		 * Gets the tag type for {@see element} (a block statement).
		 *
		 * @param element PsiElement
		 *
		 * @return IElementType?
		 *
		 * @author Bas Milius
		 */
		private fun getTagType(element: PsiElement): IElementType?
		{
			val elementType = element.node.elementType

			return when (elementType)
			{
				CappuccinoElementTypes.BLOCK_STATEMENT -> CappuccinoElementTypes.BLOCK_TAG
				CappuccinoElementTypes.EMBED_STATEMENT -> CappuccinoElementTypes.EMBED_TAG
				CappuccinoElementTypes.MACRO_STATEMENT -> CappuccinoElementTypes.MACRO_TAG
				else -> null
			}
		}

		/**
		 * Returns TRUE if {@see element} is a block statement.
		 *
		 * @param element PsiElement
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		private fun isBlockElement(element: PsiElement): Boolean
		{
			val elementType = element.node.elementType

			return elementType == CappuccinoElementTypes.BLOCK_STATEMENT || elementType == CappuccinoElementTypes.EMBED_STATEMENT || elementType == CappuccinoElementTypes.MACRO_STATEMENT
		}

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getStructureViewBuilder(file: PsiFile) = StructureViewBuilder(file)

	/**
	 * Class StructureViewBuilder
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.structure.CappuccinoStructureViewProvider
	 */
	inner class StructureViewBuilder(psiElement: PsiElement): TemplateLanguageStructureViewBuilder(psiElement)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun createMainView(editor: FileEditor, mainFile: PsiFile): StructureViewComposite.StructureViewDescriptor
		{
			val mainView = this.createBuilder(mainFile).createStructureView(editor, mainFile.project)

			return StructureViewComposite.StructureViewDescriptor(CappuccinoLanguage.LanguageId, mainView, mainFile.fileType.icon)
		}

		/**
		 * Creates the builder.
		 *
		 * @param mainFile PsiFile
		 *
		 * @return TreeBasedStructureViewBuilder
		 *
		 * @author Bas Milius
		 */
		fun createBuilder(mainFile: PsiFile): TreeBasedStructureViewBuilder
		{
			return object: TreeBasedStructureViewBuilder()
			{

				/**
				 * {@inheritdoc}
				 *
				 * @author Bas Milius
				 */
				override fun createStructureViewModel(editor: Editor?) = StructureViewModelBase(mainFile, editor, StructureViewTreeElement(mainFile))

			}
		}

	}

	/**
	 * Class StructureViewTreeElement
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.structure.CappuccinoStructureViewProvider
	 */
	private inner class StructureViewTreeElement(psiElement: PsiElement): PsiTreeElementBase<PsiElement>(psiElement)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun getChildrenBase(): MutableCollection<IdeStructureViewTreeElement>
		{
			val element = this.element
			val treeElements = ArrayList<IdeStructureViewTreeElement>()

			if (element != null && (element is CappuccinoFile || isBlockElement(element)))
			{
				var child: PsiElement? = element.firstChild

				while (child != null)
				{
					if (isBlockElement(child))
						treeElements.add(StructureViewTreeElement(child))

					child = child.nextSibling
				}
			}

			return treeElements
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun getIcon(open: Boolean): Icon?
		{
			val element = this.element

			return if (element != null && isBlockElement(element)) PlatformIcons.XML_TAG_ICON else super.getIcon(open)
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun getPresentableText(): String?
		{
			val element = this.element

			if (element != null)
			{
				if (element is CappuccinoFile)
					return element.name

				if (isBlockElement(element))
					return getStatementIdentifier(element, getTagType(element))
			}

			return "?"
		}

	}

}
