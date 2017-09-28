package com.basmilius.bastools.language.cappuccino

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull

/**
 * Class CappuccinoElementType
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoElementType(@NotNull @NonNls debugName: String): IElementType(debugName, CappuccinoLanguage.Instance)
