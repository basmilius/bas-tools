/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.configurable.presenter

import com.basmilius.bastools.core.configurable.AbstractForm
import com.intellij.uiDesigner.core.GridLayoutManager
import java.awt.Dimension

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
		this.layout = GridLayoutManager(1, 1)
		this.maximumSize = Dimension(505, Int.MAX_VALUE)

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
