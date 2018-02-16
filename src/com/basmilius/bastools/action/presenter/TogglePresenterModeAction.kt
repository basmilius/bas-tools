/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action.presenter

import com.basmilius.bastools.component.presenter.shortcuts.ShortcutPresenter
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAwareAction

/**
 * Class TogglePresenterModeAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.presenter
 * @since 1.4.0
 */
class TogglePresenterModeAction: DumbAwareAction("Toggle Presenter Mode")
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

		when (shortcutPresenter.isEnabled())
		{
			true -> shortcutPresenter.disable()
			false -> shortcutPresenter.enable()
		}
	}

}
