package com.basmilius.bastools.core.configurable

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.options.Configurable

/**
 * Class AbstractConfigurable
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.configurable
 */
abstract class AbstractConfigurable<out TAbstractConfigForm: AbstractForm<*>, TState: PersistentStateComponent<TState>>: Configurable
{

	protected val form: TAbstractConfigForm = this.createForm()
	protected var state: TState? = null

	/**
	 * AbstractConfigurable Constructor
	 * @author Bas Milius
	 */
	init
	{
		this.form.build()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun apply()
	{
		this.form.applyChanges()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
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
	 * @author Bas Milius
	 */
	abstract protected fun createForm(): TAbstractConfigForm

	/**
	 * Creates the State.
	 *
	 * @return TState
	 *
	 * @author Bas Milius
	 */
	abstract protected fun createState(): TState

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override final fun createComponent(): AbstractForm<*> = this.form

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override final fun getDisplayName(): String = AbstractConfigurable::class.qualifiedName!!

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override final fun isModified() = this.form.modified

}
