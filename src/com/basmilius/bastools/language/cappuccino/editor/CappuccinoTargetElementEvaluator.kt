/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.editor

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockReference
import com.intellij.codeInsight.TargetElementEvaluator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference

/**
 * Class CappuccinoTargetElementEvaluator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.textEditor
 */
class CappuccinoTargetElementEvaluator: TargetElementEvaluator
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getElementByReference(reference: PsiReference, flags: Int) = if (reference is CappuccinoBlockReference) if (flags and 2 != 0) reference.element else reference.resolve() else null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun includeSelfInGotoImplementation(element: PsiElement) = false

}
