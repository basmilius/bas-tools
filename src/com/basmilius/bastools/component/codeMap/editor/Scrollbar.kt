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

class Scrollbar(val textEditor: TextEditor, private val scrollstate: ScrollState): JPanel(), MouseListener, MouseWheelListener, MouseMotionListener
{

	private var scrollStart: Int = 0
	private var mouseStart: Int = 0
	private val defaultCursor = Cursor(Cursor.DEFAULT_CURSOR)
	private var dragging = false
	private var visibleRectColor: JBColor = BasToolsTabsPainterPatcherComponent.BackgroundColor

	override fun mouseEntered(e: MouseEvent?)
	{
	}

	override fun mouseExited(e: MouseEvent?)
	{
	}

	init
	{
		this.addMouseWheelListener(this)
		this.addMouseListener(this)
		this.addMouseMotionListener(this)
	}

	override fun mouseDragged(e: MouseEvent)
	{
		if (!this.dragging)
			return

		this.textEditor.editor.scrollingModel.disableAnimation()
		this.scrollTo(e.y)
		this.textEditor.editor.scrollingModel.enableAnimation()
	}

	override fun mousePressed(e: MouseEvent)
	{
		this.dragging = true

		if (!this.dragging)
			return

		this.scrollTo(e.y)

		this.scrollStart = this.textEditor.editor.scrollingModel.verticalScrollOffset
		this.mouseStart = e.y
	}

	private fun scrollTo(y: Int)
	{
		val percentage = (y + scrollstate.visibleStart) / scrollstate.documentHeight.toFloat()
		val offset = textEditor.component.size.height / 2
		textEditor.editor.scrollingModel.scrollVertically((percentage * textEditor.editor.contentComponent.size.height - offset).toInt())
	}

	override fun mouseReleased(e: MouseEvent)
	{
		this.dragging = false
	}

	override fun mouseClicked(e: MouseEvent)
	{
		this.scrollTo(e.y)
	}

	override fun mouseMoved(e: MouseEvent?)
	{
		this.cursor = this.defaultCursor
	}

	override fun mouseWheelMoved(e: MouseWheelEvent)
	{
		this.textEditor.editor.scrollingModel.scrollVertically(this.textEditor.editor.scrollingModel.verticalScrollOffset + (e.wheelRotation * this.textEditor.editor.lineHeight * 3))
	}

	override fun paint(gfx: Graphics?)
	{
		val g = gfx as Graphics2D

		g.color = this.visibleRectColor
		g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.20f)
		g.fillRect(0, this.scrollstate.viewportStart - this.scrollstate.visibleStart, this.width, this.scrollstate.viewportHeight)
	}
}
