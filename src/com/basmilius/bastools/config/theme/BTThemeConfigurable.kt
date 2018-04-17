/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.config.theme

import com.basmilius.bastools.config.BTConfigurable

class BTThemeConfigurable: BTConfigurable<BTThemeConfigUI, BTThemeConfigState>("Theme")
{

	override fun createUI() = BTThemeConfigUI()

	override fun getState() = BTThemeConfigState.getInstance()

	override fun isModified(ui: BTThemeConfigUI, state: BTThemeConfigState): Boolean
	{
		return false
	}

	override fun saveChanges(ui: BTThemeConfigUI, state: BTThemeConfigState)
	{
	}

	override fun setUIState(ui: BTThemeConfigUI, state: BTThemeConfigState)
	{
	}

	override fun dispose()
	{
	}

}