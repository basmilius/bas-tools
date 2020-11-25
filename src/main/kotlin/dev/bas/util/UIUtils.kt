package dev.bas.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import javax.swing.JComponent

fun getRootComponent(project: Project?): JComponent? {
    if (project != null) {
        val frame = WindowManager.getInstance().getIdeFrame(project)

        if (frame != null) {
            return frame.component
        }
    }

    return WindowManager.getInstance().findVisibleFrame()?.rootPane
}
