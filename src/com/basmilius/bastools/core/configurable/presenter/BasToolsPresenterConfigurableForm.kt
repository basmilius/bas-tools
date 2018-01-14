package com.basmilius.bastools.core.configurable.presenter

import com.basmilius.bastools.core.configurable.AbstractForm

/**
 * Class BasToolsPresenterConfigurableForm
 *
 * @constructor
 * @param configurable BasToolsPresenterConfigurable
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.configurable.presenter
 */
class BasToolsPresenterConfigurableForm(private val configurable: BasToolsPresenterConfigurable): AbstractForm<BasToolsPresenterConfigurable.PresenterConfigurationState>()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun applyChanges()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun build()
	{
		this.load()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun load()
	{
		this.update(BasToolsPresenterConfigurable.PresenterConfigurationState.getInstance())

		this.modified = false
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun update(state: BasToolsPresenterConfigurable.PresenterConfigurationState)
	{
	}

}
