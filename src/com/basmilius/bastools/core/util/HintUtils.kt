/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.editor.Editor

fun showErrorHint(editor: Editor, str: String)
{
	HintManager.getInstance().showErrorHint(editor, str);
}

fun showInfoHint(editor: Editor, str: String)
{
	HintManager.getInstance().showInformationHint(editor, str);
}
