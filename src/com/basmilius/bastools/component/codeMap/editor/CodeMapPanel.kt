/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.editor

import com.basmilius.bastools.component.codeMap.CMDisabled
import com.basmilius.bastools.component.codeMap.CMPixelsPerLine
import com.basmilius.bastools.component.codeMap.CMWidth
import com.basmilius.bastools.component.codeMap.concurrent.NastyLock
import com.basmilius.bastools.component.codeMap.renderer.Folds
import com.basmilius.bastools.component.codeMap.renderer.MiniCode
import com.basmilius.bastools.component.codeMap.renderer.TaskQueueRunner
import com.intellij.openapi.editor.colors.ColorKey
import com.intellij.openapi.editor.event.*
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.reference.SoftReference
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.UIUtil
import java.awt.AlphaComposite
import java.awt.BorderLayout
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import java.awt.image.BufferedImage
import javax.swing.JPanel
import javax.swing.SwingUtilities

/**
 * Class CodeMapPanel
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.textEditor
 * @since 1.4.0
 */
class CodeMapPanel(private val project: Project, private val textEditor: TextEditor, private val container: JPanel, private val runner: TaskQueueRunner): JPanel(), VisibleAreaListener
{

	private var mapRef = SoftReference<MiniCode>(null)
	private var lastFoldCount = -1
	private var buf: BufferedImage? = null
	private val renderLock = NastyLock()
	private val scrollstate = ScrollState()
	private val scrollbar = Scrollbar(this.textEditor, scrollstate)

	private val componentListener: ComponentListener
	private val documentListener: DocumentListener
	private val selectionListener: SelectionListener = SelectionListener { repaint() }

	private val isDisabled: Boolean
		get() = CMDisabled

	init
	{
		componentListener = object: ComponentAdapter()
		{
			override fun componentResized(componentEvent: ComponentEvent?)
			{
				updateSize()
				scrollstate.setVisibleHeight(height)
				this@CodeMapPanel.revalidate()
				this@CodeMapPanel.repaint()
			}
		}
		container.addComponentListener(componentListener)

		documentListener = object: DocumentListener
		{

			override fun documentChanged(documentEvent: DocumentEvent?)
			{
				updateImage()
			}

		}
		this.textEditor.editor.document.addDocumentListener(documentListener)

		this.textEditor.editor.scrollingModel.addVisibleAreaListener(this)
		this.textEditor.editor.selectionModel.addSelectionListener(selectionListener)

		updateSize()
		updateImage()

		isOpaque = false
		layout = BorderLayout()
		add(scrollbar)
	}

	private fun updateSize()
	{
		if (isDisabled)
		{
			this.preferredSize = JBDimension(0, 0)
		}
		else
		{
			val size = JBDimension(CMWidth, 0)
			this.preferredSize = size
		}
	}

	private fun getOrCreateMap(): MiniCode?
	{
		var map = mapRef.get()

		if (map == null)
		{
			map = MiniCode()
			this.mapRef = SoftReference(map)
		}

		return map
	}

	private fun updateImage()
	{
		if (this.isDisabled)
			return

		if (this.project.isDisposed)
			return

		val file = PsiDocumentManager.getInstance(project).getPsiFile(this.textEditor.editor.document) ?: return
		val map = getOrCreateMap() ?: return

		if (!renderLock.acquire())
			return

		val hl = SyntaxHighlighterFactory.getSyntaxHighlighter(file.language, project, file.virtualFile)
		val text = this.textEditor.editor.document.text
		val folds = Folds(this.textEditor.editor.foldingModel.allFoldRegions)

		runner.run {
			map.update(text, this.textEditor.editor.colorsScheme, hl, folds)
			this.scrollstate.setDocumentSize(CMWidth, map.height)

			this.renderLock.release()

			if (this.renderLock.nasty)
			{
				this.updateImageSoon()
				this.renderLock.clean()
			}

			this.repaint()
		}
	}

	private fun updateImageSoon() = SwingUtilities.invokeLater { updateImage() }

	private fun paintLast(gfx: Graphics?)
	{
		val g = gfx as Graphics2D

		if (this.buf != null)
			g.drawImage(this.buf, 0, 0, this.buf!!.width, this.buf!!.height, 0, 0, this.buf!!.width, this.buf!!.height, null)

		this.paintSelections(g)
		this.scrollbar.paint(gfx)
	}

	override fun paint(gfx: Graphics?)
	{
		if (this.renderLock.locked)
		{
			this.paintLast(gfx)
			return
		}

		val minimap = this.mapRef.get()
		if (minimap == null)
		{
			this.updateImageSoon()
			this.paintLast(gfx)
			return
		}

		if (this.buf == null || this.buf?.width!! < this.width || this.buf?.height!! < this.height)
			this.buf = UIUtil.createImage(this.width, this.height, BufferedImage.TYPE_4BYTE_ABGR)

		val g = this.buf!!.createGraphics()

		g.composite = AlphaComposite.getInstance(AlphaComposite.CLEAR)
		g.fillRect(0, 0, this.width, this.height)
		g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER)

		if (this.textEditor.editor.document.textLength != 0)
			g.drawImage(minimap.image, 0, 0, this.scrollstate.documentWidth, this.scrollstate.drawHeight, 0, this.scrollstate.visibleStart, this.scrollstate.documentWidth, this.scrollstate.visibleEnd, null)

		this.paintSelections(gfx as Graphics2D)
		gfx.drawImage(buf, 0, 0, null)
		this.scrollbar.paint(gfx)
	}

	private fun paintSelection(g: Graphics2D, startByte: Int, endByte: Int)
	{
		val start = this.textEditor.editor.offsetToVisualPosition(startByte)
		val end = this.textEditor.editor.offsetToVisualPosition(endByte)

		val sX = start.column
		val sY = (start.line + 1) * CMPixelsPerLine - this.scrollstate.visibleStart
		val eX = end.column
		val eY = (end.line + 1) * CMPixelsPerLine - this.scrollstate.visibleStart

		g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f)
		g.color = this.textEditor.editor.colorsScheme.getColor(ColorKey.createColorKey("SELECTION_BACKGROUND", JBColor.BLUE))

		if (start.line == end.line)
		{
			g.fillRect(sX, sY, eX - sX, CMPixelsPerLine)
		}
		else
		{
			g.fillRect(sX, sY, this.width - sX, CMPixelsPerLine)
			g.fillRect(0, eY, eX, CMPixelsPerLine)

			if (eY + CMPixelsPerLine != sY)
				g.fillRect(0, sY + CMPixelsPerLine, this.width, eY - sY - CMPixelsPerLine)
		}
	}

	private fun paintSelections(g: Graphics2D)
	{
		this.paintSelection(g, this.textEditor.editor.selectionModel.selectionStart, this.textEditor.editor.selectionModel.selectionEnd)

		for ((index, start) in this.textEditor.editor.selectionModel.blockSelectionStarts.withIndex())
			this.paintSelection(g, start, this.textEditor.editor.selectionModel.blockSelectionEnds[index])
	}

	override fun visibleAreaChanged(visibleAreaEvent: VisibleAreaEvent)
	{
		var currentFoldCount = 0

		for (fold in this.textEditor.editor.foldingModel.allFoldRegions)
			if (!fold.isExpanded)
				currentFoldCount++

		val visibleArea = this.textEditor.editor.scrollingModel.visibleArea
		val start = this.scrollstate.documentHeight * visibleArea.y / this.textEditor.editor.contentComponent.height
		val end = this.scrollstate.documentHeight * visibleArea.height / this.textEditor.editor.contentComponent.height

		this.scrollstate.setViewportArea(start, end)
		this.scrollstate.setVisibleHeight(height)

		if (currentFoldCount != this.lastFoldCount)
			updateImage()

		this.lastFoldCount = currentFoldCount

		this.updateSize()
		this.repaint()
	}

	fun onClose()
	{
		container.removeComponentListener(componentListener)
		this.textEditor.editor.document.removeDocumentListener(documentListener)
		this.textEditor.editor.selectionModel.removeSelectionListener(selectionListener)
		remove(scrollbar)

		mapRef.clear()
	}

}
