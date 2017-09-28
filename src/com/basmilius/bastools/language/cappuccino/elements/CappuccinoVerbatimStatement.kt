package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.ElementManipulators
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import java.lang.StringBuilder

/**
 * Class CappuccinoVerbatimStatement
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
class CappuccinoVerbatimStatement(node: ASTNode): CappuccinoCompositeElement(node), PsiLanguageInjectionHost
{

	/**
	 * Gets the content element.
	 *
	 * @return PsiElement?
	 *
	 * @author Bas Milius
	 */
	private fun getContentElement(): PsiElement? = this.findChildByType(CappuccinoTokenTypes.VERBATIM_CONTENT)

	/**
	 * Gets the end tag text.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getEndTagText() = findChildByType<PsiElement>(CappuccinoElementTypes.ENDVERBATIM_TAG)?.text

	/**
	 * Gets the start tag text.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getStartTagText() = findChildByType<PsiElement>(CappuccinoElementTypes.VERBATIM_TAG)?.text

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun isValidHost() = this.getContentElement() != null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun updateText(text: String): PsiLanguageInjectionHost = ElementManipulators.handleContentChange(this, text)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost> = VerbatimContentEscaper(this)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun toString() = "Verbatim"

	/**
	 * Inner Class VerbatimContentEscaper
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.elements
	 */
	inner class VerbatimContentEscaper(host: CappuccinoVerbatimStatement): LiteralTextEscaper<CappuccinoVerbatimStatement>(host)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean
		{
			outChars.append(rangeInsideHost.substring(this.myHost.text));

			return true
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange) = rangeInsideHost.startOffset + offsetInDecoded

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun getRelevantTextRange(): TextRange
		{
			val content = (this.myHost as CappuccinoVerbatimStatement).getContentElement() ?: error("No content element for Verbatim Statement: ${this.myHost.text}")
			val contentOffset = content.startOffsetInParent

			return TextRange(contentOffset, contentOffset + content.textLength)
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun isOneLine() = false

	}

}
