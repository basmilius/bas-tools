package com.basmilius.ps.bastools.component.presenter.shortcuts

import com.intellij.openapi.keymap.KeymapManager

/**
 * Class KeymapDescription
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.component.presenter.shortcuts
 */
class KeymapDescription(val name : String = "", val displayText : String = "")
{

	/**
	 * Gets the keymap kind.
	 *
	 * @return KeymapKind
	 *
	 * @author Bas Milius
	 */
	fun getKind() = if (name.contains("Mac OS")) KeymapKind.MAC else KeymapKind.WIN

	/**
	 * Gets the keymap.
	 *
	 * @return Keymap
	 *
	 * @author Bas Milius
	 */
	fun getKeymap() = KeymapManager.getInstance().getKeymap(name)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun equals(other : Any?) : Boolean
	{
		return other is KeymapDescription && other.name == this.name && other.displayText == this.displayText
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun hashCode() : Int
	{
		return this.name.hashCode() + 31 * this.displayText.hashCode()
	}

}
