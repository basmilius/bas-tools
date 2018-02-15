package com.basmilius.bastools.ui.laf.icon

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
		IconUtil.brighter(icon, 8),
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
