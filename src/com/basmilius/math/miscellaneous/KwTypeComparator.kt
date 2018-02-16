/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.miscellaneous

import com.basmilius.math.parsertokens.KeyWord

/**
 * Class KwTypeComparator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.miscellaneous
 */
class KwTypeComparator: Comparator<KeyWord>
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun compare(kw1: KeyWord, kw2: KeyWord): Int
	{
		val t1 = kw1.wordTypeId * 1000000 + kw1.wordId * 1000 + kw1.wordString.length
		val t2 = kw2.wordTypeId * 1000000 + kw2.wordId * 1000 + kw2.wordString.length

		return t1 - t2
	}

}
