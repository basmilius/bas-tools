/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.fileGroups

import com.intellij.openapi.vfs.VirtualFile

/**
 * Class Entry
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.fileGroups
 * @since 1.5.0
 */
open class Entry(private val predicate: (VirtualFile) -> Boolean)
{

	/**
	 * Returns TRUE when the given {@see file} matches our {@see this.predicate}.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	fun isMatch(file: VirtualFile?) = file != null && this.predicate(file)

	/**
	 * Class ExactMatch
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.fileGroups.Entry
	 * @since 1.5.0
	 */
	class ExactMatch(val name: String): Entry({ it.name == name })

	/**
	 * Class LooseMatch
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.fileGroups.Entry
	 * @since 1.5.0
	 */
	class LooseMatch(val name: String): Entry({ it.nameWithoutExtension == name })

}
