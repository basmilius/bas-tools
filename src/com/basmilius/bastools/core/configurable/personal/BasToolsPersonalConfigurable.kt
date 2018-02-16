/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.configurable.personal

import com.basmilius.bastools.core.configurable.AbstractConfigurable
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

/**
 * Class BasToolsPersonalConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable.personal
 * @since 1.3.0
 */
class BasToolsPersonalConfigurable: AbstractConfigurable<BasToolsPersonalConfigurableForm, BasToolsPersonalConfigurable.PersonalConfigurationState>()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun createForm() = BasToolsPersonalConfigurableForm(this)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun createState() = PersonalConfigurationState.getInstance()

	/**
	 * Class PersonalConfigurationState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.core.configurable.personal.BasToolsPersonalConfigurable
	 * @since 1.3.0
	 */
	@State(
			name = "PersonalConfiguration",
			storages = arrayOf(Storage("bas-tools.xml"))
	)
	class PersonalConfigurationState: PersistentStateComponent<PersonalConfigurationState>
	{

		/**
		 * Companion Object PersonalConfigurationState
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @package com.basmilius.bastools.core.configurable.personal.BasToolsPersonalConfigurable
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
			fun getInstance() = ServiceManager.getService(PersonalConfigurationState::class.java)!!

		}

		var fullName: String = "Bas Milius <bas@mili.us>"
		var eMail: String = "bas@mili.us"

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
		override fun loadState(state: PersonalConfigurationState)
		{
			this.fullName = state.fullName
			this.eMail = state.eMail
		}

	}

}
