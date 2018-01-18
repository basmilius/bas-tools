package com.basmilius.bastools.core.configurable

import com.intellij.openapi.components.PersistentStateComponent

/**
 * Class BasToolsConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable
 */
class BasToolsConfigurable: AbstractConfigurable<BasToolsConfigurableForm, BasToolsConfigurable.BasToolsState>()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun createForm() = BasToolsConfigurableForm()

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun createState() = BasToolsState()

	/**
	 * Inner Class BasToolsState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.core.configurable.BasToolsConfigurable
	 */
	inner class BasToolsState: PersistentStateComponent<BasToolsState>
	{

		/**
		 * {@inheritdoc}
		 * @author Bas Milius <bas@mili.us>
		 */
		override fun getState() = this

		/**
		 * {@inheritdoc}
		 * @author Bas Milius <bas@mili.us>
		 */
		override fun loadState(state: BasToolsState)
		{
		}

	}

}
