/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionContributor
import com.intellij.codeInsight.completion.CompletionType

/**
 * Class AllCompletionContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.completion
 * @since 1.2.0
 */
class AllCompletionContributor: BaseCompletionContributor()
{

	/**
	 * AllCompletionContributor Constructor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	init
	{
//		this.extend(CompletionType.BASIC, FunctionCallAllArgumentsCompletionProvider())
		this.extend(CompletionType.BASIC, BasicFunctionCompletionProvider())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	override fun isAvailable() = true

}
