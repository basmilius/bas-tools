package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.KeyWord

/**
 * Class DescKwLenComparator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.miscellaneous
 */
class DescKwLenComparator: Comparator<KeyWord>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun compare(kw1: KeyWord, kw2: KeyWord): Int
	{
		return kw2.wordString.length - kw1.wordString.length
	}

}
