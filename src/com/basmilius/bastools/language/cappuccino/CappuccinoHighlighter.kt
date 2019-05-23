/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino

import com.basmilius.bastools.language.cappuccino.lexer.CappuccinoLexerAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypes
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType
import com.intellij.webcore.template.TemplateLanguageFileUtil
import org.jetbrains.annotations.NotNull

/**
 * Class CappuccinoHighlighter
 *
 * @constructor
 * @param project Project?
 * @param virtualFile VirtualFile?
 * @param colors EditorColorsScheme
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoHighlighter(project: Project?, virtualFile: VirtualFile?, colors: EditorColorsScheme): LayeredLexerEditorHighlighter(CappuccinoFileHighlighter(), colors)
{

	/**
	 * CappuccinoHighlighter Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	init
	{
		var fileType: FileType? = null

		if (project != null && virtualFile != null)
			fileType = TemplateLanguageFileUtil.getTemplateDataLanguage(project, virtualFile).associatedFileType

		if (fileType == null)
			fileType = FileTypes.PLAIN_TEXT

		this.registerLayer(CappuccinoTokenTypes.TEMPLATE_HTML_TEXT, LayerDescriptor(SyntaxHighlighterFactory.getSyntaxHighlighter(fileType!!, project, virtualFile)!!, ""))
	}

	/**
	 * Class CappuccinoFileHighlighter
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.CappuccinoHighlighter
	 */
	class CappuccinoFileHighlighter: SyntaxHighlighterBase()
	{

		/**
		 * Companion Object CappuccinoFileHighlighter
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @package com.basmilius.bastools.language.cappuccino.CappuccinoHighlighter
		 */
		companion object
		{

			private val Keys = HashMap<IElementType, TextAttributesKey>()

			/**
			 * CappuccinoFileHighlighter Constructor.
			 *
			 * @author Bas Milius <bas@mili.us>
			 */
			init
			{
				fillMap(Keys, CappuccinoTokenTypes.KEYWORDS, CappuccinoHighlighterData.KEYWORD)
				fillMap(Keys, CappuccinoTokenTypes.OPERATORS, CappuccinoHighlighterData.OPERATION_SIGN)
				fillMap(Keys, CappuccinoTokenTypes.STRING_LITERALS, CappuccinoHighlighterData.STRING)
				fillMap(Keys, CappuccinoTokenTypes.PARENTHS, CappuccinoHighlighterData.BRACKETS)
				Keys[CappuccinoTokenTypes.COMMENT] = CappuccinoHighlighterData.COMMENT
				Keys[CappuccinoTokenTypes.NUMBER] = CappuccinoHighlighterData.NUMBER
				Keys[CappuccinoTokenTypes.BAD_CHARACTER] = CappuccinoHighlighterData.BAD_CHARACTER
				Keys[CappuccinoTokenTypes.IDENTIFIER] = CappuccinoHighlighterData.IDENTIFIER
			}

		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		@NotNull
		override fun getHighlightingLexer(): Lexer = CappuccinoLexerAdapter()

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		@NotNull
		override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> = pack(CappuccinoHighlighterData.TEMPLATE, Keys[tokenType])

	}

}
