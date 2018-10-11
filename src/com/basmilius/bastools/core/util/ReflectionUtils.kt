/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import com.intellij.util.ReflectionUtil
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

/**
 * Object ReflectionUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.4.0
 */
object ReflectionUtils
{

	/**
	 * Gets a field using reflection.
	 *
	 * @param instanceClass KClass<*>
	 * @param instance Any
	 * @param fieldClass KClass<*>
	 * @param fieldName String
	 *
	 * @return T
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: Any> getField(instanceClass: KClass<*>, instance: Any, fieldClass: KClass<T>, fieldName: String): T
	{
		return ReflectionUtil.getField(instanceClass.java, instance, fieldClass.java, fieldName)
	}

	/**
	 * Sets a field using reflection.
	 *
	 * @param clazz KClass<*>
	 * @param instance Any
	 * @param fieldType KClass<T>?
	 * @param fieldName String
	 * @param value T
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: Any> setField(clazz: KClass<*>, instance: Any, fieldType: KClass<T>?, fieldName: String, value: T)
	{
		ReflectionUtil.setField(clazz.java, instance, fieldType?.java, fieldName, value)
	}

	/**
	 * Sets a final static.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	fun setFinalStatic(cls: KClass<*>, fieldName: String, newValue: Any)
	{
		setFinalStatic(cls.java, fieldName, newValue)
	}

	/**
	 * Sets a final static.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	private fun setFinalStatic(cls: Class<*>, fieldName: String, newValue: Any)
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
	private fun setFinalStatic(field: Field, newValue: Any)
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
