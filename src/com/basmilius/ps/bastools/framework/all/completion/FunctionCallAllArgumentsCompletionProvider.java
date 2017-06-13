package com.basmilius.ps.bastools.framework.all.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupValueWithPriority;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import com.jetbrains.php.lang.psi.elements.Function;
import com.jetbrains.php.lang.psi.elements.Parameter;
import org.jetbrains.annotations.NotNull;

public class FunctionCallAllArgumentsCompletionProvider extends CompletionProvider<CompletionParameters>
{

	@Override
	protected void addCompletions (@NotNull final CompletionParameters parameters, final ProcessingContext context, @NotNull final CompletionResultSet results)
	{
		final Function func = PsiTreeUtil.getParentOfType(parameters.getOriginalPosition(), Function.class);

		if (func != null)
		{
			final Parameter[] ps = func.getParameters();
			final String[] pss = new String[ps.length];

			for (int i = 0; i < ps.length; i++)
			{
				pss[i] = "$" + ps[i].getName();
			}

			results.addElement(PrioritizedLookupElement.withPriority(LookupElementBuilder.create(String.join(", ", pss)), LookupValueWithPriority.HIGHER));
		}
	}

	public static ElementPattern PLACE = PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(PhpElementTypes.FUNCTION_CALL));

}
