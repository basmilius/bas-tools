package com.basmilius.bastools.language.cappuccino.parser

import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.AUTOESCAPE_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.AUTOESCAPE_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.BLOCK_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.BLOCK_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ELSEIF_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ELSEIF_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ELSE_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ELSE_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.EMBED_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.EMBED_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDAUTOESCAPE_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDBLOCK_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDEMBED_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDFILTER_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDFOR_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDIF_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDMACRO_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDRAW_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDSANDBOX_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDSET_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDSPACELESS_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.ENDVERBATIM_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.FILTER_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.FILTER_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.FOR_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.FOR_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.IF_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.IF_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.MACRO_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.MACRO_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.RAW_BLOCK
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.RAW_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.SANDBOX_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.SANDBOX_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.SET_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.SET_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.SPACELESS_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.SPACELESS_TAG
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.VERBATIM_STATEMENT
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoElementTypes.Companion.VERBATIM_TAG
import com.intellij.psi.tree.IElementType
import java.util.*

/**
 * Object CappuccinoBlockStatements
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.parser
 */
object CappuccinoBlockStatements
{

	val StatementByStartTagMap = HashMap<IElementType, StatementDefinition>()
	val StatementByTypeMap = HashMap<IElementType, StatementDefinition>()

	/**
	 * CappuccinoBlockStatements Constructor.
	 *
	 * @author Bas Milius
	 */
	init
	{
		defineStatement(IF_STATEMENT, IF_TAG, ENDIF_TAG)
		defineStatement(ELSE_STATEMENT, ELSE_TAG).endsBefore(ELSE_TAG, ELSEIF_STATEMENT, ENDIF_TAG, ENDFOR_TAG)
		defineStatement(ELSEIF_STATEMENT, ELSEIF_TAG).endsBefore(ELSE_STATEMENT, ELSEIF_STATEMENT, ENDIF_TAG, ENDFOR_TAG)
		defineStatement(BLOCK_STATEMENT, BLOCK_TAG, ENDBLOCK_TAG).mayBeShort = true
		defineStatement(FOR_STATEMENT, FOR_TAG, ENDFOR_TAG)
		defineStatement(FILTER_STATEMENT, FILTER_TAG, ENDFILTER_TAG)
		defineStatement(AUTOESCAPE_STATEMENT, AUTOESCAPE_TAG, ENDAUTOESCAPE_TAG)
		defineStatement(MACRO_STATEMENT, MACRO_TAG, ENDMACRO_TAG)
		defineStatement(SPACELESS_STATEMENT, SPACELESS_TAG, ENDSPACELESS_TAG)
		defineStatement(EMBED_STATEMENT, EMBED_TAG, ENDEMBED_TAG)
		defineStatement(SANDBOX_STATEMENT, SANDBOX_TAG, ENDSANDBOX_TAG)
		defineStatement(VERBATIM_STATEMENT, VERBATIM_TAG, ENDVERBATIM_TAG)
		defineStatement(RAW_BLOCK, RAW_TAG, ENDRAW_TAG)
		defineStatement(SET_STATEMENT, SET_TAG, ENDSET_TAG).mayBeShort = true
	}

	/**
	 * Defines a statement.
	 *
	 * @param statementType IElementType
	 * @param tagType IElementType
	 * @param terminatorTypes ...IElementType
	 *
	 * @return StatementDefinition
	 *
	 * @author Bas Milius
	 */
	private fun defineStatement(statementType: IElementType, tagType: IElementType, vararg terminatorTypes: IElementType): StatementDefinition
	{
		val definition = StatementDefinition(statementType, tagType, *terminatorTypes)

		StatementByStartTagMap.put(tagType, definition)
		StatementByTypeMap.put(statementType, definition)

		return definition
	}

	/**
	 * Gets a {@see StatementDefinition} by {@see startTagType}.
	 *
	 * @param startTagType IElementType
	 *
	 * @return StatementDefinition
	 *
	 * @author Bas Milius
	 */
	fun getStatementDefinitionByStartTag(startTagType: IElementType) = StatementByStartTagMap[startTagType]

	/**
	 * Gets a {@see StatementDefinition} by {@see statementType}.
	 *
	 * @param statementType IElementType
	 *
	 * @return StatementDefinition
	 *
	 * @author Bas Milius
	 */
	fun getStatementDefinitionByType(statementType: IElementType) = StatementByTypeMap[statementType]

	/**
	 * Returns TRUE if {@see type} is a block statement.
	 *
	 * @param type IElementType
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun isBlockStatement(type: IElementType): Boolean = StatementByTypeMap.containsKey(type) || StatementByStartTagMap.contains(type)

	/**
	 * Class StatementDefinition
	 *
	 * @constructor
	 * @param statementType IElementType
	 * @param startTagType IElementType
	 * @param endTagTypes ...IElementType
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino.parser.CappuccinoBlockStatements
	 */
	class StatementDefinition(val statementType: IElementType, val startTagType: IElementType, vararg endTagTypes: IElementType)
	{

		val endsBeforeTypes = HashSet<IElementType>()
		val endTagTypes = HashSet<IElementType>()
		var mayBeShort = false

		/**
		 * StatementDefinition Constructor.
		 *
		 * @author Bas Milius
		 */
		init
		{
			this.endTagTypes.addAll(endTagTypes)
		}

		/**
		 * Adds ends before types.
		 *
		 * @param endsBeforeTypes ...IElementType
		 *
		 * @author Bas Milius
		 */
		fun endsBefore(vararg endsBeforeTypes: IElementType)
		{
			this.endsBeforeTypes.addAll(endsBeforeTypes)
		}

		/**
		 * Returns TRUE if {@see elementType} is terminated on.
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		fun isTerminatedOn(elementType: IElementType?) = this.endTagTypes.contains(elementType)

		/**
		 * Returns TRUE if {@see elementType} is terminated before.
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		fun isTerminatedBefore(elementType: IElementType?) = this.endsBeforeTypes.contains(elementType)

		/**
		 * Returns TRUE if {@see elementType} is a start tag type.
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		fun isStartTag(elementType: IElementType) = this.startTagType == elementType

		/**
		 * Returns TRUE if {@see elementType} is an end tag type.
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		fun isEndTag(elementType: IElementType) = this.endTagTypes.contains(elementType)

	}

}
