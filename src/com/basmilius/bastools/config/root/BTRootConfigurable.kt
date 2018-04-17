/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.config.root

import com.basmilius.bastools.config.BTConfigurable

class BTRootConfigurable: BTConfigurable<BTRootConfigUI, BTRootConfigState>("Bas Tools")
{

	override fun createUI() = BTRootConfigUI()

	override fun getState() = BTRootConfigState.getInstance()

	override fun isModified(ui: BTRootConfigUI, state: BTRootConfigState): Boolean
	{
		return false
	}

	override fun saveChanges(ui: BTRootConfigUI, state: BTRootConfigState)
	{
	}

	override fun setUIState(ui: BTRootConfigUI, state: BTRootConfigState)
	{
	}

	override fun dispose()
	{
	}

}