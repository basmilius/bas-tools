/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.parsertokens

/**
 * Class KeyWord
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
class KeyWord
{

	/**
	 * Companion Object KeyWord
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.math.parsertokens
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
