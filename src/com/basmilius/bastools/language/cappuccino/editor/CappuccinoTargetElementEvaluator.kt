package com.basmilius.bastools.language.cappuccino.editor

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockReference
import com.intellij.codeInsight.TargetElementEvaluator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference

/**
 * Class CappuccinoTargetElementEvaluator
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.editor
 */
class CappuccinoTargetElementEvaluator: TargetElementEvaluator
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getElementByReference(reference: PsiReference, flags: Int) = if (reference is CappuccinoBlockReference) if (flags and 2 != 0) reference.element else reference.resolve() else null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun includeSelfInGotoImplementation(element: PsiElement) = false

}
