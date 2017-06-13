package com.basmilius.ps.bastools.component;

import com.intellij.psi.codeStyle.CodeStyleScheme;
import com.intellij.psi.impl.source.codeStyle.CodeStyleSchemeImpl;
import org.jetbrains.annotations.NotNull;

public abstract class BTCodeStyleScheme extends CodeStyleSchemeImpl
{

	private final CodeStyleScheme parentScheme;

	public BTCodeStyleScheme (@NotNull String name, boolean isDefault, CodeStyleScheme parentScheme)
	{
		super(name, isDefault, parentScheme);

		this.parentScheme = parentScheme;
	}

	public final <T extends CodeStyleSchemeImpl> T getParentScheme (Class<T> implementation)
	{
		return implementation.cast(this.parentScheme);
	}
}
