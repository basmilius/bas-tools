package dev.bas.util

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.editor.Editor

fun showErrorHint(editor: Editor, str: String) {
    HintManager.getInstance().showErrorHint(editor, str);
}

fun showInfoHint(editor: Editor, str: String) {
    HintManager.getInstance().showInformationHint(editor, str);
}
