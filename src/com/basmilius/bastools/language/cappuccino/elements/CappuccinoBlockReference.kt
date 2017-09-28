package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.util.CappuccinoLookupUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.impl.source.resolve.ResolveCache
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Class CappuccinoBlockReference
 *
 * @constructor
 * @param project Project
 * @param source CappuccinoBlockTag
 * @param range TextRange
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
class CappuccinoBlockReference(private val project: Project, source: CappuccinoBlockTag, range: TextRange): PsiReferenceBase<PsiElement>(source, range, true)
{

	private val blockResolver = BlockResolver()
	private val identifier: String? = source.findIdentifier()?.text

	/**
	 * Gets the identifier.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	@NotNull
	fun getIdentifier(): String = this.identifier!!

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@NotNull
	override fun getVariants(): Array<out PsiElement> = PsiElement.EMPTY_ARRAY

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@Nullable
	override fun resolve(): PsiElement?
	{
		val resolved = ResolveCache.getInstance(this.project).resolveWithCaching(this, this.blockResolver, false, false) ?: return null

		return resolved.getIdentifier()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun isReferenceTo(element: PsiElement?): Boolean
	{
		val resolved = this.resolve() ?: return false
		val parent = resolved.parent as? CappuccinoBlockTag ?: return false

		return parent == element
	}

	/**
	 * Resolves the Cappuccino definition.
	 *
	 * @return BlockResolveResult?
	 *
	 * @author Bas Milius
	 */
	@Nullable
	fun resolveDefinition(): BlockResolveResult?
	{
		val source = this.element

		assert(source is CappuccinoBlockTag)

		if (this.identifier == null)
			return null

		val target = CappuccinoLookupUtil.findBaseTag(this.element.containingFile, this.identifier)

		return BlockResolveResult(target)
	}

	/**
	 * Class BlockResolver
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockTag
	 */
	inner class BlockResolver: ResolveCache.AbstractResolver<CappuccinoBlockReference, BlockResolveResult>
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun resolve(reference: CappuccinoBlockReference, incompleteCode: Boolean) = reference.resolveDefinition()

	}

	/**
	 * Class BlockResolveResult
	 *
	 * @constructor
	 * @param tag CappuccinoBlockTag?
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockTag
	 */
	inner class BlockResolveResult(private val tag: CappuccinoBlockTag?): ResolveResult
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		@Nullable
		override fun getElement(): PsiElement? = this.tag

		/**
		 * Finds the closest identifier.
		 *
		 * @return PsiElement?
		 *
		 * @author Bas Milius
		 */
		fun getIdentifier(): PsiElement? = if (this.tag != null) this.tag.findIdentifier() else null

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun isValidResult(): Boolean
		{
			val identifier = this.getIdentifier()
			return identifier == null || identifier.isValid
		}

	}

}
