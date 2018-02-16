/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.provider

import com.basmilius.bastools.resource.Icons
import com.intellij.ide.FileIconProvider
import com.intellij.ide.IconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import javax.swing.Icon

/**
 * Class BasToolsIconProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.provider
 * @since 1.0.0
 */
class BasToolsIconProvider: IconProvider(), FileIconProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getIcon(file: VirtualFile, @Iconable.IconFlags flags: Int, project: Project?): Icon?
	{
		return Icons.Default
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getIcon(psi: PsiElement, @Iconable.IconFlags flags: Int) = Icons.Default

}
