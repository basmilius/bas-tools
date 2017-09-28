package com.basmilius.bastools.language.cappuccino

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory
import org.jetbrains.annotations.NotNull

/**
 * Class CappuccinoFileTypeFactory
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoFileTypeFactory: FileTypeFactory()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createFileTypes(@NotNull consumer: FileTypeConsumer)
	{
		consumer.consume(CappuccinoFileType.Instance, CappuccinoFileType.Instance.defaultExtension)
	}

}
