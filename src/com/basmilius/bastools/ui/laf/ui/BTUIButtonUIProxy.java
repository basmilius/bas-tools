/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.ui;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

// NOTE: I'm only using Java for this file because Kotlin cannot override ComponentUI::createUI at this moment.

/**
 * Class BTUIButtonUIProxy
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.ui
 * @since 1.4.0
 */
public class BTUIButtonUIProxy
{

	/**
	 * Returns the Kotlin version of BTUIButtonUI :D.
	 *
	 * @param component JComponent
	 *
	 * @return BTUIButtonUI
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	public static ComponentUI createUI (JComponent component)
	{
		return new BTUIButtonUI();
	}

}
