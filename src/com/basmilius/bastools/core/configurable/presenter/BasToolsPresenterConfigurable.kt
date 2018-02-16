/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.configurable.presenter

import com.basmilius.bastools.core.configurable.AbstractConfigurable
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

/**
 * Class BasToolsPresenterConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable.presenter
 * @since 1.3.0
 */
class BasToolsPresenterConfigurable: AbstractConfigurable<BasToolsPresenterConfigurableForm, BasToolsPresenterConfigurable.PresenterConfigurationState>()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun createForm() = BasToolsPresenterConfigurableForm(this)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun createState() = PresenterConfigurationState.getInstance()

	/**
	 * Class PresenterConfigurationState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.core.configurable.presenter.BasToolsPresenterConfigurable
	 * @since 1.3.0
	 */
	@State(
			name = "PresenterConfiguration",
			storages = arrayOf(Storage("bas-tools.xml"))
	)
	class PresenterConfigurationState: PersistentStateComponent<PresenterConfigurationState>
	{

		/**
		 * Companion Object PresenterConfigurationState
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @package com.basmilius.bastools.core.configurable.presenter.BasToolsPresenterConfigurable
		 * @since 1.3.0
		 */
		companion object
		{

			/**
			 * Gets the instance of the configuraition state.
			 *
			 * @return PersonalConfigurationState
			 *
			 * @author Bas Milius <bas@mili.us>
			 * @since 1.3.0
			 */
			fun getInstance() = ServiceManager.getService(PresenterConfigurationState::class.java)!!

		}

		/**
		 * {@inheritdoc}
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.0
		 */
		override fun getState() = this

		/**
		 * {@inheritdoc}
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.0
		 */
		override fun loadState(state: PresenterConfigurationState)
		{
		}

	}

}
