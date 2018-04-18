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
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Class BTRootConfigState
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.config.root
 * @since 1.4.0
 */
@State(
		name = "BTRootConfigState",
		storages = [Storage("bas-tools.xml")]
)
class BTRootConfigState: PersistentStateComponent<BTRootConfigState>
{

	/**
	 * Companion Object BTRootConfigState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.config.root
	 * @since 1.4.0
	 */
	companion object
	{

		/**
		 * Gets the instance of this state implementation.
		 *
		 * @returns {BTRootConfigState}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		fun getInstance(): BTRootConfigState
		{
			return BTConfigurable.getStateInstance(BTRootConfigState::class)
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
	override fun loadState(state: BTRootConfigState)
	{
		XmlSerializerUtil.copyBean(state, this)
	}

}
