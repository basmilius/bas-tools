package dev.bas.feature.quickActions

import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.ColorUtil
import com.intellij.ui.picker.ColorListener
import com.intellij.ui.picker.ColorPipette
import dev.bas.util.*
import java.awt.Color
import java.awt.event.WindowEvent
import java.awt.event.WindowListener

class ShowColorPipetteAction : AnAction("Show Color Pipette"), ColorListener, Disposable, WindowListener {

    private var currentColor: Color? = null
    private var editor: Editor? = null
    private var project: Project? = null

    override fun actionPerformed(aae: AnActionEvent) {
        this.project = aae.project

        val project = this.project ?: return
        val root = getRootComponent(project) ?: return

        this.editor = FileEditorManager.getInstance(project).selectedTextEditor

        val editor = this.editor ?: return
        val pipette = getPipetteIfAvailable(DefaultColorPipette(root, this), this) ?: return showErrorHint(
            editor,
            "Could launch color pipette because no implementation could launch."
        )

        pipette.setInitialColor(Color.BLACK)
        pipette.show().addWindowListener(this)
    }

    override fun colorChanged(color: Color, o: Any) {
        this.currentColor = color
    }

    override fun windowOpened(e: WindowEvent) {

    }

    override fun windowClosing(e: WindowEvent) {

    }

    override fun windowClosed(e: WindowEvent) {
        val editor = this.editor ?: return
        val project = this.project ?: return
        val currentColor = this.currentColor ?: return showInfoHint(editor, "Could not insert color because no color was selected.")

        editor.selectionModel.removeSelection()

        processDontCare {
            insertOrReplaceMultiCaret(editor, project, "#" + ColorUtil.toHex(currentColor))
        }
    }

    override fun windowIconified(e: WindowEvent) {

    }

    override fun windowDeiconified(e: WindowEvent) {

    }

    override fun windowActivated(e: WindowEvent) {

    }

    override fun windowDeactivated(e: WindowEvent) {
        e.window.dispose()
    }

    override fun dispose() {

    }

    private fun getPipetteIfAvailable(pipette: ColorPipette, parentDisposable: Disposable): ColorPipette? {
        return if (pipette.isAvailable) {
            Disposer.register(parentDisposable, pipette)
            pipette
        } else {
            Disposer.dispose(pipette)
            null
        }
    }

}
