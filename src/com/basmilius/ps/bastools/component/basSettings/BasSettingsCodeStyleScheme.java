package com.basmilius.ps.bastools.component.basSettings;

import com.basmilius.ps.bastools.component.BTCodeStyleScheme;

public class BasSettingsCodeStyleScheme extends BTCodeStyleScheme
{

	public BasSettingsCodeStyleScheme ()
	{
		super("Bas Settings", true, new BasSettingsBaseCodeStyleScheme(null));

		this.setCodeStyleSettings(this.getParentScheme(BasSettingsBaseCodeStyleScheme.class).getCodeStyleSettings());
		this.getCodeStyleSettings().setParentSettings(this.getParentScheme(BasSettingsBaseCodeStyleScheme.class).getCodeStyleSettings());
		this.getCodeStyleSettings().writeExternal(this.writeScheme());
	}

}
