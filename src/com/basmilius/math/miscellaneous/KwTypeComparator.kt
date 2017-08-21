package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.KeyWord

/**
 * Class KwTypeComparator
 *
 * @author Bas Milius
 * @package com.basmilius.math.miscellaneous
 */
class KwTypeComparator: Comparator<KeyWord>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun compare(kw1: KeyWord, kw2: KeyWord): Int
	{
		val t1 = kw1.wordTypeId * 1000000 + kw1.wordId * 1000 + kw1.wordString.length
		val t2 = kw2.wordTypeId * 1000000 + kw2.wordId * 1000 + kw2.wordString.length

		return t1 - t2
	}

}
