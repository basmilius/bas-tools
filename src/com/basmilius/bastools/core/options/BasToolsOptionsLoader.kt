package com.basmilius.bastools.core.options

import com.google.gson.JsonParser

object BasToolsOptionsLoader
{

	fun readFromFile(bytes: ByteArray?): BasToolsOptions?
	{
		val presenter = BasToolsPresenterOptions(false)

		if (bytes != null)
		{
			val parser = JsonParser()
			val json = parser.parse(String(bytes)).asJsonObject

			if (json.has("presenter") && json.get("presenter").isJsonObject)
			{
				val jsonPresenter = json.getAsJsonObject("presenter")

				presenter.enabled = jsonPresenter.has("enabled") && jsonPresenter.getAsJsonPrimitive("enabled").isBoolean && jsonPresenter.getAsJsonPrimitive("enabled").asBoolean
			}
		}

		return BasToolsOptions(
				presenter = presenter
		)
	}

}
