package com.basmilius.bastools.language.cappuccino

import com.intellij.codeInsight.highlighting.TemplateLanguageErrorFilter
import com.intellij.lang.html.HTMLLanguage
import com.intellij.psi.tree.TokenSet

/**
 * Class CappuccinoHighlightErrorFilter
 *
 * @author Bas Milius
 * @package com.intellij.codeInsight.highlighting.TemplateLanguageErrorFilter
 */
class CappuccinoHighlightErrorFilter: TemplateLanguageErrorFilter(TokenSet.create(CappuccinoTokenTypes.STATEMENT_BLOCK_START, CappuccinoTokenTypes.PRINT_BLOCK_START, CappuccinoTokenTypes.PRINT_BLOCK_END), CappuccinoFileViewProvider::class.java, HTMLLanguage.INSTANCE.id)
