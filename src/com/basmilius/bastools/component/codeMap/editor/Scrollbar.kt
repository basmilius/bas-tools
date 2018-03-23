/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.editor

import com.basmilius.bastools.ui.tabs.BasToolsTabsPainterPatcherComponent
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.ui.JBColor
import java.awt.AlphaComposite
import java.awt.Cursor
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.*
import javax.swing.JPanel

/**
 * Class Scrollbar
 *
 * @constructor
 * @param textEditor TextEditor
 * @param scrollState ScrollState
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.editor
 * @since 1.4.0
 */
class Scrollbar(val textEditor: TextEditor, private val scrollState: ScrollState): JPanel(), MouseListener, MouseWheelListener, MouseMotionListener
{

	private var scrollStart: Int = 0
	private var mouseStart: Int = 0
	private val defaultCursor = Cursor(Cursor.DEFAULT_CURSOR)
	private var dragging = false
	private var visibleRectColor: JBColor = BasToolsTabsPainterPatcherComponent.BackgroundColor

	/**
	 * Scrollbar Initializer.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	init
	{
		this.addMouseWheelListener(this)
		this.addMouseListener(this)
		this.addMouseMotionListener(this)
	}

	/**
	 * Scrolls to {@see y}.
	 *
	 * @param y Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun scrollTo(y: Int)
	{
		val percentage = (y + scrollState.visibleStart) / scrollState.documentHeight.toFloat()
		val offset = textEditor.component.size.height / 2
		textEditor.editor.scrollingModel.scrollVertically((percentage * textEditor.editor.contentComponent.size.height - offset).toInt())
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mouseClicked(e: MouseEvent)
	{
		this.scrollTo(e.y)
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mouseDragged(e: MouseEvent)
	{
		if (!this.dragging)
			return

		this.textEditor.editor.scrollingModel.disableAnimation()
		this.scrollTo(e.y)
		this.textEditor.editor.scrollingModel.enableAnimation()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mouseEntered(e: MouseEvent?)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mouseExited(e: MouseEvent?)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mouseMoved(e: MouseEvent?)
	{
		this.cursor = this.defaultCursor
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mousePressed(e: MouseEvent)
	{
		this.dragging = true

		if (!this.dragging)
			return

		this.scrollTo(e.y)

		this.scrollStart = this.textEditor.editor.scrollingModel.verticalScrollOffset
		this.mouseStart = e.y
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mouseReleased(e: MouseEvent)
	{
		this.dragging = false
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun mouseWheelMoved(e: MouseWheelEvent)
	{
		this.textEditor.editor.scrollingModel.scrollVertically(this.textEditor.editor.scrollingModel.verticalScrollOffset + (e.wheelRotation * this.textEditor.editor.lineHeight * 3))
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun paint(gfx: Graphics?)
	{
		val g = gfx as Graphics2D

		g.color = this.visibleRectColor
		g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.20f)
		g.fillRect(0, this.scrollState.viewportStart - this.scrollState.visibleStart, this.width, this.scrollState.viewportHeight)
	}

}
