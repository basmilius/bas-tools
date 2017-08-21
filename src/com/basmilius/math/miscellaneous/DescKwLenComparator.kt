package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.KeyWord

/**
 * Class DescKwLenComparator
 *
 * @author Bas Milius
 * @package com.basmilius.math.miscellaneous
 */
class DescKwLenComparator: Comparator<KeyWord>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun compare(kw1: KeyWord, kw2: KeyWord): Int
	{
		return kw2.wordString.length - kw1.wordString.length
	}

}
