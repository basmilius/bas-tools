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

	/**
	 * Gets or Sets the document width.
	 *
	 * @property documentWidth
	 * @return Boolean
	 */
	var documentWidth: Int = 0
		private set

	/**
	 * Gets or Sets the document height.
	 *
	 * @property documentHeight
	 * @return Boolean
	 */
	var documentHeight: Int = 0
		private set

	/**
	 * Gets or Sets the visible start.
	 *
	 * @property visibleStart
	 * @return Boolean
	 */
	var visibleStart: Int = 0
		private set

	/**
	 * Gets or Sets the visible end.
	 *
	 * @property visibleEnd
	 * @return Boolean
	 */
	var visibleEnd: Int = 0
		private set

	/**
	 * Gets or Sets the visible height.
	 *
	 * @property visibleHeight
	 * @return Boolean
	 */
	var visibleHeight: Int = 0
		private set

	/**
	 * Gets or Sets the draw height.
	 *
	 * @property drawHeight
	 * @return Boolean
	 */
	var drawHeight: Int = 0
		private set

	/**
	 * Gets or Sets the viewport start.
	 *
	 * @property viewportStart
	 * @return Boolean
	 */
	var viewportStart: Int = 0
		private set

	/**
	 * Gets or Sets the viewport height.
	 *
	 * @property viewportHeight
	 * @return Boolean
	 */
	var viewportHeight: Int = 0
		private set

	/**
	 * Sets the document height.
	 *
	 * @param width Int
	 * @param height Int
	 *
	 * @return ScrollState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun setDocumentSize(width: Int, height: Int): ScrollState
	{
		this.documentWidth = width
		this.documentHeight = height
		this.recomputeVisible()

		return this
	}

	/**
	 * Sets the visible height.
	 *
	 * @param height Int
	 *
	 * @return ScrollState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun setVisibleHeight(height: Int): ScrollState
	{
		this.visibleHeight = height
		this.recomputeVisible()

		return this
	}

	/**
	 * Sets the viewport area.
	 *
	 * @param start Int
	 * @param height Int
	 *
	 * @return ScrollState
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun setViewportArea(start: Int, height: Int): ScrollState
	{
		this.viewportStart = start
		this.viewportHeight = height
		this.recomputeVisible()

		return this
	}

	/**
	 * Recomputes the visible part.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun recomputeVisible()
	{
		this.visibleStart = ((this.viewportStart / (this.documentHeight - this.viewportHeight + 1).toFloat()) * (this.documentHeight - this.visibleHeight + 1)).toInt()

		if (this.visibleStart < 0)
			this.visibleStart = 0

		this.drawHeight = Math.min(this.visibleHeight, this.documentHeight)
		this.visibleEnd = this.visibleStart + this.drawHeight
	}

}
