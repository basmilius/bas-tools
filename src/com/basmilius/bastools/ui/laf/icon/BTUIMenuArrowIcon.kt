package com.basmilius.bastools.ui.laf.icon

import java.awt.Component
import java.awt.Graphics
import javax.swing.Icon
import javax.swing.JMenu
import javax.swing.JMenuItem
import javax.swing.plaf.UIResource

/**
 * Class BTUIMenuArrowIcon
 *
 * @constructor
 * @param icon Icon
 * @param selectedIcon Icon
 * @param disabledIcon Icon
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.icon
 * @since 1.4.0
 */
abstract class BTUIMenuArrowIcon(private val icon: Icon, private val selectedIcon: Icon, private val disabledIcon: Icon): Icon, UIResource
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paintIcon(component: Component, graphics: Graphics, x: Int, y: Int)
	{
		val menuItem = component as? JMenuItem ?: return
		val model = menuItem.model

		when
		{
			!model.isEnabled -> this.disabledIcon.paintIcon(component, graphics, x, y)
			model.isArmed || component is JMenu && model.isSelected -> this.selectedIcon.paintIcon(component, graphics, x, y)
			else -> this.icon.paintIcon(component, graphics, x, y)
		}
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getIconHeight() = this.icon.iconHeight

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getIconWidth() = this.icon.iconWidth

}
