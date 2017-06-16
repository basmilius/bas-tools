package com.basmilius.ps.bastools.component.basSettings

import com.basmilius.ps.bastools.component.BTCodeStyleScheme

class BasSettingsCodeStyleScheme : BTCodeStyleScheme("Bas Settings", true, BasSettingsBaseCodeStyleScheme(null))
{
	init
	{

		this.codeStyleSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.parentSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.writeExternal(this.writeScheme())
	}

}
