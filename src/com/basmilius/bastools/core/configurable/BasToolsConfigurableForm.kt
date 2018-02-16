/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.configurable

import com.basmilius.bastools.core.configurable.personal.BasToolsPersonalConfigurable
import javax.swing.BoxLayout
import javax.swing.JButton

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
