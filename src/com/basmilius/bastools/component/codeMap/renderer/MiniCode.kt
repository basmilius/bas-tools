/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.renderer

import com.basmilius.bastools.component.codeMap.CMPixelsPerLine
import com.basmilius.bastools.component.codeMap.CMWidth
import com.basmilius.bastools.core.util.MathUtil.clamp
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.tree.IElementType
import com.intellij.util.ui.UIUtil
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * Class MiniCode
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.renderer
 * @since 1.4.0
 */
class MiniCode
{

	/**
	 * Companion Object MiniCode
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.component.codeMap.renderer
	 * @since 1.4.0
	 */
	companion object
	{

		private val Clear = AlphaComposite.getInstance(AlphaComposite.CLEAR)

		private val NoLines = LineInfo(1, 0, 0)

		private val UnpackedColor = IntArray(4)

	}

	var height: Int = 0
	var image: BufferedImage? = null

	private var lineEndings: ArrayList<Int>? = null

	/**
	 * Gets the color for a {@see element}.
	 *
	 * @param element IElementType
	 * @param hl SyntaxHighlighter
	 * @param colorScheme EditorColorsScheme
	 *
	 * @return Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun getColorForElementType(element: IElementType, hl: SyntaxHighlighter, colorScheme: EditorColorsScheme): Int
	{
		var color = colorScheme.defaultForeground.rgb
		var tmp: Color?
		val attributes = hl.getTokenHighlights(element)

		for (attribute in attributes)
		{
			val attr = colorScheme.getAttributes(attribute)

			if (attr != null)
			{
				tmp = attr.foregroundColor

				if (tmp != null)
					color = tmp.rgb
			}
		}

		return color
	}

	/**
	 * Gets a line at offset.
	 *
	 * @param offset Int
	 *
	 * @return LineInfo
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun getLine(offset: Int): LineInfo
	{
		if (this.lineEndings == null)
			return NoLines

		val lineEndings = this.lineEndings!!

		if (lineEndings.size == 0)
			return NoLines

		val lines = lineEndings[lineEndings.size - 1]

		if (lineEndings.size == 0)
			return NoLines

		if (lineEndings.size == 1)
			return NoLines

		if (lineEndings.size == 2)
			return LineInfo(1, lineEndings[0] + 1, lineEndings[1])

		var indexMin = 0
		var indexMax = lineEndings.size - 1
		var indexMid: Int
		var value: Int

		val clampedOffset = clamp(offset, 0, lines)

		while (true)
		{
			indexMid = Math.floor(((indexMin + indexMax) / 2.0f).toDouble()).toInt()
			value = lineEndings[indexMid]

			when
			{
				value < clampedOffset ->
				{
					if (clampedOffset < lineEndings[indexMid + 1])
						return LineInfo(indexMid + 1, value + 1, lineEndings[indexMid + 1])

					indexMin = indexMid + 1
				}

				clampedOffset < value ->
				{
					if (lineEndings[indexMid - 1] < clampedOffset)
						return LineInfo(indexMid, lineEndings[indexMid - 1] + 1, value)

					indexMax = indexMid - 1
				}

				else -> return LineInfo(indexMid, lineEndings[indexMid - 1] + 1, clampedOffset)
			}
		}
	}

	/**
	 * Renders a clean code sheet.
	 *
	 * @param x Int
	 * @param y Int
	 * @param char Int
	 * @param color Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun renderClean(x: Int, y: Int, char: Int, color: Int)
	{
		val weight = when (char)
		{
			in 0..32 -> 0.0f
			in 33..126 -> 0.8f
			else -> 0.4f
		}

		if (weight == 0.0f)
			return

		when (CMPixelsPerLine)
		{
			1 ->
				this.setPixel(x, y + 1, color, weight * 0.6f)

			2 ->
			{
				this.setPixel(x, y, color, weight * 0.3f)
				this.setPixel(x, y + 1, color, weight * 0.6f)
			}

			3 ->
			{
				this.setPixel(x, y, color, weight * 0.1f)
				this.setPixel(x, y + 1, color, weight * 0.6f)
				this.setPixel(x, y + 2, color, weight * 0.6f)
			}

			4 ->
			{
				this.setPixel(x, y + 1, color, weight * 0.6f)
				this.setPixel(x, y + 2, color, weight * 0.6f)
				this.setPixel(x, y + 3, color, weight * 0.6f)
			}
		}
	}

	/**
	 * Sets a pixel by X and Y.
	 *
	 * @param x Int
	 * @param y Int
	 * @param color Int
	 * @param alpha Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun setPixel(x: Int, y: Int, color: Int, alpha: Float)
	{
		var a = alpha

		if (a > 1)
			a = color.toFloat()

		if (a < 0)
			a = 0f

		UnpackedColor[3] = (a * 192).toInt()
		UnpackedColor[0] = (color and 16711680) shr 16
		UnpackedColor[1] = (color and 65280) shr 8
		UnpackedColor[2] = (color and 255)

		this.image!!.raster.setPixel(x, y, UnpackedColor)
	}

	/**
	 * Updates the MiniCode map.
	 *
	 * @param text String
	 * @param colorScheme EditorColorsScheme
	 * @param highlighter SyntaxHighlighter
	 * @param folds Folds
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun update(text: String, colorScheme: EditorColorsScheme, highlighter: SyntaxHighlighter, folds: Folds)
	{
		updateDimensions(text, folds)

		var color: Int
		var ch: Char
		var startLine: LineInfo
		val lexer = highlighter.highlightingLexer
		var tokenType: IElementType?

		val g = this.image!!.graphics as Graphics2D
		g.composite = Clear
		g.fillRect(0, 0, this.image!!.width, this.image!!.height)

		lexer.start(text)
		tokenType = lexer.tokenType

		var x: Int
		var y: Int
		while (tokenType != null)
		{
			val start = lexer.tokenStart

			startLine = getLine(start)
			y = startLine.number * CMPixelsPerLine
			color = getColorForElementType(tokenType, highlighter, colorScheme)
			x = 0

			for (i in startLine.begin until start)
			{
				if (folds.isFolded(i))
					continue

				x += if (text[i] == '\t') 4 else 1

				if (x > CMWidth)
					break
			}

			for (i in start until lexer.tokenEnd)
			{
				if (folds.isFolded(i))
					continue

				if (i >= text.length)
					return

				ch = text[i]

				when (ch)
				{
					'\n' ->
					{
						x = 0
						y += CMPixelsPerLine
					}

					'\t' -> x += 4
					else -> x += 1
				}

				if (0 <= x && x < this.image!!.width && 0 <= y && y + CMPixelsPerLine < this.image!!.height)
					this.renderClean(x, y, text[i].toInt(), color)
			}

			lexer.advance()
			tokenType = lexer.tokenType
		}
	}

	/**
	 * Updates the code dimensions.
	 *
	 * @param text String
	 * @param folds Folds
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun updateDimensions(text: String, folds: Folds)
	{
		var lineLength = 0
		var longestLine = 1
		var lines = 1
		var last = ' '
		var ch: Char

		val lineEndings = ArrayList<Int>()
		lineEndings.add(-1)

		var i = 0
		val len = text.length
		while (i < len)
		{
			if (folds.isFolded(i))
			{
				i++
				continue
			}

			ch = text[i]

			if (ch == '\n' || (ch == '\r' && last != '\n'))
			{
				lineEndings.add(i)
				lines++

				if (lineLength > longestLine)
					longestLine = lineLength

				lineLength = 0
			}
			else if (ch == '\t')
			{
				lineLength += 4
			}
			else
			{
				lineLength++
			}

			last = ch
			i++
		}

		if (lineEndings[lineEndings.size - 1] != text.length - 1)
			lineEndings.add(text.length - 1)

		this.lineEndings = lineEndings
		this.height = (lines + 1) * CMPixelsPerLine

		if (this.image != null && this.image!!.height >= this.height && this.image!!.width >= CMWidth)
			return

		if (this.image != null)
			this.image?.flush()

		this.image = UIUtil.createImage(CMWidth, this.height + 100 * CMPixelsPerLine, BufferedImage.TYPE_4BYTE_ABGR)
	}

}
