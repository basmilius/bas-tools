package com.basmilius.bastools.framework.identity.installer

import com.basmilius.bastools.resource.Icons
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.platform.ProjectTemplate
import com.intellij.platform.ProjectTemplatesFactory

/**
 * Class IdentityInstallerTemplatesFactory
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.framework.identity.installer
 */
class IdentityInstallerTemplatesFactory: ProjectTemplatesFactory()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getGroups() = Array(1, { _ -> "PHP" })

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getGroupIcon(s: String) = Icons.IdeeMedia

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createTemplates(s: String?, w: WizardContext?): Array<ProjectTemplate> = arrayOf(IdentityInstallerWebProjectTemplate())

}
