package com.basmilius.bastools.component.basSettings

import com.basmilius.bastools.component.BasToolsCodeStyleScheme
import com.intellij.openapi.options.SchemeImportException
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.codeStyle.CodeStyleScheme
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.impl.source.codeStyle.CodeStyleSettingsLoader

/**
 * Class BasSettingsBaseCodeStyleScheme
 *
 * @constructor
 * @param parent CodeStyleScheme
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.component.basSettings
 */
class BasSettingsBaseCodeStyleScheme(parent: CodeStyleScheme?): BasToolsCodeStyleScheme("Bas Settings Base", true, parent)
{

	/**
	 * BasSettingsBaseCodeStyleScheme Init.
	 *
	 * @author Bas Milius
	 */
	init
	{
		this.reloadSettings()
	}

	/**
	 * Reloads the settings.
	 *
	 * @author Bas Milius
	 */
	private fun reloadSettings()
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

	/**
	 * Gets the code style settings.
	 *
	 * @return CodeStyleSettings
	 * @author Bas Milius
	 */
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
