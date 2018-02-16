/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.base.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class BaseCompletionContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base.completion
 * @since 1.0.0
 */
abstract class BaseCompletionContributor: CompletionContributor()
{

	/**
	 * Adds completions.
	 *
	 * @param type CompletionType
	 * @param completionProvider BaseCompletionProvider
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun extend(type: CompletionType, completionProvider: BaseCompletionProvider)
	{
		if (!this.isAvailable())
			return

		completionProvider.setContributor(this)

		this.extend(type, completionProvider.getPlace(), completionProvider)
	}

	/**
	 * Returns TRUE if this completion provider should be available.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	abstract fun isAvailable(): Boolean

}
