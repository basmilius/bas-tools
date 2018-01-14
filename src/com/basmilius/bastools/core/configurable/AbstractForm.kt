package com.basmilius.bastools.core.configurable

import javax.swing.JComponent

/**
 * Class AbstractForm
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.configurable
 */
abstract class AbstractForm<in TState>: JComponent()
{

	private var _modified = false

	/**
	 * Gets or Sets modified.
	 *
	 * @author Bas Milius
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
	 * @author Bas Milius
	 */
	abstract fun applyChanges()

	/**
	 * Builds the form.
	 *
	 * @author Bas Milius
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
	 * @author Bas Milius
	 */
	abstract fun update(state: TState)

}
