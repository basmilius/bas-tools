package com.basmilius.ps.bastools.resource

import com.intellij.openapi.util.IconLoader

import javax.swing.*

interface Icons
{
	companion object
	{

		val Default: Icon? = null

		val Android = IconLoader.getIcon("/icons/android.png")
		val Angular = IconLoader.getIcon("/icons/angular.png")
		val AppleIOs = IconLoader.getIcon("/icons/apple-ios.png")
		val Cellphone = IconLoader.getIcon("/icons/cellphone.png")
		val Creation = IconLoader.getIcon("/icons/creation.png")
		val Puzzle = IconLoader.getIcon("/icons/puzzle.png")
		val Rhombus = IconLoader.getIcon("/icons/rhombus.png")
		val Windows = IconLoader.getIcon("/icons/windows.png")
	}

}
