package com.basmilius.ps.bastools.component.basSettings

import com.basmilius.ps.bastools.component.BTCodeStyleScheme
import com.intellij.openapi.options.SchemeImportException
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.codeStyle.CodeStyleScheme
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.impl.source.codeStyle.CodeStyleSettingsLoader

class BasSettingsBaseCodeStyleScheme(parent: CodeStyleScheme?) : BTCodeStyleScheme("Bas Settings Base", true, parent)
{

	/**
	 * BasSettingsBaseCodeStyleScheme Init.
	 */
	init
	{
		this.reloadSettings()
	}

	/**
	 * Reloads the settings.
	 */
	fun reloadSettings()
	{
		try
		{
			val settings = this.settings
			this.codeStyleSettings = settings
		}
		catch (e: Exception)
		{
			e.printStackTrace()
		}

	}

	private val settings: CodeStyleSettings
		@Throws(SchemeImportException::class)
		get()
		{
			val resource = this.javaClass.classLoader.getResource("codestyles/Bas Settings.xml") ?: throw SchemeImportException("Scheme file not found!")

			val file = VfsUtil.findFileByURL(resource)
			val loader = CodeStyleSettingsLoader()

			if (file == null)
				throw SchemeImportException("Scheme file not found!")

			return loader.loadSettings(file)
		}

}
