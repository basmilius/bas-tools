package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReference
import com.intellij.util.IncorrectOperationException
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Class CappuccinoBlockTag
 *
 * @constructor
 * @param node ASTNode
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
class CappuccinoBlockTag(node: ASTNode): CappuccinoCompositeElement(node), PsiNamedElement
{

	private var reference: CappuccinoBlockReference? = null

	/**
	 * Finds the sibbling identifier.
	 *
	 * @return PsiElement?
	 *
	 * @author Bas Milius
	 */
	@Nullable
	fun findIdentifier(): PsiElement?
	{
		var child: PsiElement? = this.firstChild

		while (child != null)
		{
			if (child.node.elementType === CappuccinoTokenTypes.IDENTIFIER)
				return child

			child = child.nextSibling
		}

		return null
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getReference(): PsiReference?
	{
		val identifier = this.findIdentifier()

		if (identifier == null)
			this.reference = null
		else
			this.reference = CappuccinoBlockReference(this.project, this, TextRange(0, this.textLength))

		return this.reference
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getName(): String?
	{
		val identifier = this.findIdentifier() ?: return null

		return identifier.text
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@Throws(IncorrectOperationException::class)
	override fun setName(@NonNls @NotNull name: String): PsiElement
	{
		val newIdentifier = CappuccinoElementFactory.createPsiElement(this.project, "", CappuccinoTokenTypes.IDENTIFIER) ?: throw IncorrectOperationException("Cannot rename block '$this'")
		val identifier = this.findIdentifier() ?: throw IncorrectOperationException("Cannot rename block '$this'")

		identifier.replace(newIdentifier)

		return this
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun toString(): String
	{
		val identifier = this.findIdentifier() ?: return "BlockTag('?')"

		return "BlockTag('${identifier.text}')"
	}

}
