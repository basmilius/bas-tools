package com.basmilius.bastools.core.util

import java.lang.reflect.Field
import java.lang.reflect.Modifier

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
