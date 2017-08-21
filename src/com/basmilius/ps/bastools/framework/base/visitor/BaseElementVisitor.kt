package com.basmilius.ps.bastools.framework.base.visitor

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag
import com.jetbrains.php.lang.psi.elements.Declare
import com.jetbrains.php.lang.psi.elements.PhpEval
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import org.jetbrains.annotations.NotNull

/**
 * Class BaseElementVisitor
 *
 * @constructor
 * @param problemsHolder ProblemsHolder
 * @param isOnTheFly Boolean
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.base.visitor
 */
abstract class BaseElementVisitor(val problemsHolder: ProblemsHolder, val isOnTheFly: Boolean): PhpElementVisitor()
{

	/**
	 * Visits a PHP Element.
	 *
	 * @param element PhpPsiElement
	 *
	 * @author Bas Milius
	 */
	final override fun visitPhpElement(@NotNull element: PhpPsiElement)
	{
		when (element)
		{
			is PhpEval -> this.visitPhpEval(element)
			is PhpDocTag -> this.visitPhpDocTag(element)
			is Declare -> this.visitPhpDeclare(element)
			else -> this.visitElement(element)
		}
	}

	/**
	 * Visits a PHP Declare statement.
	 *
	 * @param declare Declare
	 *
	 * @author Bas Milius
	 */
	fun visitPhpDeclare(@NotNull declare: Declare)
	{
		this.visitElement(declare)
	}

	/**
	 * Visits a PHP Doc Tag.
	 *
	 * @param tag PhpDocTag
	 *
	 * @author Bas Milius
	 */
	fun visitPhpDocTag(@NotNull tag: PhpDocTag)
	{
		this.visitElement(tag)
	}

}
