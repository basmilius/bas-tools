package com.basmilius.ps.bastools.core.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BasToolsConfigurable implements SearchableConfigurable
{

	private BasToolsConfigurableGUI gui;

	@Nls
	@Override
	public final String getDisplayName ()
	{
		return "Bas Tools Preferences";
	}

	@NotNull
	@Override
	public final String getHelpTopic ()
	{
		return "preference.BasTools";
	}

	@NotNull
	@Override
	public final String getId ()
	{
		return "preference.BasTools";
	}

	@Nullable
	@Override
	public final Runnable enableSearch (final String searchQuery)
	{
		return null;
	}

	@Nullable
	@Override
	public final JComponent createComponent ()
	{
		this.gui = new BasToolsConfigurableGUI();
		return this.gui.getRootPanel();
	}

	@Override
	public final boolean isModified ()
	{
		return false;
	}

	@Override
	public final void apply () throws ConfigurationException
	{
	}

	@Override
	public final void reset ()
	{
	}

	@Override
	public final void disposeUIResources ()
	{
		this.gui = null;
	}

}
