package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Class ShortcutPresenter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 */
@State(name = "ShortcutPresenter", storages = arrayOf(Storage(file = "shortcut-presenter.xml")))
class ShortcutPresenter: ApplicationComponent, PersistentStateComponent<ShortcutPresenterState>
{

	val configuration = ShortcutPresenterState()
	var presenter: Presenter? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getState() = this.configuration

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun loadState(state: ShortcutPresenterState)
	{
		XmlSerializerUtil.copyBean(state, this.configuration)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun initComponent()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun disposeComponent()
	{
	}

	/**
	 * Enables shortcut presenter.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun enable()
	{
		if (this.presenter == null && this.configuration.showActionDescription)
		{
			this.presenter = Presenter()
		}
	}

	/**
	 * Disables shortcut presenter.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun disable()
	{
		this.presenter?.disable()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getComponentName() = "Shortcut Presentation"

	/**
	 * Sets showActionDescriptions.
	 *
	 * @param value Boolean
	 * @param project Project?
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun setShowActionsDescriptions(value: Boolean, project: Project?)
	{
		this.configuration.showActionDescription = value

		if (value && this.presenter == null)
		{
			this.presenter = Presenter().apply {
				showActionInfo(ActionData("shortcutPresenter.ShowActionDescriptions", project, "Show Descriptions of Actions"))
			}
		}

		if (!value && this.presenter != null)
		{
			this.presenter?.disable()
			this.presenter = null
		}
	}

	/**
	 * Sets the font size.
	 *
	 * @param value Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun setFontSize(value: Int)
	{
		configuration.fontSize = value
	}

}
