/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.patch

import com.basmilius.bastools.core.util.ReflectionUtils
import com.intellij.ui.tabs.TabsUtil
import com.intellij.util.ui.JBValue

/**
 * Class TabsUtilPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class TabsUtilPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		ReflectionUtils.setFinalStatic(TabsUtil::class, "TAB_VERTICAL_PADDING", JBValue.Float(9f))
		ReflectionUtils.setFinalStatic(TabsUtil::class, "TABS_BORDER", JBValue.Float(3f))
	}

}
