package com.basmilius.bastools.core.configurable.personal

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider
import com.intellij.openapi.components.ServiceManager
import com.intellij.psi.PsiDirectory
import java.util.*

/**
 * Class BasToolsPersonalTemplatePropertiesProvider
 *
 * @author Bas Milius
 * @packate com.basmilius.bastools.core.configurable.personal
 * @since 1.3.0
 */
class BasToolsPersonalTemplatePropertiesProvider: DefaultTemplatePropertiesProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 * @since 1.3.0
	 */
	override fun fillProperties(directory: PsiDirectory, properties: Properties)
	{
		val personalConfigurable = ServiceManager.getService(BasToolsPersonalConfigurable.PersonalConfigurationState::class.java)

		properties["BT_PERSONAL_NAME"] = personalConfigurable.fullName
		properties["BT_PERSONAL_EMAIL"] = personalConfigurable.eMail
		properties["BT_PROJECT_VERSION"] = "bt.version!"
	}

}
