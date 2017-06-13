package com.basmilius.ps.bastools.framework.identity.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import com.jetbrains.php.lang.psi.elements.Method;
import org.jetbrains.annotations.NotNull;

public class CoreCompletionContributor extends CompletionContributor
{

	/**
	 * CoreCompletionContributor Constructor.
	 */
	public CoreCompletionContributor ()
	{
		this.extend(CompletionType.BASIC, PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(PhpElementTypes.METHOD_REFERENCE)), new PluginCallProvider());
	}

	class PluginCallProvider extends CompletionProvider<CompletionParameters>
	{

		@Override
		protected final void addCompletions (@NotNull final CompletionParameters parameters, final ProcessingContext context, @NotNull final CompletionResultSet results)
		{
			final Method func = PsiTreeUtil.getContextOfType(parameters.getOriginalPosition(), Method.class);

			if (func == null)
				return;
		}

	}

}
