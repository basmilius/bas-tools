package dev.bas.feature.quickActions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.ui.ColorPicker
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor
import dev.bas.util.getRootComponent
import dev.bas.util.insertOrReplaceMultiCaret
import dev.bas.util.processDontCare

class ShowColorPickerAction : AnAction("Show Color Picker") {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val root = getRootComponent(project) ?: return

        val color = ColorPicker.showDialog(root, "Pick a Color", JBColor.CYAN, false, null, false) ?: return
        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return

        processDontCare {
            insertOrReplaceMultiCaret(editor, project, "#" + ColorUtil.toHex(color))
        }
    }

}
