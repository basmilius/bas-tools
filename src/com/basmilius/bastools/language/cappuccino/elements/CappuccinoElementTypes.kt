package com.basmilius.bastools.language.cappuccino.elements

import com.basmilius.bastools.language.cappuccino.CappuccinoLanguage
import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.parser.CappuccinoKeywords
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.IStubFileElementType
import com.intellij.psi.tree.TokenSet

/**
 * Interface CappuccinoElementTypes
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.elements
 */
interface CappuccinoElementTypes: CappuccinoKeywords
{

	/**
	 * Companion Object CappuccinoElementTypes
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.elements
	 */
	companion object
	{

		val TAG = CappuccinoCompositeElementType("TAG")
		val CAPPUCCINO_STATEMENT = CappuccinoTag("CAPPUCCINO_STAGEMENT", true)
		val IF_STATEMENT: IElementType = CappuccinoCompositeElementType("IF_STATEMENT")
		val ELSE_STATEMENT: IElementType = CappuccinoCompositeElementType("ELSE_STATEMENT")
		val ELSEIF_STATEMENT: IElementType = CappuccinoCompositeElementType("ELSEIF_STATEMENT")
		val BLOCK_STATEMENT: IElementType = CappuccinoCompositeElementType("BLOCK_STATEMENT")
		val FOR_STATEMENT: IElementType = CappuccinoCompositeElementType("FOR_STATEMENT")
		val FILTER_STATEMENT: IElementType = CappuccinoCompositeElementType("FILTER_STATEMENT")
		val AUTOESCAPE_STATEMENT: IElementType = CappuccinoCompositeElementType("AUTOESCAPE_STATEMENT")
		val MACRO_STATEMENT: IElementType = CappuccinoCompositeElementType("MACRO_STATEMENT")
		val SPACELESS_STATEMENT: IElementType = CappuccinoCompositeElementType("SPACELESS_STATEMENT")
		val EMBED_STATEMENT: IElementType = CappuccinoCompositeElementType("EMBED_STATEMENT")
		val SANDBOX_STATEMENT: IElementType = CappuccinoCompositeElementType("SANDBOX_STATEMENT")
		val VERBATIM_STATEMENT: IElementType = CappuccinoCompositeElementType("VERBATIM_STATEMENT")
		val RAW_BLOCK: IElementType = CappuccinoCompositeElementType("RAW_BLOCK")
		val SET_STATEMENT: IElementType = CappuccinoCompositeElementType("SET_STATEMENT")
		val EXTENDS_TAG: IElementType = CappuccinoTag("extends", false)
		val IMPORT_TAG: IElementType = CappuccinoTag("import", false)
		val INCLUDE_TAG: IElementType = CappuccinoTag("include", false)
		val DO_TAG: IElementType = CappuccinoTag("do", false)
		val FLUSH_TAG: IElementType = CappuccinoTag("flush", false)
		val TAGS_WITH_FILE_REF = TokenSet.create(EXTENDS_TAG, IMPORT_TAG, INCLUDE_TAG)
		val IF_TAG: IElementType = CappuccinoTag("if", true)
		val ELSE_TAG: IElementType = CappuccinoTag("else", true)
		val ELSEIF_TAG: IElementType = CappuccinoTag("elseif", true)
		val ENDIF_TAG: IElementType = CappuccinoTag("endif", true)
		val BLOCK_TAG: IElementType = CappuccinoTag("block", true)
		val ENDBLOCK_TAG: IElementType = CappuccinoTag("endblock", true)
		val FOR_TAG: IElementType = CappuccinoTag("for", true)
		val ENDFOR_TAG: IElementType = CappuccinoTag("endfor", true)
		val FILTER_TAG: IElementType = CappuccinoTag("filter", true)
		val ENDFILTER_TAG: IElementType = CappuccinoTag("endfilter", true)
		val AUTOESCAPE_TAG: IElementType = CappuccinoTag("autoescape", true)
		val ENDAUTOESCAPE_TAG: IElementType = CappuccinoTag("endautoescape", true)
		val MACRO_TAG: IElementType = CappuccinoTag("macro", true)
		val ENDMACRO_TAG: IElementType = CappuccinoTag("endmacro", true)
		val SPACELESS_TAG: IElementType = CappuccinoTag("spaceless", true)
		val ENDSPACELESS_TAG: IElementType = CappuccinoTag("endspaceless", true)
		val EMBED_TAG: IElementType = CappuccinoTag("embed", true)
		val ENDEMBED_TAG: IElementType = CappuccinoTag("endembed", true)
		val SANDBOX_TAG: IElementType = CappuccinoTag("sandbox", true)
		val ENDSANDBOX_TAG: IElementType = CappuccinoTag("endsandbox", true)
		val VERBATIM_TAG: IElementType = CappuccinoTag("verbatim", true)
		val ENDVERBATIM_TAG: IElementType = CappuccinoTag("endverbatim", true)
		val RAW_TAG: IElementType = CappuccinoTag("raw", true)
		val ENDRAW_TAG: IElementType = CappuccinoTag("endraw", true)
		val SET_TAG: IElementType = CappuccinoTag("set", true)
		val ENDSET_TAG: IElementType = CappuccinoTag("endset", true)
		val TEMPLATE_DATA = TemplateDataElementType("CAPPUCCINO_TEMPLATE_DATA", CappuccinoLanguage.Instance, CappuccinoTokenTypes.TEMPLATE_HTML_TEXT, CappuccinoTokenTypes.OUTER_ELEMENT_TYPE)
		val CAPPUCCINO_FILE: IFileElementType = IStubFileElementType<PsiFileStub<PsiFile>>("CAPPUCCINO_FILE", CappuccinoLanguage.Instance)
		val TAGS = TokenSet.create(EXTENDS_TAG, IMPORT_TAG, IF_TAG, ELSE_TAG, ELSEIF_TAG, ENDIF_TAG, BLOCK_TAG, ENDBLOCK_TAG, FOR_TAG, ENDFOR_TAG, FILTER_TAG, ENDFILTER_TAG, AUTOESCAPE_TAG, ENDAUTOESCAPE_TAG, MACRO_TAG, ENDMACRO_TAG, SPACELESS_TAG, ENDSPACELESS_TAG, EMBED_TAG, ENDEMBED_TAG, SET_TAG, ENDSET_TAG, SANDBOX_TAG, ENDSANDBOX_TAG, VERBATIM_TAG, ENDVERBATIM_TAG, INCLUDE_TAG, FLUSH_TAG, DO_TAG, RAW_TAG, ENDRAW_TAG)
		val PRINT_BLOCK: IElementType = CappuccinoCompositeElementType("PRINT_BLOCK")
		val LITERAL: IElementType = CappuccinoCompositeElementType("LITERAL")

	}

}
