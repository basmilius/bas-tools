package com.basmilius.bastools.ui.laf.border

import com.intellij.ide.ui.laf.darcula.ui.DarculaMenuItemBorder
import com.intellij.util.ui.JBUI
import java.awt.Component
import java.awt.Insets

/**
 * Class BTUIMenuItemBorder
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.border
 * @since 1.4.0
 */
class BTUIMenuItemBorder: DarculaMenuItemBorder()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getBorderInsets(c: Component): Insets
	{
		return JBUI.insets(4, 3)
	}

}
