/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.config.codeMap

import com.basmilius.bastools.config.IBTConfigUI
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Class BTCodeMapConfigUI
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.config.codeMap
 * @since 1.4.0
 */
class BTCodeMapConfigUI: IBTConfigUI
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun initialize()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getComponent(): JComponent
	{
		return JPanel()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun dispose()
	{
	}

}
