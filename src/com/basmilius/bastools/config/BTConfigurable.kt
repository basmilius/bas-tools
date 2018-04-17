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

abstract class BTConfigurable<TConfigUI: IBTConfigUI, TConfigState: PersistentStateComponent<TConfigState>>(private val configDisplayName: String): BaseConfigurable(), Disposable
{

	companion object
	{

		inline fun <reified TConfigState> getStateInstance(clazz: KClass<*>): TConfigState
		{
			return ServiceManager.getService(clazz.java) as TConfigState
		}

	}

	private var ui: TConfigUI? = null

	override fun apply()
	{
		this.initComponent()

		val ui = this.getUI() ?: return

		this.saveChanges(ui, this.getState())
	}

	override fun createComponent(): JComponent?
	{
		this.initComponent()

		val ui = this.getUI() ?: return null

		this.setUIState(ui, this.getState())

		return ui.getComponent()
	}

	override fun disposeUIResources()
	{
		this.dispose()
		this.getUI()?.dispose()
		this.setUI(null)
	}

	override fun getDisplayName() = this.configDisplayName

	override fun isModified(): Boolean
	{
		val ui = this.getUI()

		if (ui != null)
			this.isModified = this.isModified(ui, this.getState())

		return super.isModified()
	}

	override fun reset()
	{
		this.initComponent()

		val ui = this.getUI() ?: return

		this.setUIState(ui, this.getState())
	}

	protected fun getUI(): TConfigUI?
	{
		return this.ui
	}

	protected fun setUI(ui: TConfigUI?)
	{
		this.ui = ui
	}

	protected abstract fun createUI(): TConfigUI

	protected abstract fun getState(): TConfigState

	protected abstract fun isModified(ui: TConfigUI, state: TConfigState): Boolean

	protected abstract fun saveChanges(ui: TConfigUI, state: TConfigState)

	@Throws(ConfigurationException::class)
	protected abstract fun setUIState(ui: TConfigUI, state: TConfigState)

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