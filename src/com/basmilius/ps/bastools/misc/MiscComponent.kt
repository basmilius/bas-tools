package com.basmilius.ps.bastools.misc

import com.intellij.openapi.components.ApplicationComponent
import com.basmilius.ps.bastools.misc.angryDeveloper.AngryDeveloperFeature

/**
 * Class MiscComponent
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.misc
 */
class MiscComponent: ApplicationComponent
{

	private val features: Array<MiscFeature> = arrayOf(
			AngryDeveloperFeature()
	)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getComponentName(): String
	{
		return "Bas Tools - Misc stuff"
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
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
	 * @author Bas Milius
	 */
	override fun disposeComponent()
	{
		super.disposeComponent()

		this.features.forEach {
			it.dispose()
		}
	}

}
