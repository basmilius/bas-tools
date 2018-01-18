package com.basmilius.bastools.language.cappuccino

import com.intellij.lang.Commenter

/**
 * Class CappuccinoCommenter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoCommenter: Commenter
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getCommentedBlockCommentPrefix() = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getCommentedBlockCommentSuffix() = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getBlockCommentPrefix() = "{#"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getBlockCommentSuffix() = "#}"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getLineCommentPrefix() = null

}
