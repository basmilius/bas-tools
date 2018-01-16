package com.basmilius.bastools.action.code.json

import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonObject
import com.intellij.json.psi.JsonProperty
import com.intellij.json.psi.impl.JsonPropertyImpl
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.util.PsiTreeUtil

/**
 * Class JsonRearrangePropertiesAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.code.json
 * @since 1.3.0
 */
class JsonRearrangePropertiesAction: AnAction("Rearrange JSON properties")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val file = aae.getData(LangDataKeys.PSI_FILE) ?: return
		val objects = PsiTreeUtil.findChildrenOfType(file, JsonObject::class.java)

		if (objects.isEmpty())
			return

		val writer = object: WriteCommandAction.Simple<Unit>(aae.project!!)
		{

			override fun run()
			{
				objects.forEach { this@JsonRearrangePropertiesAction.rearrange(it) }
			}

		}

		writer.execute()
	}

	/**
	 * Rearranges the JSON properties recursively.
	 *
	 * @param obj JsonObject
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	private fun rearrange(obj: JsonObject)
	{
		val properties = PsiTreeUtil.getChildrenOfType(obj, JsonProperty::class.java) ?: return

		if (properties.isEmpty())
			return

		val arrangedProperties = properties
				.sortedBy { it.name }
				.map { it.copy() as JsonProperty }

		for (i in 0 until properties.size)
		{
			val prop = arrangedProperties[i]

			if (prop.value is JsonObject)
				this.rearrange(prop.value as JsonObject)

			obj.children.elementAt(obj.children.indexOf(properties[i])).replace(prop)
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun update(aae: AnActionEvent)
	{
		val file = aae.getData(LangDataKeys.PSI_FILE)

		aae.presentation.isEnabled = file is JsonFile
	}

}
