/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.configurable

import javax.swing.JComponent

/**
 * Class AbstractForm
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable
 * @since 1.3.0
 */
abstract class AbstractForm<in TState>: JComponent()
{

	private var _modified = false

	/**
	 * Gets or Sets modified.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	var modified: Boolean
		get() = this._modified
		protected set(value)
		{
			this._modified = value
		}

	/**
	 * Applies changes.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	abstract fun applyChanges()

	/**
	 * Builds the form.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	abstract fun build()

	/**
	 * Loads the state.
	 *
	 * @author Bas Miius <bas@mili.us>
	 * @since 1.3.0
	 */
	abstract fun load()

	/**
	 * Updates the form with configurable state.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	abstract fun update(state: TState)

}
