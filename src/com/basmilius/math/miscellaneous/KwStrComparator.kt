package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.KeyWord

/**
 * Class KwStrComparator
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.miscellaneous
 */
class KwStrComparator: Comparator<KeyWord>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun compare(kw1: KeyWord, kw2: KeyWord): Int
	{
		return kw1.wordString.compareTo(kw2.wordString)
	}

}
