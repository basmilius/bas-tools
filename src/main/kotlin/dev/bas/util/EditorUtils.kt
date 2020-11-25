package dev.bas.util

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Computable

@Throws(Exception::class)
fun insertOrReplaceMultiCaret(editor: Editor, project: Project, str: String) {
    writeAction(project) {
        val document = editor.document
        val caret = editor.caretModel
        val selection = editor.selectionModel

        when {
            selection.hasSelection() -> {
                val starts = selection.blockSelectionStarts
                val ends = selection.blockSelectionEnds
                val length = starts.size - 1

                for (i in length downTo 0) {
                    document.replaceString(starts[i], ends[i], str)
                }
            }

            caret.caretCount > 0 -> {
                for (c in caret.allCarets) {
                    document.insertString(c.offset, str)
                    c.moveToOffset(c.offset + 7)
                }
            }

            else -> throw Exception("No carets found!")
        }
    }
}

fun writeAction(project: Project, command: () -> Unit) {
    WriteCommandAction.runWriteCommandAction(project, Computable {
        command()
    })
}
