package dev.bas.feature.code

import com.intellij.json.psi.*
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import dev.bas.util.findChildrenOfType
import dev.bas.util.findElementOfClassAtOffset
import dev.bas.util.getChildrenOfType
import dev.bas.util.writeAction

class RearrangeJsonPropertiesAction : AnAction("Rearrange JSON properties") {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(LangDataKeys.EDITOR)
        val file = e.getData(LangDataKeys.PSI_FILE) ?: return

        if (editor != null && editor.selectionModel.hasSelection()) {
            val selectedPsiTree = findElementOfClassAtOffset(file, editor.selectionModel.selectionStart, JsonElement::class, false) as? JsonObject ?: return

            writeAction(file.project) {
                this.rearrange(selectedPsiTree)
            }
        } else {
            val objects = findChildrenOfType(file, JsonObject::class)

            if (objects.isEmpty())
                return

            writeAction(file.project) {
                objects.forEach { this.rearrange(it) }
            }
        }
    }

    private fun rearrange(obj: JsonObject) {
        val propertiesRaw = getChildrenOfType(obj, JsonProperty::class) ?: return
        val properties = propertiesRaw.filterNotNull()

        if (properties.isEmpty())
            return

        val arrangedProperties = properties
            .sortedBy { it.name }
            .map { it.copy() as JsonProperty }

        for (i in properties.indices) {
            val prop = arrangedProperties[i]
            val value = prop.value

            if (value is JsonArray)
                value.valueList
                    .mapNotNull { it as? JsonObject }
                    .forEach { this.rearrange(it) }

            if (value is JsonObject)
                this.rearrange(value)

            obj.children.elementAt(obj.children.indexOf(properties[i])).replace(prop)
        }
    }

    override fun update(e: AnActionEvent) {
        val file = e.getData(LangDataKeys.PSI_FILE)

        e.presentation.isEnabled = file is JsonFile
    }

}
