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
import com.basmilius.bastools.resource.Icons
import com.intellij.icons.AllIcons

/**
 * Class IconPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class IconPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		ReflectionUtils.setFinalStatic(AllIcons.Actions::class, "Close", Icons.Close)
		ReflectionUtils.setFinalStatic(AllIcons.Actions::class, "CloseNew", Icons.Close)
		ReflectionUtils.setFinalStatic(AllIcons.Actions::class, "CloseHovered", Icons.CloseHover)
		ReflectionUtils.setFinalStatic(AllIcons.Actions::class, "CloseNewHovered", Icons.CloseHover)
	}

}
