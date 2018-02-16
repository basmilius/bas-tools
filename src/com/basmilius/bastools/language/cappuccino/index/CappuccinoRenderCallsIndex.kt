/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.util.io.KeyDescriptor
import com.jetbrains.php.lang.psi.elements.MethodReference

/**
 * Class CappuccinoRenderCallsIndex
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.index
 * @since 1.4.0
 */
class CappuccinoRenderCallsIndex: StringStubIndexExtension<MethodReference>()
{

	override fun getKey(): StubIndexKey<String, MethodReference>
	{
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun getVersion(): Int
	{
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun getKeyDescriptor(): KeyDescriptor<String>
	{
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}
