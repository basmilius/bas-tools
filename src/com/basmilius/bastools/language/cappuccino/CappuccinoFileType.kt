package com.basmilius.bastools.language.cappuccino

import com.basmilius.bastools.resource.Icons
import com.intellij.openapi.fileTypes.FileTypeEditorHighlighterProviders
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

/**
 * Class CappuccinoFileType
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoFileType: LanguageFileType(CappuccinoLanguage.Instance)
{

	/**
	 * Companion Object CappuccinoFileType
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino
	 */
	companion object
	{

		val Instance = CappuccinoFileType()

	}

	/**
	 * CappuccinoFileType Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getIcon(): Icon = Icons.CappuccinoFile

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getDefaultExtension(): String = CappuccinoLanguage.DefaultExtension

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getDescription(): String = CappuccinoLanguage.Description

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getName(): String = CappuccinoLanguage.LanguageName

}
