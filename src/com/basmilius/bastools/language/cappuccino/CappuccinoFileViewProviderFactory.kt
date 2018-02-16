/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino

import com.intellij.lang.Language
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.FileViewProviderFactory
import com.intellij.psi.PsiManager
import org.jetbrains.annotations.NotNull

/**
 * Class CappuccinoFileViewProviderFactory
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoFileViewProviderFactory: FileViewProviderFactory
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun createFileViewProvider(@NotNull file: VirtualFile, @NotNull language: Language, @NotNull manager: PsiManager, eventSystemEnabled: Boolean): FileViewProvider = CappuccinoFileViewProvider(manager, file, eventSystemEnabled)

}
