/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.columba

import com.basmilius.bastools.framework.base.AbstractFramework
import com.basmilius.bastools.resource.Icons
import com.intellij.openapi.project.Project
import com.intellij.util.PlatformUtils
import com.jetbrains.php.actions.newClassDataProvider.ClassCreationType

/**
 * Class ColumbaFramework
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.columba
 * @since 1.4.0
 */
class ColumbaFramework: AbstractFramework("Columba by Bas Milius")
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onLoad()
	{
		if (!PlatformUtils.isPhpStorm())
			return

		ClassCreationType.BUNDLED.add(ClassCreationType("Router", Icons.BasTools, "Router"))
		ClassCreationType.BUNDLED.add(ClassCreationType("Renderer implementation", Icons.BasTools, "Renderer Implementation"))
		ClassCreationType.BUNDLED.add(ClassCreationType("Response implementation", Icons.BasTools, "Response Implementation"))
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onUnload()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onProjectClosed(project: Project)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onProjectOpened(project: Project)
	{
	}

}
