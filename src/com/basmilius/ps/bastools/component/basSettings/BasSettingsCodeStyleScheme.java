package com.basmilius.ps.bastools.component.basSettings;

import com.intellij.openapi.options.SchemeImportException;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.codeStyle.CodeStyleSchemes;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.impl.source.codeStyle.CodeStyleSchemeImpl;
import com.intellij.psi.impl.source.codeStyle.CodeStyleSettingsLoader;

import java.net.URL;

public class BasSettingsCodeStyleScheme extends CodeStyleSchemeImpl
{

	public BasSettingsCodeStyleScheme ()
	{
		super("Bas Settings", true, CodeStyleSchemes.getInstance().getDefaultScheme());

		this.reloadSettings();
	}

	public final void reloadSettings ()
	{
		try
		{
			this.getCodeStyleSettings().setParentSettings(this.getSettings());
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
