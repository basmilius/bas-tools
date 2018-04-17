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
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.util.xmlb.XmlSerializerUtil

class BTThemeConfigState: PersistentStateComponent<BTThemeConfigState>
{

	companion object
	{

		fun getInstance(): BTThemeConfigState
		{
			return BTConfigurable.getStateInstance(BTThemeConfigState::class)
		}

	}

	override fun getState() = this

	override fun loadState(state: BTThemeConfigState)
	{
		XmlSerializerUtil.copyBean(state, this)
	}

}