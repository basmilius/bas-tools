/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino

import com.intellij.codeInsight.highlighting.TemplateLanguageErrorFilter
import com.intellij.lang.html.HTMLLanguage
import com.intellij.psi.tree.TokenSet

/**
 * Class CappuccinoHighlightErrorFilter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.intellij.codeInsight.highlighting.TemplateLanguageErrorFilter
 */
class CappuccinoHighlightErrorFilter: TemplateLanguageErrorFilter(TokenSet.create(CappuccinoTokenTypes.STATEMENT_BLOCK_START, CappuccinoTokenTypes.PRINT_BLOCK_START, CappuccinoTokenTypes.PRINT_BLOCK_END), CappuccinoFileViewProvider::class.java, HTMLLanguage.INSTANCE.id)
