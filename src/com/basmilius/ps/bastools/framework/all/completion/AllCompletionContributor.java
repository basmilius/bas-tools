package com.basmilius.ps.bastools.framework.all.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;

public class AllCompletionContributor extends CompletionContributor
{

	@SuppressWarnings("unchecked")
	public AllCompletionContributor ()
	{
		this.extend(CompletionType.BASIC, FunctionCallAllArgumentsCompletionProvider.PLACE, new FunctionCallAllArgumentsCompletionProvider());
	}

}
