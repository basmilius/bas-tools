/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

/**
 * Object StaticPatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.2.0
 */
object StaticPatcher
{

	/**
	 * Sets a field value.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	fun setFieldValue(obj: Any, fieldName: String, value: Any)
	{
		try
		{
			val field = obj.javaClass.getDeclaredField(fieldName)
			field.isAccessible = true
			field.set(obj, value)
		}
		catch (err: Exception)
		{
			err.printStackTrace()
		}
	}

	/**
	 * Sets a final static.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	fun setFinalStatic (cls: KClass<*>, fieldName: String, newValue: Any)
	{
		setFinalStatic(cls.java, fieldName, newValue)
	}

	/**
	 * Sets a final static.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	fun setFinalStatic(cls: Class<*>, fieldName: String, newValue: Any)
	{
		val fields = cls.declaredFields

		for (field in fields)
			if (field.name == fieldName)
			{
				this.setFinalStatic(field, newValue)
				return
			}
	}

	/**
	 * Sets a final static.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	@Throws(Exception::class)
	fun setFinalStatic(field: Field, newValue: Any)
	{
		field.isAccessible = true

		val modifiersField = Field::class.java.getDeclaredField("modifiers")
		modifiersField.isAccessible = true
		modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())

		field.set(null, newValue)

		modifiersField.setInt(field, field.modifiers or Modifier.FINAL)
		modifiersField.isAccessible = false

		field.isAccessible = false
	}

}
