package com.basmilius.ps.bastools.component.basSettings;

import com.basmilius.ps.bastools.component.BTCodeStyleScheme;
import com.intellij.openapi.options.SchemeImportException;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.codeStyle.CodeStyleScheme;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.impl.source.codeStyle.CodeStyleSettingsLoader;

import java.net.URL;

public class BasSettingsBaseCodeStyleScheme extends BTCodeStyleScheme
{

	public BasSettingsBaseCodeStyleScheme (final CodeStyleScheme parent)
	{
		super("Bas Settings Base", true, parent);

		this.reloadSettings();
	}

	public final void reloadSettings ()
	{
		try
		{
			final CodeStyleSettings settings = this.getSettings();
			this.setCodeStyleSettings(settings);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private CodeStyleSettings getSettings () throws SchemeImportException
	{
		final URL resource = this.getClass().getClassLoader().getResource("codestyles/Bas Settings.xml");

		if (resource == null)
			throw new SchemeImportException("Scheme file not found!");

		final VirtualFile file = VfsUtil.findFileByURL(resource);
		final CodeStyleSettingsLoader loader = new CodeStyleSettingsLoader();

		if (file == null)
			throw new SchemeImportException("Scheme file not found!");

		return loader.loadSettings(file);
	}

}
