package com.basmilius.bastools.language.cappuccino.injection

import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoVerbatimStatement
import com.intellij.lang.Language
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.psi.PsiElement
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider
import java.util.*

/**
 * Class CappuccinoVerbatimInjector
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.injection
 */
class CappuccinoVerbatimInjector: MultiHostInjector
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> = Collections.singletonList(CappuccinoVerbatimStatement::class.java)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement)
	{
		if (context !is CappuccinoVerbatimStatement)
			return

		val content = context.getContentElement() ?: return
		val language = this.getInjectedLanguage(context, content) ?: return

		registrar.startInjecting(language)

		val injectionRange = content.textRange.shiftRight(-context.textRange.startOffset)

		registrar.addPlace(context.getStartTagText(), context.getEndTagText(), context, injectionRange)
		registrar.doneInjecting()
	}

	/**
	 * Gets the injected {@see Language}.
	 *
	 * @param verbatimStatement CappuccinoVerbatimStatement
	 * @param content PsiElement
	 *
	 * @return Language
	 *
	 * @author Bas Milius
	 */
	private fun getInjectedLanguage(verbatimStatement: CappuccinoVerbatimStatement, content: PsiElement): Language?
	{
		if (verbatimStatement.isValid)
		{
			val file = verbatimStatement.containingFile
			val viewProvider = file.viewProvider

			if (viewProvider is TemplateLanguageFileViewProvider)
			{
				val dataLanguage = viewProvider.templateDataLanguage
				val dataFile = viewProvider.getPsi(dataLanguage)

				if (dataFile != null)
				{
					var dataContext = viewProvider.findElementAt(content.textOffset + 1, dataLanguage)

					if (dataContext != null && dataContext.node.elementType == CappuccinoTokenTypes.OUTER_ELEMENT_TYPE)
						dataContext = dataContext.parent

					if (dataContext != null)
						return dataContext.language
				}
			}
		}

		return null
	}

}
