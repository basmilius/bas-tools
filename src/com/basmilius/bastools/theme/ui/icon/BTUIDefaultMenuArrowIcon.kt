/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.theme.ui.icon

import com.intellij.util.IconUtil
import com.intellij.util.ui.UIUtil
import javax.swing.Icon

/**
 * Class BTUIDefaultMenuArrowIcon
 *
 * @constructor
 * @param icon Icon
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.icon
 * @since 1.4.0
 */
class BTUIDefaultMenuArrowIcon(icon: Icon): BTUIMenuArrowIcon(
		if (invert) IconUtil.brighter(icon, 2) else IconUtil.darker(icon, 2),
		if (invert) IconUtil.brighter(icon, 8) else IconUtil.darker(icon, 8),
		if (invert) IconUtil.darker(icon, 2) else IconUtil.brighter(icon, 2)
)
{

	/**
	 * Companion Object BTUIDefaultMenuArrowIcon
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.ui.laf.icon
	 * @since 1.4.0
	 */
	companion object
	{

		val invert = UIUtil.isUnderDarcula()

	}

}
