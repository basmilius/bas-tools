/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.base

import com.intellij.openapi.project.Project

/**
 * Class AbstractFramework
 *
 * @constructor
 * @param name String
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base
 * @since 1.4.0
 */
abstract class AbstractFramework(val name: String)
{

	/**
	 * Invoked when Bas Tools is loaded.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	abstract fun onLoad()

	/**
	 * Invoked when Bas Tools is unloaded.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	abstract fun onUnload()

	/**
	 * Invoked when a project is closed.
	 *
	 * @param project Project
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	abstract fun onProjectClosed(project: Project)

	/**
	 * Invoked when a project is opened.
	 *
	 * @param project Project
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	abstract fun onProjectOpened(project: Project)

}
