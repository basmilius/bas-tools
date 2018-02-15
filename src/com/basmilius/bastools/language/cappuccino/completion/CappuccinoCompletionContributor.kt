package com.basmilius.bastools.language.cappuccino.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionContributor
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoTag
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.PlatformUtils

/**
 * Class CappuccinoCompletionContributor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.completion
 */
class CappuccinoCompletionContributor: BaseCompletionContributor()
{

	/**
	 * Companion Object CappuccinoCompletionContributor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.completion
	 */
	companion object
	{

		private val BuildInFilters = arrayOf("date", "format", "replace", "url_encode", "json_encode", "title", "capitalize", "upper", "lower", "striptags", "join", "reverse", "length", "sort", "default", "keys", "escape", "raw", "merge")

		/**
		 * Adds build-in filters to completion result set.
		 *
		 * @param result CompletionResultSet
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		fun addBuildInFilterLookups(result: CompletionResultSet)
		{
			for (filter in BuildInFilters)
				result.addElement(LookupElementBuilder.create(filter))
		}

		/**
		 * Adds tag name lookups to completion result set.
		 *
		 * @result result CompletionResultSet
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		fun addTagNameLookups(result: CompletionResultSet)
		{
			val types = CappuccinoElementTypes.TAGS.types
					.mapNotNull { it as? CappuccinoTag }

			for (type in types)
				result.addElement(LookupElementBuilder.create(type.tagName))
		}

	}

	/**
	 * CappuccinoCompletionContributor Constructor.
	 */
	init
	{
		this.extend(CompletionType.BASIC, CappuccinoBlockCompletionProvider())
		this.extend(CompletionType.BASIC, CappuccinoKeywordCompletionProvider())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isAvailable() = PlatformUtils.isPhpStorm()

}
