/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.config.codeMap

import com.basmilius.bastools.config.BTConfigurable

/**
 * Class BTCodeMapConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.config.codeMap
 * @since 1.4.0
 */
class BTCodeMapConfigurable: BTConfigurable<BTCodeMapConfigUI, BTCodeMapConfigState>("CodeMap")
{

	override fun createUI() = BTCodeMapConfigUI()

	override fun getState() = BTCodeMapConfigState.getInstance()

	override fun isModified(ui: BTCodeMapConfigUI, state: BTCodeMapConfigState): Boolean
	{
		return false
	}

	override fun saveChanges(ui: BTCodeMapConfigUI, state: BTCodeMapConfigState)
	{
	}

	override fun setUIState(ui: BTCodeMapConfigUI, state: BTCodeMapConfigState)
	{
	}

	override fun dispose()
	{
	}

}
