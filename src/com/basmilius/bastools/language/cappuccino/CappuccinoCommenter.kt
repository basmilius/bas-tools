package com.basmilius.bastools.language.cappuccino

import com.intellij.lang.Commenter

/**
 * Class CappuccinoCommenter
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoCommenter: Commenter
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getCommentedBlockCommentPrefix() = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getCommentedBlockCommentSuffix() = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getBlockCommentPrefix() = "{#"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getBlockCommentSuffix() = "#}"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getLineCommentPrefix() = null

}
