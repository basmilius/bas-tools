package com.basmilius.bastools.action.presenter

import com.basmilius.bastools.component.presenter.shortcuts.ShortcutPresenter
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAwareAction

/**
 * Class EnablePresenterModeAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.presenter
 * @since 1.4.0
 */
class EnablePresenterModeAction: DumbAwareAction("Enable Presenter Mode")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val shortcutPresenter = ApplicationManager.getApplication().getComponent(ShortcutPresenter::class.java)

		shortcutPresenter.enable()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun update(aae: AnActionEvent)
	{
		aae.presentation.isEnabled = !ApplicationManager.getApplication().getComponent(ShortcutPresenter::class.java).isEnabled()
	}

}
