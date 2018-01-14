package com.basmilius.bastools.core.configurable

import com.intellij.openapi.components.PersistentStateComponent

/**
 * Class BasToolsConfigurable
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.configurable
 */
class BasToolsConfigurable: AbstractConfigurable<BasToolsConfigurableForm, BasToolsConfigurable.BasToolsState>()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun createForm() = BasToolsConfigurableForm()

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun createState() = BasToolsState()

	/**
	 * Inner Class BasToolsState
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.core.configurable.BasToolsConfigurable
	 */
	inner class BasToolsState: PersistentStateComponent<BasToolsState>
	{

		/**
		 * {@inheritdoc}
		 * @author Bas Milius
		 */
		override fun getState() = this

		/**
		 * {@inheritdoc}
		 * @author Bas Milius
		 */
		override fun loadState(state: BasToolsState)
		{
		}

	}

}
