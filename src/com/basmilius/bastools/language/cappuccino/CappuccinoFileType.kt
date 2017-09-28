package com.basmilius.bastools.language.cappuccino

import com.basmilius.bastools.resource.Icons
import com.intellij.openapi.fileTypes.FileTypeEditorHighlighterProviders
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

/**
 * Class CappuccinoFileType
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoFileType: LanguageFileType(CappuccinoLanguage.Instance)
{

	/**
	 * Companion Object CappuccinoFileType
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino
	 */
	companion object
	{

		val Instance = CappuccinoFileType()

	}

	/**
	 * CappuccinoFileType Constructor.
	 *
	 * @author Bas Milius
	 */
	init
	{
		FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(this) { project, _, virtualFile, colors ->
			CappuccinoHighlighter(project, virtualFile, colors)
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getIcon(): Icon = Icons.CoffeeBean

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDefaultExtension(): String = CappuccinoLanguage.DefaultExtension

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDescription(): String = CappuccinoLanguage.Description

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getName(): String = CappuccinoLanguage.LanguageName

}
