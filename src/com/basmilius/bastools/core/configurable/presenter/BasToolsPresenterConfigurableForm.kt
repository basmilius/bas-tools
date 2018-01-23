package com.basmilius.bastools.core.configurable.presenter

import com.basmilius.bastools.core.configurable.AbstractForm

/**
 * Class BasToolsPresenterConfigurableForm
 *
 * @constructor
 * @param configurable BasToolsPresenterConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable.presenter
 * @since 1.3.0
 */
class BasToolsPresenterConfigurableForm(private val configurable: BasToolsPresenterConfigurable): AbstractForm<BasToolsPresenterConfigurable.PresenterConfigurationState>()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun applyChanges()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun build()
	{
		this.load()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun load()
	{
		this.update(BasToolsPresenterConfigurable.PresenterConfigurationState.getInstance())

		this.modified = false
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun update(state: BasToolsPresenterConfigurable.PresenterConfigurationState)
	{
	}

}
