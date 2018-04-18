/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.config

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.BaseConfigurable
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.util.Computable
import com.intellij.util.ui.UIUtil
import javax.swing.JComponent
import kotlin.reflect.KClass

/**
 * Class BTConfigurable
 *
 * @constructor
 * @param configDisplayName {String}
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.config
 * @since 1.4.0
 */
abstract class BTConfigurable<TConfigUI: IBTConfigUI, TConfigState: PersistentStateComponent<TConfigState>>(private val configDisplayName: String): BaseConfigurable(), Disposable
{

	/**
	 * Companion Object BTConfigurable
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.config
	 * @since 1.4.0
	 */
	companion object
	{

		/**
		 * Gets a state instance from ServiceManager.
		 *
		 * @param clazz {KClass<*>}
		 *
		 * @return {TConfigState}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		inline fun <reified TConfigState> getStateInstance(clazz: KClass<*>): TConfigState
		{
			return ServiceManager.getService(clazz.java) as TConfigState
		}

	}

	private var ui: TConfigUI? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun apply()
	{
		this.initComponent()

		val ui = this.getUI() ?: return

		this.saveChanges(ui, this.getState())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun createComponent(): JComponent?
	{
		this.initComponent()

		val ui = this.getUI() ?: return null

		this.setUIState(ui, this.getState())

		return ui.getComponent()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun disposeUIResources()
	{
		this.dispose()
		this.getUI()?.dispose()
		this.setUI(null)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getDisplayName() = this.configDisplayName

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun isModified(): Boolean
	{
		val ui = this.getUI()

		if (ui != null)
			this.isModified = this.isModified(ui, this.getState())

		return super.isModified()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun reset()
	{
		this.initComponent()

		val ui = this.getUI() ?: return

		this.setUIState(ui, this.getState())
	}

	/**
	 * Gets the config ui.
	 *
	 * @return {TConfigUI?}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	protected fun getUI(): TConfigUI?
	{
		return this.ui
	}

	/**
	 * Sets the config ui.
	 *
	 * @param ui {TConfigUI?}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	protected fun setUI(ui: TConfigUI?)
	{
		this.ui = ui
	}

	/**
	 * Creates the config ui.
	 *
	 * @return {TConfigUI}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	protected abstract fun createUI(): TConfigUI

	/**
	 * Gets the config state.
	 *
	 * @return {TConfigState}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	protected abstract fun getState(): TConfigState

	/**
	 * Returns TRUE if config is modified.
	 *
	 * @param ui {TConfigUI}
	 * @param state {TConfigState}
	 *
	 * @return {Boolean}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	protected abstract fun isModified(ui: TConfigUI, state: TConfigState): Boolean

	/**
	 * Saves changes from config ui to config state.
	 *
	 * @param ui {TConfigUI}
	 * @param state {TConfigState}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	protected abstract fun saveChanges(ui: TConfigUI, state: TConfigState)

	/**
	 * Sets the config ui from config state.
	 *
	 * @param ui {TConfigUI}
	 * @param state {TConfigState}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	@Throws(ConfigurationException::class)
	protected abstract fun setUIState(ui: TConfigUI, state: TConfigState)

	/**
	 * Initializes our component.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun initComponent()
	{
		if (this.getUI() != null)
			return

		val ui = UIUtil.invokeAndWaitIfNeeded(Computable {
			val x = this.createUI()

			x.initialize()

			return@Computable x
		})

		this.setUI(ui)
	}

}
