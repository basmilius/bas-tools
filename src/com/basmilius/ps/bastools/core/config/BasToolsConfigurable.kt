package com.basmilius.ps.bastools.core.config

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import org.jetbrains.annotations.Nls

import javax.swing.*

/**
 * Class BasToolsConfigurable
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.core.config
 */
class BasToolsConfigurable : SearchableConfigurable
{

	private var gui : BasToolsConfigurableGUI? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@Nls
	override fun getDisplayName() : String
	{
		return "Bas Tools Preferences"
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getHelpTopic() : String
	{
		return "preference.BasTools"
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getId() : String
	{
		return "preference.BasTools"
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun enableSearch(searchQuery : String?) : Runnable?
	{
		return null
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createComponent() : JComponent?
	{
		this.gui = BasToolsConfigurableGUI()
		return this.gui!!.rootPanel
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun isModified() : Boolean
	{
		return false
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	@Throws(ConfigurationException::class)
	override fun apply()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun reset()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun disposeUIResources()
	{
		this.gui = null
	}

}
