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
import com.intellij.openapi.editor.impl.EditorMarkupModelImpl

/**
 * Class EditorMarkupModelImplPatch
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.patch
 * @since 1.4.0
 */
class EditorMarkupModelImplPatch: IUIPatch
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun patch()
	{
		ReflectionUtils.setFinalStatic(EditorMarkupModelImpl::class, "EDITOR_FRAGMENT_POPUP_BORDER", 9)
	}

}
