package com.basmilius.ps.bastools.framework.base.visitor

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag
import com.jetbrains.php.lang.psi.elements.Declare
import com.jetbrains.php.lang.psi.elements.PhpEval
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import org.jetbrains.annotations.NotNull

abstract class BaseElementVisitor(val problemsHolder : ProblemsHolder, val isOnTheFly : Boolean) : PhpElementVisitor()
{

	final override fun visitPhpElement(@NotNull element : PhpPsiElement)
	{
		if (element is PhpEval)
			this.visitPhpEval(element)
		else if (element is PhpDocTag)
			this.visitPhpDocTag(element)
		else if (element is Declare)
			this.visitPhpDeclare(element)
		else
			this.visitElement(element)
	}

	fun visitPhpDeclare(@NotNull declare : Declare)
	{
		this.visitElement(declare)
	}

	fun visitPhpDocTag(@NotNull tag : PhpDocTag)
	{
		this.visitElement(tag)
	}

}
