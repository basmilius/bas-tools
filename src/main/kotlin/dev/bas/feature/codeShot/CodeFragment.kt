package dev.bas.feature.codeShot

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.ex.util.EditorUIUtil
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.util.DocumentUtil
import com.intellij.util.ui.StartupUiUtil
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JComponent
import javax.swing.JPanel
import kotlin.math.max
import kotlin.math.min

class CodeFragment(private val component: Component?, private val editor: EditorEx, private val startLine: Int, private val endLine: Int, private val showFolding: Boolean = false, private val showGutter: Boolean = false) : JPanel() {

    init {
        this.editor.isPurePaintingMode = true

        try {
            this.doInit()
        } finally {
            this.editor.isPurePaintingMode = false
        }
    }

    private fun doInit() {
        val newRendering = this.editor is EditorImpl
        val savedScrollOffset = if (newRendering) 0 else this.editor.scrollingModel.horizontalScrollOffset

        val foldingModel = this.editor.foldingModel
        val isFoldingEnabled = foldingModel.isFoldingEnabled

        if (!showFolding) {
            foldingModel.isFoldingEnabled = false
        }

        val textImageHeight: Int
        val textImageWidth: Int
        val markersImageWidth: Int
        val textImage: BufferedImage
        val markersImage: BufferedImage?
        val rowHeader: JComponent?

        try {
            val document = this.editor.document
            val endOffset = if (endLine < document.lineCount) document.getLineEndOffset(max(0, endLine - 1)) else document.textLength
            val widthAdjustment = if (newRendering) EditorUtil.getSpaceWidth(Font.PLAIN, editor) else 0
            var offsetColumn = Int.MAX_VALUE

            for (line in startLine until endLine) {
                if (DocumentUtil.isLineEmpty(document, line)) {
                    continue
                }

                val offset = DocumentUtil.getFirstNonSpaceCharOffset(document, line) - document.getLineStartOffset(line)

                if (offset < offsetColumn) {
                    offsetColumn = offset
                }
            }

            val offsetStart = offsetColumn * EditorUtil.getSpaceWidth(Font.PLAIN, editor)

            textImageWidth = min(
                this.editor.getMaxWidthInRange(document.getLineStartOffset(startLine), endOffset) + widthAdjustment - offsetStart,
                getWidthLimit(editor)
            )

            val p1 = this.editor.logicalPositionToXY(LogicalPosition(startLine, offsetColumn))
            val p2 = this.editor.logicalPositionToXY(LogicalPosition(max(endLine, startLine - 1), offsetColumn))

            textImageHeight = if (p2.y - p1.y == 0) this.editor.lineHeight else p2.y - p1.y

            if (savedScrollOffset > 0) {
                this.editor.scrollingModel.scrollHorizontally(0)
            }

            textImage = UIUtil.createImage(this.component ?: this.editor.contentComponent, textImageWidth, textImageHeight, BufferedImage.TYPE_INT_RGB)

            val textGraphics = textImage.graphics
            EditorUIUtil.setupAntialiasing(textGraphics)

            if (showGutter) {
                rowHeader = this.editor.gutterComponentEx
                markersImageWidth = max(1, rowHeader.width)
                markersImage = UIUtil.createImage(this.editor.component, markersImageWidth, textImageHeight, BufferedImage.TYPE_INT_RGB)

                val markerGraphics = markersImage.graphics
                EditorUIUtil.setupAntialiasing(markerGraphics)

                markerGraphics.translate(0, -p1.y)
                markerGraphics.setClip(0, p1.y, rowHeader.width, textImageWidth)
                markerGraphics.color = getBackgroundColor(this.editor)
                markerGraphics.fillRect(0, p1.y, rowHeader.width, textImageWidth)

                rowHeader.paint(markerGraphics)
            } else {
                markersImageWidth = 0
                rowHeader = null
                markersImage = null
            }

            textGraphics.translate(-offsetStart, -p1.y)
            textGraphics.setClip(0, p1.y, textImageWidth + offsetStart, textImageHeight)

            val wasVisible = this.editor.setCaretVisible(false)

            this.editor.contentComponent.paint(textGraphics)

            if (wasVisible) {
                this.editor.setCaretVisible(true)
            }
        } finally {
            if (!showFolding) {
                foldingModel.isFoldingEnabled = isFoldingEnabled
            }
        }

        if (savedScrollOffset > 0) {
            this.editor.scrollingModel.scrollHorizontally(savedScrollOffset)
        }

        val component = object : JComponent() {

            override fun getPreferredSize() = Dimension(textImageWidth + markersImageWidth, textImageHeight)

            override fun paintComponent(g: Graphics) {
                if (markersImage != null) {
                    StartupUiUtil.drawImage(g, markersImage, 0, 0, null)
                    StartupUiUtil.drawImage(g, textImage, rowHeader?.width ?: 0, 0, null)
                } else {
                    StartupUiUtil.drawImage(g, textImage, 0, 0, null)
                }
            }

        }

        this.layout = BorderLayout()
        this.add(component)
        this.border = createCodeFragmentBorder(this.editor)
    }

    companion object {

        fun createCodeFragmentComponent(editor: Editor, startLine: Int, endLine: Int, showFolding: Boolean = false, showGutter: Boolean = false, useCaretRowBackground: Boolean = false): CodeFragment {
            return createCodeFragmentComponent(null, editor, startLine, endLine, showFolding, showGutter, useCaretRowBackground)
        }

        private fun createCodeFragmentComponent(component: Component?, editor: Editor, startLine: Int, endLine: Int, showFolding: Boolean = false, showGutter: Boolean = false, useCaretRowBackground: Boolean = false): CodeFragment {
            val editorEx = editor as EditorEx
            val old = editorEx.backgroundColor
            val backColor = getBackgroundColor(editor, useCaretRowBackground)

            editorEx.backgroundColor = backColor

            val fragment = CodeFragment(component, editorEx, startLine, endLine, showFolding, showGutter)
            fragment.background = backColor

            editorEx.backgroundColor = old

            return fragment
        }

    }

}
