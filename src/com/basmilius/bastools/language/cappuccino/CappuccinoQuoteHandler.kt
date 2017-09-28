package com.basmilius.bastools.language.cappuccino

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.highlighter.HighlighterIterator

/**
 * Class CappuccinoQuoteHandler
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoQuoteHandler: SimpleTokenSetQuoteHandler(CappuccinoTokenTypes.SINGLE_QUOTE, CappuccinoTokenTypes.DOUBLE_QUOTE)
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun isClosingQuote(iterator: HighlighterIterator, offset: Int): Boolean
	{
		return if (this.myLiteralTokenSet.contains(iterator.tokenType) && iterator.end - iterator.start == 1)
		{
			!this.isOpeningQuoteInternal(iterator)
		}
		else
		{
			false
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun isOpeningQuote(iterator: HighlighterIterator, offset: Int): Boolean
	{
		return if (this.myLiteralTokenSet.contains(iterator.tokenType) && offset == iterator.start)
		{
			this.isOpeningQuoteInternal(iterator)
		}
		else
		{
			false
		}
	}

	/**
	 * Internal isOpeningQuote method.
	 *
	 * @author Bas Milius
	 */
	private fun isOpeningQuoteInternal(iterator: HighlighterIterator): Boolean
	{
		iterator.retreat()

		val isOpeningQuote: Boolean

		try
		{
			if (!iterator.atEnd())
			{
				val type = iterator.tokenType
				return !this.myLiteralTokenSet.contains(type) && CappuccinoTokenTypes.STRING_TEXT == type
			}

			isOpeningQuote = true
		}
		finally
		{
			iterator.advance()
		}

		return isOpeningQuote
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun hasNonClosedLiteral(editor: Editor, iterator: HighlighterIterator, offset: Int): Boolean
	{
		val start = iterator.start

		try
		{
			val document = editor.document
			val chars = document.charsSequence
			val lineEnd = document.getLineEndOffset(document.getLineNumber(offset))

			while (!iterator.atEnd() && iterator.start < lineEnd)
			{
				val type = iterator.tokenType

				if (this.myLiteralTokenSet.contains(type) && (iterator.start >= iterator.end - 1 || (chars[iterator.end - 1] != '"' && chars[iterator.end - 1] != '\'')))
					return true

				iterator.advance()
			}

			return false
		}
		finally
		{
			while(true)
			{
				if (!iterator.atEnd() && iterator.start == start)
				{
				}
				else
				{
					iterator.retreat()
				}
			}
		}
	}

}
