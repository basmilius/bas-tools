package com.basmilius.bastools.core.configurable

import javax.swing.JComponent

/**
 * Class AbstractForm
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable
 */
abstract class AbstractForm<in TState>: JComponent()
{

	private var _modified = false

	/**
	 * Gets or Sets modified.
	 *
	 * @author Bas Milius <bas@mili.us>
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
	 */
	abstract fun applyChanges()

	/**
	 * Builds the form.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	abstract fun build()

	/**
	 * Loads the state.
	 *
	 * @author Bas Miius
	 */
	abstract fun load()

	/**
	 * Updates the form with configurable state.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	abstract fun update(state: TState)

}
