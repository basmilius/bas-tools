package com.basmilius.bastools.core.configurable

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.options.Configurable

/**
 * Class AbstractConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable
 * @since 1.3.0
 */
abstract class AbstractConfigurable<out TAbstractConfigForm: AbstractForm<*>, TState: PersistentStateComponent<TState>>: Configurable
{

	protected val form: TAbstractConfigForm = this.createForm()
	protected var state: TState? = null

	/**
	 * AbstractConfigurable Constructor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	init
	{
		this.form.build()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun apply()
	{
		this.form.applyChanges()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun reset()
	{
		this.form.load()
	}

	/**
	 * Creates the form used for this configurable.
	 *
	 * @return TAbstractConfigForm
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	protected abstract fun createForm(): TAbstractConfigForm

	/**
	 * Creates the State.
	 *
	 * @return TState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	protected abstract fun createState(): TState

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	final override fun createComponent(): AbstractForm<*> = this.form

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	final override fun getDisplayName(): String = AbstractConfigurable::class.qualifiedName!!

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	final override fun isModified() = this.form.modified

}
