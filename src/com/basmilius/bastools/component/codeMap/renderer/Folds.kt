/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.renderer

import com.intellij.openapi.editor.FoldRegion

/**
 * Class Folds
 *
 * @constructor
 * @param allFolds Array<FoldRegion>
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.renderer
 * @since 1.4.0
 */
class Folds(allFolds: Array<FoldRegion>)
{

	private val folds = IntArray(allFolds.count { it.isExpanded } * 2)

	/**
	 * Folds Initializer.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	init
	{
		var i = 0

		allFolds.forEach {
			if (!it.isExpanded)
			{
				folds[i++] = it.startOffset
				folds[i++] = it.endOffset
			}
		}
	}

	/**
	 * Returns TRUE if the given position is within a folded region.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun isFolded(position: Int): Boolean
	{
		for (i in 0 until folds.size step 2)
			if (folds[i] <= position && position < folds[i + 1])
				return true

		return false
	}

}
