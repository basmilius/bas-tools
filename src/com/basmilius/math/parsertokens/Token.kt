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
 * Class Token
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
class Token
{

	/**
	 * Companion Object Token
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.math.parsertokens
	 */
	companion object
	{

		val NOT_MATCHED = KeyWord.NO_DEFINITION

	}

	var tokenStr = ""
	var keyWord = ""
	var tokenId = NOT_MATCHED
	var tokenTypeId = NOT_MATCHED
	var tokenLevel = -1
	var tokenValue = Double.NaN
	var looksLike = ""

	/**
	 * Token Cloning.
	 *
	 * @return Token
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun clone(): Token
	{
		val token = Token()

		token.keyWord = this.keyWord
		token.tokenStr = this.tokenStr
		token.tokenId = this.tokenId
		token.tokenLevel = this.tokenLevel
		token.tokenTypeId = this.tokenTypeId
		token.tokenValue = this.tokenValue
		token.looksLike = this.looksLike

		return token
	}

}
