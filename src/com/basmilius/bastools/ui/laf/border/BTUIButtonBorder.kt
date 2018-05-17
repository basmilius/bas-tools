/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.border

import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonPainter
import com.intellij.util.ui.JBUI
import java.awt.Component

/**
 * Class BTUIButtonBorder
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.border
 * @since 1.4.0
 */
class BTUIButtonBorder: DarculaButtonPainter()
{

	override fun getBorderInsets(p0: Component?) = JBUI.insets(4, 6)

}
