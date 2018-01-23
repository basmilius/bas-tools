package com.basmilius.bastools.core.configurable

import com.basmilius.bastools.core.configurable.personal.BasToolsPersonalConfigurable
import javax.swing.*

/**
 * Class BasToolsConfigurableForm
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable
 * @since 1.3.0
 */
class BasToolsConfigurableForm: AbstractForm<BasToolsPersonalConfigurable.PersonalConfigurationState>()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun applyChanges()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun build()
	{
		this.layout = BoxLayout(this, BoxLayout.Y_AXIS)

		this.add(JButton("Hello world"))
		this.add(JButton("Hello world"))
		this.add(JButton("Hello world"))
		this.add(JButton("Hello world"))
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun load()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun update(state: BasToolsPersonalConfigurable.PersonalConfigurationState)
	{
	}

}
