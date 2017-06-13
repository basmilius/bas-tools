package com.basmilius.ps.bastools.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.picker.ColorListener;
import com.intellij.ui.picker.ColorPipetteBase;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class DefaultColorPipette extends ColorPipetteBase
{

	private static final int SIZE = 30;
	private static final int DIALOG_SIZE = SIZE - 4;
	private static final Point HOT_SPOT = new Point(DIALOG_SIZE / 2, DIALOG_SIZE / 2);

	private final Rectangle myCaptureRect = new Rectangle(-4, -4, 8, 8);
	private final Rectangle myZoomRect = new Rectangle(0, 0, SIZE, SIZE);
	private final Point myPreviousLocation = new Point();

	private Graphics2D myGraphics;
	private BufferedImage myImage;
	private BufferedImage myPipetteImage;
	private BufferedImage myMaskImage;
	private final Timer myTimer;

	public DefaultColorPipette (@NotNull JComponent parent, @NotNull ColorListener colorListener)
	{
		super(parent, colorListener);
		myTimer = UIUtil.createNamedTimer("DefaultColorPipette", 5, e -> updatePipette());
	}

	@Override
	protected Color getPixelColor (Point location)
	{
		return super.getPixelColor(new Point(location.x - HOT_SPOT.x + SIZE / 2, location.y - HOT_SPOT.y + SIZE / 2));
	}

	@Override
	public Dialog show ()
	{
		Dialog picker = super.show();
		myTimer.start();

		WindowManager.getInstance().setAlphaModeRatio(picker, SystemInfo.isMac ? 0.95f : 0.99f);

		if (SystemInfo.isJavaVersionAtLeast("1.7"))
		{
			Area area = new Area(new Rectangle(0, 0, DIALOG_SIZE, DIALOG_SIZE));
			area.subtract(new Area(new Rectangle(SIZE / 2 - 1, SIZE / 2 - 1, 3, 3)));
			picker.setShape(area);
		}
		return picker;
	}

	@Override
	public boolean isAvailable ()
	{
//		if (SystemInfo.isWayland)
//		{
//			return false;
//		}

		if (myRobot != null)
		{
			myRobot.createScreenCapture(new Rectangle(0, 0, 1, 1));
			return WindowManager.getInstance().isAlphaModeSupported();
		}
		return false;
	}

	@NotNull
	@SuppressWarnings("UseJBColor")
	protected Dialog getOrCreatePickerDialog ()
	{
		Dialog pickerDialog = getPickerDialog();
		if (pickerDialog == null)
		{
			pickerDialog = super.getOrCreatePickerDialog();
			pickerDialog.addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseExited (MouseEvent event)
				{
					updatePipette();
				}
			});
			pickerDialog.addMouseMotionListener(new MouseAdapter()
			{

				@Override
				public void mouseMoved (MouseEvent e)
				{
					updatePipette();
				}
			});
			pickerDialog.addFocusListener(new FocusAdapter()
			{

				@Override
				public void focusLost (FocusEvent e)
				{
					if (e.isTemporary())
					{
						pickAndClose();
					}
					else
					{
						cancelPipette();
					}
				}
			});

			pickerDialog.setSize(DIALOG_SIZE, DIALOG_SIZE);
			myMaskImage = UIUtil.createImage(pickerDialog, SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D maskG = myMaskImage.createGraphics();
			maskG.setColor(Color.BLUE);
			maskG.fillRect(0, 0, SIZE, SIZE);

			maskG.setColor(Color.RED);
			maskG.setComposite(AlphaComposite.SrcOut);
			maskG.fillRect(0, 0, SIZE, SIZE);
			maskG.dispose();

			myPipetteImage = UIUtil.createImage(pickerDialog, AllIcons.Ide.Pipette.getIconWidth(), AllIcons.Ide.Pipette.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = myPipetteImage.createGraphics();
			//noinspection ConstantConditions
			AllIcons.Ide.Pipette.paintIcon(null, graphics, 0, 0);
			graphics.dispose();

			myImage = myParent.getGraphicsConfiguration().createCompatibleImage(SIZE, SIZE, Transparency.TRANSLUCENT);

			myGraphics = (Graphics2D) myImage.getGraphics();
			myGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		}

		return pickerDialog;
	}

	private void updatePipette ()
	{
		Dialog pickerDialog = getPickerDialog();
		if (pickerDialog != null && pickerDialog.isShowing())
		{
			Point mouseLoc = updateLocation();
			if (mouseLoc == null) return;
			final Color c = getPixelColor(mouseLoc);
			if (!c.equals(getColor()) || !mouseLoc.equals(myPreviousLocation))
			{
				setColor(c);
				myPreviousLocation.setLocation(mouseLoc);
				myCaptureRect.setBounds(mouseLoc.x - HOT_SPOT.x + SIZE / 2 - 2, mouseLoc.y - HOT_SPOT.y + SIZE / 2 - 2, 5, 5);

				BufferedImage capture = myRobot.createScreenCapture(myCaptureRect);

				// Clear the cursor graphics
				myGraphics.setComposite(AlphaComposite.Src);
				myGraphics.setColor(UIUtil.TRANSPARENT_COLOR);
				myGraphics.fillRect(0, 0, myImage.getWidth(), myImage.getHeight());

				myGraphics.drawImage(capture, myZoomRect.x, myZoomRect.y, myZoomRect.width, myZoomRect.height, this);

				// cropping round image
				myGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT));
				myGraphics.drawImage(myMaskImage, myZoomRect.x, myZoomRect.y, myZoomRect.width, myZoomRect.height, this);

				// paint magnifier
				myGraphics.setComposite(AlphaComposite.SrcOver);

				UIUtil.drawImage(myGraphics, myPipetteImage, SIZE - AllIcons.Ide.Pipette.getIconWidth(), 0, this);

				pickerDialog.setCursor(myParent.getToolkit().createCustomCursor(myImage, HOT_SPOT, "ColorPicker"));
				notifyListener(c, 300);
			}
		}
	}

	@Override
	public void cancelPipette ()
	{
		myTimer.stop();
		super.cancelPipette();
	}

	@Override
	public void dispose ()
	{
		myTimer.stop();
		super.dispose();
		if (myGraphics != null)
		{
			myGraphics.dispose();
		}
		myImage = null;
		myPipetteImage = null;
		myMaskImage = null;
	}

}
