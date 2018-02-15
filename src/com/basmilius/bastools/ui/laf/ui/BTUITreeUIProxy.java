package com.basmilius.bastools.ui.laf.ui;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

// NOTE: I'm only using Java for this file because Kotlin cannot override ComponentUI::createUI at this moment.

/**
 * Class BTUITreeUIProxy
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.ui
 * @since 1.4.0
 */
public class BTUITreeUIProxy
{

	/**
	 * Returns the Kotlin version of BTUITreeUI :D.
	 *
	 * @param component JComponent
	 *
	 * @return BTUITreeUI
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	public static ComponentUI createUI (JComponent component)
	{
		return new BTUITreeUI();
	}

}
