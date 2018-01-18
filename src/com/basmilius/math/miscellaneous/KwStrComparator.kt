package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.KeyWord

/**
 * Class KwStrComparator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.miscellaneous
 */
class KwStrComparator: Comparator<KeyWord>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun compare(kw1: KeyWord, kw2: KeyWord): Int
	{
		return kw1.wordString.compareTo(kw2.wordString)
	}

}
