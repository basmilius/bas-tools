package com.basmilius.bastools.core.configurable

import com.basmilius.bastools.core.configurable.personal.BasToolsPersonalConfigurable
import javax.swing.*

/**
 * Class BasToolsConfigurableForm
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.configurable
 */
class BasToolsConfigurableForm: AbstractForm<BasToolsPersonalConfigurable.PersonalConfigurationState>()
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
		this.layout = BoxLayout(this, BoxLayout.Y_AXIS)

		this.add(JButton("Hello world"))
		this.add(JButton("Hello world"))
		this.add(JButton("Hello world"))
		this.add(JButton("Hello world"))
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun load()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun update(state: BasToolsPersonalConfigurable.PersonalConfigurationState)
	{
	}

}
