package com.basmilius.bastools.core.configurable.presenter

import com.basmilius.bastools.core.configurable.AbstractConfigurable
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

/**
 * Class BasToolsPresenterConfigurable
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.configurable.presenter
 */
class BasToolsPresenterConfigurable: AbstractConfigurable<BasToolsPresenterConfigurableForm, BasToolsPresenterConfigurable.PresenterConfigurationState>()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun createForm() = BasToolsPresenterConfigurableForm(this)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun createState() = PresenterConfigurationState.getInstance()

	/**
	 * Class PresenterConfigurationState
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.core.configurable.presenter.BasToolsPresenterConfigurable
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
		 * @author Bas Milius
		 * @package com.basmilius.bastools.core.configurable.presenter.BasToolsPresenterConfigurable
		 */
		companion object
		{

			/**
			 * Gets the instance of the configuraition state.
			 *
			 * @return PersonalConfigurationState
			 *
			 * @author Bas Milius
			 */
			fun getInstance() = ServiceManager.getService(PresenterConfigurationState::class.java)!!

		}

		/**
		 * {@inheritdoc}
		 * @author Bas Milius
		 */
		override fun getState() = this

		/**
		 * {@inheritdoc}
		 * @author Bas Milius
		 */
		override fun loadState(state: PresenterConfigurationState?)
		{
		}

	}

}
