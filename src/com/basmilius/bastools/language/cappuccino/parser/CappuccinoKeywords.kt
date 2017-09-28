package com.basmilius.bastools.language.cappuccino.parser

/**
 * Interface CappuccinoKeywords
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.parser
 */
interface CappuccinoKeywords
{

	/**
	 * Companion Object CappuccinoKeywords
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.parser
	 */
	companion object
	{

		val If = "if"
		val Else = "else"
		val ElseIf = "elseif"
		val EndIf = "endif"
		val Block = "block"
		val EndBlock = "endblock"
		val For = "for"
		val ENDFor = "endfor"
		val Filter = "filter"
		val ENDFilter = "endfilter"
		val AutoEscape = "autoescape"
		val ENDAutoEscape = "endautoescape"
		val Macro = "macro"
		val ENDMacro = "endmacro"
		val Spaceless = "spaceless"
		val EndSpaceless = "endspaceless"
		val Embed = "embed"
		val EndEmbed = "endembed"
		val Import = "import"
		val Extends = "extends"
		val As = "as"
		val From = "from"
		val Set = "set"
		val EndSet = "endset"
		val Sandbox = "sandbox"
		val EndSandbox = "endsandbox"
		val Do = "do"
		val Flush = "flush"
		val Include = "include"
		val Verbatim = "verbatim"
		val EndVerbatim = "endverbatim"
		val Raw = "raw"
		val EndRaw = "endraw"

	}

}
