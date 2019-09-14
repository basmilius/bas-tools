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
 * @param predicate {(VirtualFile) -> Boolean}
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
	open fun isMatch(file: VirtualFile?) = file != null && this.predicate(file)

	/**
	 * Class Directory
	 *
	 * @param predicate {(VirtualFile) -> Boolean}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.fileGroups.Entry
	 * @since 1.5.0
	 */
	open class Directory(predicate: (VirtualFile) -> Boolean) : Entry(predicate)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.5.0
		 */
		override fun isMatch(file: VirtualFile?): Boolean
		{
			return file != null && file.isDirectory && super.isMatch(file)
		}

		/**
		 * Class ContainsChild
		 *
		 * @param name {String}
		 * @param deep {Boolean}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @package com.basmilius.bastools.fileGroups.Entry.File
		 * @since 1.5.0
		 */
		open class ContainsChild(name: String, deep: Boolean = false) : Directory({
			if (deep)
				it.findChild(name) != null
			else
				it.children.any { child -> child.name == name }
		})

	}

	/**
	 * Class File
	 *
	 * @param predicate {(VirtualFile) -> Boolean}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.fileGroups.Entry
	 * @since 1.5.0
	 */
	open class File(predicate: (VirtualFile) -> Boolean) : Entry(predicate)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.5.0
		 */
		override fun isMatch(file: VirtualFile?): Boolean
		{
			return file != null && !file.isDirectory && super.isMatch(file)
		}

		/**
		 * Class ExactMatch
		 *
		 * @param name {String}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @package com.basmilius.bastools.fileGroups.Entry.File
		 * @since 1.5.0
		 */
		class ExactMatch(name: String) : File({ it.name == name })

		/**
		 * Class ExtensionlessExactMatch
		 *
		 * @param name {String}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @package com.basmilius.bastools.fileGroups.Entry.File
		 * @since 1.5.0
		 */
		class ExtensionlessExactMatch(name: String) : File({ it.nameWithoutExtension == name })

		/**
		 * Class StartsWithMatch
		 *
		 * @param needle {String}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @package com.basmilius.bastools.fileGroups.Entry.File
		 * @since 1.5.0
		 */
		class StartsWithMatch(needle: String) : File({ it.name.startsWith(needle) })

	}

}
