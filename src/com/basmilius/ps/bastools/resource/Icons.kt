package com.basmilius.ps.bastools.resource

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * Interface Icons
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.resource
 */
interface Icons
{

	/**
	 * Companion Object for Icons
	 *
	 * @author Bas Milius
	 */
	companion object
	{

		val Default : Icon? = null

		val Android = IconLoader.getIcon("/icons/android.png")
		val Angular = IconLoader.getIcon("/icons/angular.png")
		val AppleIOs = IconLoader.getIcon("/icons/apple-ios.png")
		val Beats = IconLoader.getIcon("/icons/beats.png")
		val Cellphone = IconLoader.getIcon("/icons/cellphone.png")
		val Creation = IconLoader.getIcon("/icons/creation.png")
		val Puzzle = IconLoader.getIcon("/icons/puzzle.png")
		val Rhombus = IconLoader.getIcon("/icons/rhombus.png")
		val Windows = IconLoader.getIcon("/icons/windows.png")
	}

}
