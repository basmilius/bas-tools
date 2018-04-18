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
import javax.swing.JComponent

/**
 * Interface IBTConfigUI
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.config
 * @since 1.4.0
 */
interface IBTConfigUI: Disposable
{

	/**
	 * Initializes our config ui.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun initialize()

	/**
	 * Gets the root component.
	 *
	 * @return {JComponent}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun getComponent(): JComponent

}
