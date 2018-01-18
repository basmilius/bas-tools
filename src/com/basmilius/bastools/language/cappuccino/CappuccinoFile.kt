package com.basmilius.bastools.language.cappuccino

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.jetbrains.annotations.NotNull

/**
 * Class CappuccinoFile
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoFile(viewProvider: FileViewProvider): PsiFileBase(viewProvider, CappuccinoLanguage.Instance)
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	@NotNull
	override fun getFileType(): FileType = CappuccinoFileType.Instance

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun toString(): String = "CappuccinoFile: ${this.name}"

}
