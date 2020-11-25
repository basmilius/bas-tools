package dev.bas.feature.codeShot

import com.intellij.ide.ui.UISettings
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorColors
import com.intellij.ui.ScreenUtil
import com.intellij.util.ui.ImageUtil
import com.intellij.util.ui.JBUI
import java.awt.Color
import java.awt.image.BufferedImage
import javax.swing.BorderFactory
import javax.swing.SwingUtilities
import javax.swing.border.CompoundBorder

fun createBufferedImage(fragment: CodeFragment): BufferedImage {
    val size = fragment.preferredSize
    fragment.size = size
    fragment.doLayout()

    val image = ImageUtil.createImage(size.width, size.height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.graphics

    UISettings.setupAntialiasing(graphics)
    fragment.printAll(graphics)
    graphics.dispose()

    return image
}

fun createCodeFragmentBorder(editor: Editor): CompoundBorder {
    val color = getBackgroundColor(editor)
    val outsideBorder = JBUI.Borders.customLine(color, 6, 12, 6, 12)
    val insideBorder = JBUI.Borders.empty(1)

    return BorderFactory.createCompoundBorder(outsideBorder, insideBorder)
}

fun getBackgroundColor(editor: Editor, useCaretRowBackground: Boolean = false): Color {
    val colorsScheme = editor.colorsScheme
    var color = colorsScheme.getColor(EditorColors.CARET_ROW_COLOR)

    if (!useCaretRowBackground || color == null) {
        color = colorsScheme.defaultBackground
    }

    return color
}

fun getWidthLimit(editor: Editor): Int {
    val component = editor.component
    val screenWidth = ScreenUtil.getScreenRectangle(component).width

    if (screenWidth > 0) {
        return screenWidth
    }

    val window = SwingUtilities.getWindowAncestor(component)

    return window?.width ?: Int.MAX_VALUE
}
