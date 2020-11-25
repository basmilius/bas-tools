package dev.bas.feature.codeShot

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import dev.bas.util.dontCare
import dev.bas.util.showErrorHint
import dev.bas.util.showInfoHint
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.Transferable

class CodeShotAction : AnAction("Code Shot"), ClipboardOwner {

    override fun actionPerformed(aae: AnActionEvent) {
        val project = aae.project ?: return
        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        val document = editor.document
        val selectionModel = editor.selectionModel

        if (selectionModel.blockSelectionStarts.size >= 2) {
            return showErrorHint(editor, "CodeShot is not available with multiple selections at the moment.")
        }

        val start = selectionModel.selectionStart
        val end = selectionModel.selectionEnd
        val startLine = document.getLineNumber(start)
        val endLine = document.getLineNumber(end) + 1

        selectionModel.removeSelection()

        val fragment = CodeFragment.createCodeFragmentComponent(editor, startLine, endLine)

        dontCare {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val picture = createBufferedImage(fragment)
            val transferable = TransferableImage(picture)

            clipboard.setContents(transferable, this)
        }

        selectionModel.setSelection(start, end)
        showInfoHint(editor, "Successfully took a CodeShot and saved it to your clipboard!")
    }

    override fun update(aae: AnActionEvent) {
        val project = aae.getData(LangDataKeys.PROJECT)
        val editor = aae.getData(LangDataKeys.EDITOR)

        aae.presentation.isEnabled = project != null && editor != null && editor.selectionModel.hasSelection()
    }

    override fun lostOwnership(clipboard: Clipboard?, contents: Transferable?) {
    }

}
