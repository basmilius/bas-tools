/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.openapi.project.Project

/**
 * Class ActionData
 *
 * @constructor
 * @param actionId String
 * @param project Project?
 * @param actionText String?
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 * @since 1.1.0
 */
class ActionData(val actionId: String, val project: Project?, val actionText: String?)
