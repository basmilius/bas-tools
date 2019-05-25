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
import com.basmilius.bastools.core.util.ApplicationUtils
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

/**
 * Class DisablePresenterModeAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.presenter
 * @since 1.4.0
 */
class DisablePresenterModeAction: DumbAwareAction("Disable Presenter Mode")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		ApplicationUtils.withComponent(ShortcutPresenter::class) {
			it.disable()
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun update(aae: AnActionEvent)
	{
		ApplicationUtils.withComponent(ShortcutPresenter::class) {
			aae.presentation.isEnabled = it.isEnabled()
		}
	}

}
