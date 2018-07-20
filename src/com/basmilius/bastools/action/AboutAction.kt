/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action

import com.basmilius.bastools.core.util.ApplicationUtils
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages

/**
 * Class AboutAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action
 * @since 1.0.0
 */
class AboutAction: DumbAwareAction("About Bas Tools")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		ApplicationUtils.invokeLater {
			Messages.showMessageDialog(aae.project, "Bas Tools 1.4.0, go to basmilius.com for more information about this plugin.", "Bas Tools", Messages.getInformationIcon())
		}
	}

}
