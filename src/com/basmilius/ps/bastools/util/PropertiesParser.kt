package com.basmilius.ps.bastools.util

import com.basmilius.ps.bastools.ui.laf.BasToolsLaf
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.text.StringUtil
import com.intellij.ui.ColorUtil
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBInsets
import org.jetbrains.annotations.NotNull
import java.awt.Color
import java.awt.Dimension
import java.awt.Insets
import javax.swing.plaf.BorderUIResource
import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.IconUIResource

object PropertiesParser
{

	private var System = Any()

	private fun getInteger(value : String) : Int?
	{
		try
		{
			return Integer.parseInt(value)
		}
		catch (e : Exception)
		{
			return null
		}
	}

	private fun parseColor(value : String) : Color?
	{
		if (value.length == 8)
		{
			val color = ColorUtil.fromHex(value.substring(0, 6))

			try
			{
				val alpha = Integer.parseInt(value.substring(6, 8), 16)
				return ColorUIResource(Color(color.red, color.green, color.blue, alpha))
			}
			catch (e : Exception)
			{
			}

			return null
		}

		return ColorUtil.fromHex(value, null)
	}

	private fun parseInsets(value : String) : Insets
	{
		val numbers = StringUtil.split(value, ",")

		return JBInsets(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]), Integer.parseInt(numbers[3])).asUIResource()
	}

	private fun parseSize(value : String) : Dimension
	{
		val numbers = StringUtil.split(value, ",")

		return JBDimension(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])).asUIResource()
	}

	fun parseValue (key : String, @NotNull value : String) : Any?
	{
		if (value === "null")
			return null

		if (value === "system")
			return System

		if (key.endsWith("Insets"))
		{
			return parseInsets(value)
		}
		else if (key.endsWith("Border") || key.endsWith("border"))
		{
			try
			{
				if (StringUtil.split(value, ",").size == 4)
				{
					return BorderUIResource.EmptyBorderUIResource(parseInsets(value))
				}
				else
				{
					return Class.forName(value).newInstance()
				}
			}
			catch (e : Exception)
			{
				e.printStackTrace()
			}
		}
		else if (key.endsWith("Size"))
		{
			return parseSize(value)
		}
		else
		{
			val color = parseColor(value)
			val invVal = getInteger(value)
			val boolVal = if (value === "true") java.lang.Boolean.TRUE else if (value === "false") java.lang.Boolean.FALSE else null

			var icon = if (value.startsWith("AllIcons.")) IconLoader.getIcon(value) else null
			if (icon === null)
				icon = IconLoader.findIcon(value, BasToolsLaf::class.java, true)

			if (color !== null)
				return color

			if (invVal !== null)
				return invVal

			if (icon !== null)
				return IconUIResource(icon)

			if (boolVal !== null)
				return boolVal
		}

		return value
	}

}
