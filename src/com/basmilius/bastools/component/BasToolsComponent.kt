/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component

import com.basmilius.bastools.framework.all.AllFramework
import com.basmilius.bastools.framework.base.AbstractFramework
import com.basmilius.bastools.framework.columba.ColumbaFramework
import com.basmilius.bastools.theme.BTTheme
import com.intellij.openapi.components.BaseComponent

/**
 * Class BasToolsComponent
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component
 * @since 1.1.0
 */
class BasToolsComponent: BaseComponent
{

	/**
	 * Companion Object BasToolsComponent
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.component
	 * @since 1.1.0
	 */
	companion object
	{

		/**
		 * BasToolsComponent Initializer.
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		init
		{
			BTTheme.apply()
		}

		/**
		 * Gets a list with registred frameworks.
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		val Frameworks: List<AbstractFramework> = arrayListOf(
				AllFramework(),
				ColumbaFramework()
		)

	}

	/**
	 * Does something with all frameworks.
	 *
	 * @param func Closure
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun withFrameworks(func: (AbstractFramework) -> Unit)
	{
		Frameworks.forEach { func(it) }
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun initComponent()
	{
		try
		{
			BTTheme.initFileListener()
			BTTheme.initLafListener()

			Frameworks.forEach { it.onLoad() }
		}
		catch (e: Exception)
		{
			e.printStackTrace()
		}
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun disposeComponent()
	{
		Frameworks.forEach { it.onUnload() }
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun getComponentName() = "Bas Tools"

}
