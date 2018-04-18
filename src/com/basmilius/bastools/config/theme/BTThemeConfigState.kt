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
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Class BTThemeConfigState
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.config.theme
 * @since 1.4.0
 */
@State(
		name = "BTThemeConfigState",
		storages = [Storage("bas-tools.xml")]
)
class BTThemeConfigState: PersistentStateComponent<BTThemeConfigState>
{

	/**
	 * Companion Object BTThemeConfigState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.config.theme
	 * @since 1.4.0
	 */
	companion object
	{

		/**
		 * Gets the instance of this state implementation.
		 *
		 * @return {BTThemeConfigState}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		fun getInstance(): BTThemeConfigState
		{
			return BTConfigurable.getStateInstance(BTThemeConfigState::class)
		}

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getState() = this

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun loadState(state: BTThemeConfigState)
	{
		XmlSerializerUtil.copyBean(state, this)
	}

}
