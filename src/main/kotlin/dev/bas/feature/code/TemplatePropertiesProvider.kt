package dev.bas.feature.code

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.PsiDirectory
import java.util.*

class TemplatePropertiesProvider : DefaultTemplatePropertiesProvider {

    override fun fillProperties(directory: PsiDirectory, props: Properties) {
        val project = directory.project
        val root = project.guessProjectDir() ?: return
        val env = root.findChild(".env") ?: return

        env.inputStream.bufferedReader().lines().forEach {
            if (!it.startsWith("BT_") || !it.contains('=')) {
                return@forEach
            }

            val data = it.split("=", limit = 2)

            props[data[0]] = data[1].trim('"')
        }
    }

}