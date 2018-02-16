/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.intellij.lang.Language
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.webcore.template.AbstractTemplateLanguageFileViewProvider
import org.jetbrains.annotations.NotNull

/**
 * Class CappuccinoFileViewProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoFileViewProvider: AbstractTemplateLanguageFileViewProvider
{

	/**
	 * CappuccinoFileViewProvider Constructor.
	 *
	 * @param manager PsiManager
	 * @param virtualFile VirtualFile
	 * @param eventSystemEnabled Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(@NotNull manager: PsiManager, @NotNull virtualFile: VirtualFile, eventSystemEnabled: Boolean): super(manager, virtualFile, eventSystemEnabled)

	/**
	 * CappuccinoFileViewProvider Constructor.
	 *
	 * @param manager PsiManager
	 * @param virtualFile VirtualFile
	 * @param eventSystemEnabled Boolean
	 * @param templateDataLanguage Language
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(@NotNull manager: PsiManager, @NotNull virtualFile: VirtualFile, eventSystemEnabled: Boolean, @NotNull templateDataLanguage: Language): super(manager, virtualFile, eventSystemEnabled, templateDataLanguage)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getTemplateDataType() = CappuccinoElementTypes.TEMPLATE_DATA

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	@NotNull
	override fun getBaseLanguage(): Language = CappuccinoLanguage.Instance

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun cloneInner(fileCopy: VirtualFile): CappuccinoFileViewProvider = CappuccinoFileViewProvider(this.manager, fileCopy, false, this.templateDataLanguage)

}
