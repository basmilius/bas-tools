/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action.code.json

import com.basmilius.bastools.core.util.EditorUtils
import com.basmilius.bastools.core.util.PsiUtils
import com.intellij.json.psi.*
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys

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
		val editor = aae.getData(LangDataKeys.EDITOR)
		val file = aae.getData(LangDataKeys.PSI_FILE) ?: return

		if (editor != null && editor.selectionModel.hasSelection())
		{
			val selectedPsiTree = PsiUtils.findElementOfClassAtOffset(file, editor.selectionModel.selectionStart, JsonElement::class, false) as? JsonObject ?: return

			EditorUtils.writeAction(file.project) {
				this.rearrange(selectedPsiTree)
			}
		}
		else
		{
			val objects = PsiUtils.findChildrenOfType(file, JsonObject::class)

			if (objects.isEmpty())
				return

			EditorUtils.writeAction(file.project) {
				objects.forEach { this.rearrange(it) }
			}
		}
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
		val properties = PsiUtils.getChildrenOfType(obj, JsonProperty::class) ?: return

		if (properties.isEmpty())
			return

		val arrangedProperties = properties
				.sortedBy { it.name }
				.map { it.copy() as JsonProperty }

		for (i in 0 until properties.size)
		{
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
