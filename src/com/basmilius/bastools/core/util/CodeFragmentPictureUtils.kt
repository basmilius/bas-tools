package com.basmilius.bastools.core.util

import com.intellij.codeInsight.hint.EditorFragmentComponent
import com.intellij.ide.ui.UISettings
import com.intellij.util.ui.UIUtil
import java.awt.Image
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.awt.image.BufferedImage

/**
 * Object CodeFragmentPictureUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.1.0
 */
object CodeFragmentPictureUtils
{

	/**
	 * Creates the buffered image from a Editor Fragment Component.
	 *
	 * @return BufferedImage
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun createBufferedImage(fragment: EditorFragmentComponent): BufferedImage
	{
		val size = fragment.preferredSize
		fragment.size = size
		fragment.doLayout()

		val image = UIUtil.createImage(size.width, size.height, BufferedImage.TYPE_INT_RGB)
		val graphics = image.graphics

		UISettings.setupAntialiasing(graphics)
		fragment.printAll(graphics)
		graphics.dispose()

		return image
	}

	/**
	 * Class TransferableImage
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.core.util.CodeFragmentPictureUtils
	 * @since 1.1.0
	 */
	class TransferableImage(private val image: Image): Transferable
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		@Throws(UnsupportedFlavorException::class)
		override fun getTransferData(flavor: DataFlavor): Any
		{
			if (flavor == DataFlavor.imageFlavor)
				return this.image

			throw UnsupportedFlavorException(flavor)
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun getTransferDataFlavors(): Array<DataFlavor?>
		{
			val flavors = arrayOfNulls<DataFlavor>(1)
			flavors[0] = DataFlavor.imageFlavor

			return flavors
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun isDataFlavorSupported(flavor: DataFlavor): Boolean
		{
			val flavors = transferDataFlavors

			return flavors.indices.any { flavor.equals(flavors[it]) }
		}

	}

}
