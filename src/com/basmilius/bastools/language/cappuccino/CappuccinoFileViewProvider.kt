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
 * @author Bas Milius
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
	 * @author Bas Milius
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
	 * @author Bas Milius
	 */
	constructor(@NotNull manager: PsiManager, @NotNull virtualFile: VirtualFile, eventSystemEnabled: Boolean, @NotNull templateDataLanguage: Language): super(manager, virtualFile, eventSystemEnabled, templateDataLanguage)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getTemplateDataType() = CappuccinoElementTypes.TEMPLATE_DATA

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@NotNull
	override fun getBaseLanguage(): Language = CappuccinoLanguage.Instance

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun cloneInner(fileCopy: VirtualFile): CappuccinoFileViewProvider = CappuccinoFileViewProvider(this.manager, fileCopy, false, this.templateDataLanguage)

}
