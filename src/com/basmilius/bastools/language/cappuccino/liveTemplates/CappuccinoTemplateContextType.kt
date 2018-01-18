package com.basmilius.bastools.language.cappuccino.liveTemplates

import com.basmilius.bastools.language.cappuccino.CappuccinoFile
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile
import com.intellij.psi.xml.XmlTokenType

/**
 * Class CappuccinoTemplateContextType
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.liveTemplates
 */
class CappuccinoTemplateContextType: TemplateContextType("Cappuccino", "Cappuccino")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isInContext(file: PsiFile, offset: Int): Boolean
	{
		if (file is CappuccinoFile)
		{
			val currentElement = file.findElementAt(offset)

			if (currentElement != null)
				return currentElement.node.elementType == XmlTokenType.XML_DATA_CHARACTERS
		}

		return false
	}

}
