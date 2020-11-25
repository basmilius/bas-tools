package dev.bas.feature.quickActions

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.picker.ColorListener
import com.intellij.ui.picker.ColorPipetteBase
import com.intellij.util.ui.TimerUtil
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Area
import java.awt.image.BufferedImage
import javax.swing.JComponent

class DefaultColorPipette(parent: JComponent, colorListener: ColorListener) : ColorPipetteBase(parent, colorListener) {

    private val myCaptureRect = Rectangle(-4, -4, 8, 8)
    private val myZoomRect = Rectangle(0, 0, SIZE, SIZE)
    private val myPreviousLocation = Point()

    private var myGraphics: Graphics2D? = null
    private var myImage: BufferedImage? = null
    private var myPipetteImage: BufferedImage? = null
    private var myMaskImage: BufferedImage? = null

    private val myTimer = TimerUtil.createNamedTimer("DefaultColorPipette", 5) {
        updatePipette()
    }

    override fun getPixelColor(location: Point): Color {
        return super.getPixelColor(Point(location.x - HOT_SPOT.x + SIZE / 2, location.y - HOT_SPOT.y + SIZE / 2))
    }

    override fun show(): Dialog {
        val picker = super.show()
        this.myTimer.start()

        WindowManager.getInstance().setAlphaModeRatio(picker, if (SystemInfo.isMac) 0.95f else 0.99f)

        val area = Area(Rectangle(0, 0, DIALOG_SIZE, DIALOG_SIZE))
        area.subtract(Area(Rectangle(SIZE / 2 - 1, SIZE / 2 - 1, 3, 3)))
        picker.shape = area

        return picker
    }

    override fun isAvailable(): Boolean {
        if (this.myRobot == null) {
            return false
        }

        this.myRobot.createScreenCapture(Rectangle(0, 0, 1, 1))

        return WindowManager.getInstance().isAlphaModeSupported
    }

    override fun getOrCreatePickerDialog(): Dialog {
        val existingDialog = this.pickerDialog

        if (existingDialog != null) {
            return existingDialog
        }

        val pickerDialog = super.getOrCreatePickerDialog()

        pickerDialog.addMouseMotionListener(object : MouseAdapter() {

            override fun mouseMoved(e: MouseEvent?) {
                this@DefaultColorPipette.updatePipette()
            }

        })

        pickerDialog.addMouseListener(object : MouseAdapter() {

            override fun mouseExited(e: MouseEvent?) {
                this@DefaultColorPipette.updatePipette()
            }

        })

        pickerDialog.addFocusListener(object : FocusAdapter() {

            override fun focusLost(e: FocusEvent?) = if (e != null && e.isTemporary) {
                this@DefaultColorPipette.pickAndClose()
            } else {
                this@DefaultColorPipette.cancelPipette()
            }

        })

        pickerDialog.setSize(DIALOG_SIZE, DIALOG_SIZE)
        val maskImage = UIUtil.createImage(pickerDialog, SIZE, SIZE, BufferedImage.TYPE_INT_ARGB)

        val maskGraphics = maskImage.createGraphics()
        maskGraphics.color = Color.BLUE
        maskGraphics.fillRect(0, 0, SIZE, SIZE)

        maskGraphics.color = Color.RED
        maskGraphics.composite = AlphaComposite.SrcOut
        maskGraphics.fillRect(0, 0, SIZE, SIZE)
        maskGraphics.dispose()

        val pipetteIcon = AllIcons.Ide.Pipette
        val pipetteImage = UIUtil.createImage(pickerDialog, pipetteIcon.iconWidth, pipetteIcon.iconHeight, BufferedImage.TYPE_INT_ARGB)
        val pipetteGraphics = pipetteImage.createGraphics()
        pipetteIcon.paintIcon(null, pipetteGraphics, 0, 0)
        pipetteGraphics.dispose()

        val image = this.myParent.graphicsConfiguration.createCompatibleImage(SIZE, SIZE, Transparency.TRANSLUCENT)

        this.myGraphics = image.graphics as Graphics2D
        this.myGraphics!!.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR)

        this.myImage = image
        this.myMaskImage = maskImage
        this.myPipetteImage = pipetteImage

        return pickerDialog
    }

    private fun updatePipette() {
        val pickerDialog = pickerDialog

        if (pickerDialog == null || !pickerDialog.isShowing) {
            return
        }

        val mouseLocation = this.updateLocation() ?: return
        val color = this.getPixelColor(mouseLocation)

        if (color == this.color && mouseLocation == this.myPreviousLocation) return

        this.color = color
        this.myPreviousLocation.location = mouseLocation
        this.myCaptureRect.setBounds(mouseLocation.x - HOT_SPOT.x + SIZE / 2 - 2, mouseLocation.y - HOT_SPOT.y + SIZE / 2 - 2, 5, 5)

        val capture = this.myRobot.createScreenCapture(this.myCaptureRect)
        val graphics = this.myGraphics
        val image = this.myImage
        val maskImage = this.myMaskImage
        val pipetteImage = this.myPipetteImage

        if (graphics != null && image != null && maskImage != null && pipetteImage != null) {
            // Clear the cursor graphics
            graphics.composite = AlphaComposite.Src
            graphics.color = UIUtil.TRANSPARENT_COLOR
            graphics.fillRect(0, 0, image.width, image.height)

            graphics.drawImage(capture, this.myZoomRect.x, this.myZoomRect.y, this.myZoomRect.width, this.myZoomRect.height, this)

            // Cropping round image
            graphics.composite = AlphaComposite.getInstance(AlphaComposite.DST_OUT)
            graphics.drawImage(maskImage, this.myZoomRect.x, this.myZoomRect.y, this.myZoomRect.width, this.myZoomRect.height, this)

            // Paint magnifier
            graphics.composite = AlphaComposite.SrcOver

            UIUtil.drawImage(graphics, pipetteImage, SIZE - AllIcons.Ide.Pipette.iconWidth, 0, this)

            pickerDialog.cursor = this.myParent.toolkit.createCustomCursor(image, HOT_SPOT, "ColorPicker")
            this.notifyListener(color, 300)
        }
    }

    override fun cancelPipette() {
        myTimer.stop()
        super.cancelPipette()
    }

    override fun dispose() {
        myTimer.stop()
        super.dispose()

        if (myGraphics != null) {
            myGraphics!!.dispose()
        }

        myImage = null
        myPipetteImage = null
        myMaskImage = null
    }

    companion object {

        private const val SIZE = 30
        private const val DIALOG_SIZE = SIZE - 4
        private val HOT_SPOT = Point(DIALOG_SIZE / 2, DIALOG_SIZE / 2)

    }

}
