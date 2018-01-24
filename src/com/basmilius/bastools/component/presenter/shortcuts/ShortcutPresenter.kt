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
 * @since 1.1.0
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
	 * @since 1.1.0
	 */
	override fun getState() = this.configuration

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun loadState(state: ShortcutPresenterState)
	{
		XmlSerializerUtil.copyBean(state, this.configuration)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun initComponent()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun disposeComponent()
	{
	}

	/**
	 * Enables shortcut presenter.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun enable()
	{
		if (this.isEnabled())
			return

		this.presenter = Presenter()
	}

	/**
	 * Disables shortcut presenter.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun disable()
	{
		this.presenter?.disable()
		this.presenter = null
	}

	/**
	 * Returns TRUE if Presenter Mode is currently active.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun isEnabled() = !(this.presenter === null)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun getComponentName() = "Shortcut Presentation"

	/**
	 * Sets showActionDescriptions.
	 *
	 * @param value Boolean
	 * @param project Project?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
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
	 * @since 1.1.0
	 */
	fun setFontSize(value: Int)
	{
		configuration.fontSize = value
	}

}
