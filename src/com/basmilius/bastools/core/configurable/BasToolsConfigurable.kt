package com.basmilius.bastools.core.configurable

import com.intellij.openapi.components.PersistentStateComponent

/**
 * Class BasToolsConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable
 * @since 1.3.0
 */
class BasToolsConfigurable: AbstractConfigurable<BasToolsConfigurableForm, BasToolsConfigurable.BasToolsState>()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun createForm() = BasToolsConfigurableForm()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun createState() = BasToolsState()

	/**
	 * Inner Class BasToolsState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.core.configurable.BasToolsConfigurable
	 * @since 1.3.0
	 */
	inner class BasToolsState: PersistentStateComponent<BasToolsState>
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.0
		 */
		override fun getState() = this

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.0
		 */
		override fun loadState(state: BasToolsState)
		{
		}

	}

}
