package com.basmilius.bastools.ui

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.picker.ColorListener
import com.intellij.ui.picker.ColorPipetteBase
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Area
import java.awt.image.BufferedImage
import javax.swing.JComponent
import javax.swing.Timer

/**
 * Class DefaultColorPipette
 *
 * @constructor
 * @param parent JComponent
 * @param colorListener COlorListener
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui
 * @since 1.0.0
 */
class DefaultColorPipette(parent: JComponent, colorListener: ColorListener): ColorPipetteBase(parent, colorListener)
{

	private val myCaptureRect = Rectangle(-4, -4, 8, 8)
	private val myZoomRect = Rectangle(0, 0, SIZE, SIZE)
	private val myPreviousLocation = Point()

	private var myGraphics: Graphics2D? = null
	private var myImage: BufferedImage? = null
	private var myPipetteImage: BufferedImage? = null
	private var myMaskImage: BufferedImage? = null
	private val myTimer: Timer

	/**
	 * DefaultColorPipette Constructor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	init
	{
		myTimer = UIUtil.createNamedTimer("DefaultColorPipette", 5) { _ -> updatePipette() }
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getPixelColor(location: Point): Color
	{
		return super.getPixelColor(Point(location.x - HOT_SPOT.x + SIZE / 2, location.y - HOT_SPOT.y + SIZE / 2))
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun show(): Dialog
	{
		val picker = super.show()
		myTimer.start()

		WindowManager.getInstance().setAlphaModeRatio(picker, if (SystemInfo.isMac) 0.95f else 0.99f)

		if (SystemInfo.isJavaVersionAtLeast("1.7"))
		{
			val area = Area(Rectangle(0, 0, DIALOG_SIZE, DIALOG_SIZE))
			area.subtract(Area(Rectangle(SIZE / 2 - 1, SIZE / 2 - 1, 3, 3)))
			picker.shape = area
		}
		return picker
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun isAvailable(): Boolean
	{
		if (myRobot != null)
		{
			myRobot.createScreenCapture(Rectangle(0, 0, 1, 1))
			return WindowManager.getInstance().isAlphaModeSupported
		}
		return false
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getOrCreatePickerDialog(): Dialog
	{
		var pickerDialog = pickerDialog
		if (pickerDialog == null)
		{
			pickerDialog = super.getOrCreatePickerDialog()
			pickerDialog.addMouseListener(object: MouseAdapter()
			{

				override fun mouseExited(event: MouseEvent?)
				{
					updatePipette()
				}
			})
			pickerDialog.addMouseMotionListener(object: MouseAdapter()
			{

				override fun mouseMoved(e: MouseEvent?)
				{
					updatePipette()
				}
			})
			pickerDialog.addFocusListener(object: FocusAdapter()
			{

				override fun focusLost(e: FocusEvent?)
				{
					if (e!!.isTemporary)
					{
						pickAndClose()
					}
					else
					{
						cancelPipette()
					}
				}
			})

			pickerDialog.setSize(DIALOG_SIZE, DIALOG_SIZE)
			myMaskImage = UIUtil.createImage(pickerDialog, SIZE, SIZE, BufferedImage.TYPE_INT_ARGB)
			val maskG = myMaskImage!!.createGraphics()
			maskG.color = Color.BLUE
			maskG.fillRect(0, 0, SIZE, SIZE)

			maskG.color = Color.RED
			maskG.composite = AlphaComposite.SrcOut
			maskG.fillRect(0, 0, SIZE, SIZE)
			maskG.dispose()

			myPipetteImage = UIUtil.createImage(pickerDialog, AllIcons.Ide.Pipette.iconWidth, AllIcons.Ide.Pipette.iconHeight, BufferedImage.TYPE_INT_ARGB)
			val graphics = myPipetteImage!!.createGraphics()

			AllIcons.Ide.Pipette.paintIcon(null, graphics, 0, 0)
			graphics.dispose()

			myImage = myParent.graphicsConfiguration.createCompatibleImage(SIZE, SIZE, Transparency.TRANSLUCENT)

			myGraphics = myImage!!.graphics as Graphics2D
			myGraphics!!.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR)
		}

		return pickerDialog
	}

	/**
	 * Updates the pipette.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun updatePipette()
	{
		val pickerDialog = pickerDialog
		if (pickerDialog != null && pickerDialog.isShowing)
		{
			val mouseLoc = updateLocation() ?: return
			val c = getPixelColor(mouseLoc)
			if (c != color || mouseLoc != myPreviousLocation)
			{
				color = c
				myPreviousLocation.location = mouseLoc
				myCaptureRect.setBounds(mouseLoc.x - HOT_SPOT.x + SIZE / 2 - 2, mouseLoc.y - HOT_SPOT.y + SIZE / 2 - 2, 5, 5)

				val capture = myRobot.createScreenCapture(myCaptureRect)

				// Clear the cursor graphics
				myGraphics!!.composite = AlphaComposite.Src
				myGraphics!!.color = UIUtil.TRANSPARENT_COLOR
				myGraphics!!.fillRect(0, 0, myImage!!.width, myImage!!.height)

				myGraphics!!.drawImage(capture, myZoomRect.x, myZoomRect.y, myZoomRect.width, myZoomRect.height, this)

				// cropping round image
				myGraphics!!.composite = AlphaComposite.getInstance(AlphaComposite.DST_OUT)
				myGraphics!!.drawImage(myMaskImage, myZoomRect.x, myZoomRect.y, myZoomRect.width, myZoomRect.height, this)

				// paint magnifier
				myGraphics!!.composite = AlphaComposite.SrcOver

				UIUtil.drawImage(myGraphics!!, myPipetteImage, SIZE - AllIcons.Ide.Pipette.iconWidth, 0, this)

				pickerDialog.cursor = myParent.toolkit.createCustomCursor(myImage, HOT_SPOT, "ColorPicker")
				notifyListener(c, 300)
			}
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun cancelPipette()
	{
		myTimer.stop()
		super.cancelPipette()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun dispose()
	{
		myTimer.stop()
		super.dispose()
		if (myGraphics != null)
		{
			myGraphics!!.dispose()
		}
		myImage = null
		myPipetteImage = null
		myMaskImage = null
	}

	/**
	 * Companion Object for DefaultColorPipette
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	companion object
	{

		private const val SIZE = 30
		private const val DIALOG_SIZE = SIZE - 4
		private val HOT_SPOT = Point(DIALOG_SIZE / 2, DIALOG_SIZE / 2)
	}

}
