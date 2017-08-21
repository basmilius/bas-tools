package com.basmilius.math.parsertokens

/**
 * Class KeyWord
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.parsertokens
 */
class KeyWord
{

	/**
	 * Companion Object KeyWord
	 *
	 * @author Bas Milius
	 * @package com.basmilius.math.mxparser.parsertokens
	 */
	companion object
	{

		val NO_DEFINITION = ConstantValue.NaN

	}

	var wordString: String
	var wordId: Int
	var wordTypeId: Int
	var description: String
	var syntax: String
	var since: String

	/**
	 * KeyWord Constructor.
	 *
	 * @author Bas Milius
	 */
	constructor()
	{
		this.wordString = ""
		this.wordId = NO_DEFINITION
		this.wordTypeId = NO_DEFINITION
		this.description = ""
		this.syntax = ""
		this.since = ""
	}

	/**
	 * KeyWord Constructor.
	 *
	 * @param wordString String
	 * @param description String
	 * @param wordId Int
	 * @param syntax String
	 * @param since String
	 * @param wordTypeId Int
	 *
	 * @author Bas Milius
	 */
	constructor(wordString: String, description: String, wordId: Int, syntax: String, since: String, wordTypeId: Int)
	{
		this.wordString = wordString
		this.wordId = wordId
		this.wordTypeId = wordTypeId
		this.description = description
		this.syntax = syntax
		this.since = since
	}

}
