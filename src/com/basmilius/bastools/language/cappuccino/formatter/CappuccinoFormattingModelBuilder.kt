package com.basmilius.bastools.language.cappuccino.formatter

import com.basmilius.bastools.language.cappuccino.CappuccinoFile
import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.intellij.formatting.Alignment
import com.intellij.formatting.Indent
import com.intellij.formatting.Wrap
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.xml.XmlFormattingPolicy
import com.intellij.xml.template.formatter.AbstractXmlTemplateFormattingModelBuilder

/**
 * Class CappuccinoFormattingModelBuilder
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.formatter
 */
class CappuccinoFormattingModelBuilder: AbstractXmlTemplateFormattingModelBuilder()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun createTemplateLanguageBlock(node: ASTNode, settings: CodeStyleSettings, xmlFormattingPolicy: XmlFormattingPolicy, indent: Indent?, alignment: Alignment?, wrap: Wrap?) = CappuccinoBlock(this, node, wrap, alignment, settings, xmlFormattingPolicy, indent)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isMarkupLanguageElement(element: PsiElement): Boolean
	{
		val type = element.node.elementType

		return type == CappuccinoTokenTypes.TEMPLATE_HTML_TEXT || type == CappuccinoElementTypes.TEMPLATE_DATA
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isOuterLanguageElement(element: PsiElement) = element.node.elementType == CappuccinoTokenTypes.OUTER_ELEMENT_TYPE

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isTemplateFile(file: PsiFile) = file is CappuccinoFile

}
