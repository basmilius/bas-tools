package com.basmilius.math.parsertokens

/**
 * Class Token
 *
 * @author Bas Milius
 * @package com.basmilius.math.parsertokens
 */
class Token
{

	/**
	 * Companion Object Token
	 *
	 * @author Bas Milius
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
	 * @author Bas Milius
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
