package com.basmilius.ps.bastools.component.basSettings

import com.basmilius.ps.bastools.component.BasToolsCodeStyleScheme

/**
 * Class BasSettingsCodeStyleScheme
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.component.basSettings
 */
class BasSettingsCodeStyleScheme: BasToolsCodeStyleScheme("Bas Settings", true, BasSettingsBaseCodeStyleScheme(null))
{

	/**
	 * BasSettingsCodeStyleScheme Constructor
	 *
	 * @author Bas Milius
	 */
	init
	{

		this.codeStyleSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.parentSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.writeExternal(this.writeScheme())
	}

}
