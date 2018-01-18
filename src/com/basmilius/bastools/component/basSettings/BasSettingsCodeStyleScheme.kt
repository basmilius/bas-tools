package com.basmilius.bastools.component.basSettings

import com.basmilius.bastools.component.BasToolsCodeStyleScheme

/**
 * Class BasSettingsCodeStyleScheme
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.basSettings
 */
class BasSettingsCodeStyleScheme: BasToolsCodeStyleScheme("Bas Settings", true, BasSettingsBaseCodeStyleScheme(null))
{

	/**
	 * BasSettingsCodeStyleScheme Constructor
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	init
	{

		this.codeStyleSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.parentSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.writeExternal(this.writeScheme())
	}

}
