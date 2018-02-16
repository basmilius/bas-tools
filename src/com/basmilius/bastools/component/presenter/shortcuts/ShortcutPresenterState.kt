/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.presenter.shortcuts

/**
 * Class ShortcutPresenterState
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 * @since 1.1.0
 */
class ShortcutPresenterState
{

	var alternativeKeymap = getDefaultAlternativeKeymap()
	var fontSize = 15
	var mainKeymap = getDefaultMainKeymap()
	var showActionDescription = true

}
