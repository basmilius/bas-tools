package dev.bas.feature

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.util.IconUtil
import dev.bas.resource.BTIcons
import dev.bas.util.invokeLater

class AboutAction : AnAction("About Bas Tools") {

    override fun actionPerformed(e: AnActionEvent) {
        invokeLater {
            Messages.showMessageDialog(
                e.project,
                "Bas Tools 2.1, go to basmilius.com for more information about this plugin.",
                "Bas Tools",
                IconUtil.scale(BTIcons.BasTools, null, 2f)
            )
        }
    }

}
