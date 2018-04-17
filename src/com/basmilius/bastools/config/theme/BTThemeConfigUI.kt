/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.config.theme

import com.basmilius.bastools.config.IBTConfigUI
import javax.swing.JComponent
import javax.swing.JPanel

class BTThemeConfigUI: IBTConfigUI
{

	override fun initialize()
	{
	}

	override fun getComponent(): JComponent
	{
		return JPanel()
	}

	override fun dispose()
	{
	}

}