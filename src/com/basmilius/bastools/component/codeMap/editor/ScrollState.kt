/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.editor

/**
 * Class ScrollState
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.textEditor
 * @since 1.4.0
 */
class ScrollState
{

	var documentWidth: Int = 0
		private set

	var documentHeight: Int = 0
		private set

	var visibleStart: Int = 0
		private set

	var visibleEnd: Int = 0
		private set

	var visibleHeight: Int = 0
		private set

	var drawHeight: Int = 0
		private set

	var viewportStart: Int = 0
		private set

	var viewportHeight: Int = 0
		private set

	fun setDocumentSize(width: Int, height: Int): ScrollState
	{
		this.documentWidth = width
		this.documentHeight = height
		this.recomputeVisible()

		return this
	}

	fun setVisibleHeight(height: Int): ScrollState
	{
		this.visibleHeight = height
		this.recomputeVisible()

		return this
	}

	fun setViewportArea(start: Int, height: Int): ScrollState
	{
		this.viewportStart = start
		this.viewportHeight = height
		this.recomputeVisible()

		return this
	}

	private fun recomputeVisible()
	{
		this.visibleStart = ((this.viewportStart / (this.documentHeight - this.viewportHeight + 1).toFloat()) * (this.documentHeight - this.visibleHeight + 1)).toInt()

		if (this.visibleStart < 0)
			this.visibleStart = 0

		this.drawHeight = Math.min(this.visibleHeight, this.documentHeight)
		this.visibleEnd = this.visibleStart + this.drawHeight
	}

}
