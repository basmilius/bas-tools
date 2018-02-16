/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.basSettings

import com.basmilius.bastools.component.BasToolsCodeStyleScheme

/**
 * Class BasSettingsCodeStyleScheme
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.basSettings
 * @since 1.1.0
 */
class BasSettingsCodeStyleScheme: BasToolsCodeStyleScheme("Bas Settings", true, BasSettingsBaseCodeStyleScheme(null))
{

	/**
	 * BasSettingsCodeStyleScheme Constructor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	init
	{

		this.codeStyleSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.parentSettings = this.getParentScheme(BasSettingsBaseCodeStyleScheme::class.java).codeStyleSettings
		this.codeStyleSettings.writeExternal(this.writeScheme())
	}

}
