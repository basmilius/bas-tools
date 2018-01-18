package com.basmilius.bastools.misc

import com.intellij.openapi.components.ApplicationComponent
import com.basmilius.bastools.misc.angryDeveloper.AngryDeveloperFeature

/**
 * Class MiscComponent
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.misc
 */
class MiscComponent: ApplicationComponent
{

	private val features: Array<MiscFeature> = arrayOf(
			AngryDeveloperFeature()
	)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getComponentName() = "Bas Tools - Misc stuff"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun initComponent()
	{
		super.initComponent()

		this.features.forEach {
			it.init()
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun disposeComponent()
	{
		super.disposeComponent()

		this.features.forEach {
			it.dispose()
		}
	}

}
