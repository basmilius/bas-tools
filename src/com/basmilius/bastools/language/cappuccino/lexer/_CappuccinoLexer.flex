package com.basmilius.bastools.language.cappuccino.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes;
%%

/*
 * TODO(Bas): Add {% verbatim %} .. {% endverbatim %} support. Should be VERBATIM_CONTENT.
 */

/* -----------------Options and Declarations Section----------------- */

%class _CappuccinoLexer
%implements FlexLexer
%public
%unicode
%public

%function advance
%type IElementType

%eof{ return;
%eof}

/* Macro Declarations  */

CAPPUCCINO_VAR_OPEN = "{{"
CAPPUCCINO_VAR_CLOSE = "}}"

COMMENT_OPEN = "{#"
COMMENT_CLOSE = "#}"

CAPPUCCINO_STMT_OPEN = "{%"
CAPPUCCINO_STMT_CLOSE = "%}"

IDENTIFIER=[a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*
WHITESPACE=[ \n\r\t]+

ANY_CHAR=[.]

DOUBLE_QUOTES_CHARS=(([^\"\\]|("\\"{ANY_CHAR})))


LNUM=[0-9]+
DNUM=([0-9]*"."[0-9]+)|([0-9]+"."[0-9]*)
EXPONENT_DNUM=(({LNUM}|{DNUM})[eE][+-]?{LNUM})
HNUM="0x"[0-9a-fA-F]+
TABS_AND_SPACES=[ \t]*
BACKQUOTE_CHARS=(([^`\\]|("\\"{ANY_CHAR})))
NEWLINE=("\r"|"\n"|"\r\n")



/* lexical states */

%state ST_CAPPUCCINO_VAR
%state ST_CAPPUCCINO_COMMENT
%state ST_CAPPUCCINO_BLOCK_NAME

%state ST_BLOCK_EXPRESSION
%state ST_EXPRESSION_EXPRESSION

%state ST_DOUBLE_QUOTES
%state ST_BACKQUOTE


%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> (  ( [^{] | "{" [^?%s{#] )+  ) |" {s" | "{"
{
        // raw content
        return CappuccinoTokenTypes.TEMPLATE_HTML_TEXT;
}

<YYINITIAL>
{
    {CAPPUCCINO_VAR_OPEN}
    {
        yybegin(ST_EXPRESSION_EXPRESSION);
        return CappuccinoTokenTypes.PRINT_BLOCK_START;
    }

	{COMMENT_OPEN}
	{
		yybegin(ST_CAPPUCCINO_COMMENT);
		return CappuccinoTokenTypes.COMMENT;
	}

    {CAPPUCCINO_STMT_OPEN}
    {
        yybegin(ST_CAPPUCCINO_BLOCK_NAME);
        return CappuccinoTokenTypes.STATEMENT_BLOCK_START;
    }

    {WHITESPACE} { return CappuccinoTokenTypes.WHITE_SPACE; }

    .            { return CappuccinoTokenTypes.BAD_CHARACTER; }
}

<ST_CAPPUCCINO_COMMENT>
{
	{COMMENT_CLOSE}     {yybegin(YYINITIAL); return CappuccinoTokenTypes.COMMENT; }

    {WHITESPACE}        { return CappuccinoTokenTypes.COMMENT; }

    .                   { return CappuccinoTokenTypes.COMMENT; }
}

<ST_CAPPUCCINO_BLOCK_NAME>
{
    {IDENTIFIER}
    {
        yybegin(ST_BLOCK_EXPRESSION);
        return CappuccinoTokenTypes.TAG_NAME;
    }

    {CAPPUCCINO_STMT_CLOSE}
    {
        yybegin(YYINITIAL);
        return CappuccinoTokenTypes.STATEMENT_BLOCK_END;
    }

    {WHITESPACE}  {return CappuccinoTokenTypes.WHITE_SPACE;}

    .
    {
    	yybegin(YYINITIAL);
    	yypushback(1);
    }
}

<ST_EXPRESSION_EXPRESSION, ST_BLOCK_EXPRESSION>
{
	(true|false)                                    {return CappuccinoTokenTypes.BOOLEAN;}

	/* STRING LITERALS */
    (b?[\"]{DOUBLE_QUOTES_CHARS}*[\"])              {return CappuccinoTokenTypes.DOUBLE_QUOTE;}
    (b?[']([^'\\]|("\\"{ANY_CHAR}))*['])            {return CappuccinoTokenTypes.SINGLE_QUOTE;}

    {LNUM}                                          {return CappuccinoTokenTypes.NUMBER;}
    {DNUM}                                          {return CappuccinoTokenTypes.NUMBER;}
    {EXPONENT_DNUM}                                 {return CappuccinoTokenTypes.NUMBER;}
    {HNUM}                                          {return CappuccinoTokenTypes.NUMBER;}

    /* KEYWORDS */
    "b-or"                                          {return CappuccinoTokenTypes.BITWISE_OR;}
    "b-xor"                                         {return CappuccinoTokenTypes.BITWISE_XOR;}
    "b-and"                                         {return CappuccinoTokenTypes.BITWISE_AND;}
    "starts with"                                   {return CappuccinoTokenTypes.STARTS_WITH;}
    "ends with"                                     {return CappuccinoTokenTypes.ENDS_WITH;}
    "matches"                                       {return CappuccinoTokenTypes.MATCHES;}
    "and"                                           {return CappuccinoTokenTypes.AND;}
    "or"                                            {return CappuccinoTokenTypes.OR;}
    "not"                                           {return CappuccinoTokenTypes.NOT;}
    "not in"                                        {return CappuccinoTokenTypes.NOT_IN;}
    "as"                                            {return CappuccinoTokenTypes.AS_KEYWORD;}
    "import"                                        {return CappuccinoTokenTypes.IMPORT_KEYWORD;}
    "if"                                            {return CappuccinoTokenTypes.IF_KEYWORD;}
    "in"                                            {return CappuccinoTokenTypes.IN;}
    "is"                                            {return CappuccinoTokenTypes.IS;}

    /* OPERATORS */
    ".."                                            {return CappuccinoTokenTypes.RANGE;}
    "??"                                            {return CappuccinoTokenTypes.COALESCE;}
    "&&"                                            {return CappuccinoTokenTypes.AND;}
    "||"                                            {return CappuccinoTokenTypes.OR;}
    "."                                             {return CappuccinoTokenTypes.DOT;}
    ","                                             {return CappuccinoTokenTypes.COMMA;}
    ":"                                             {return CappuccinoTokenTypes.COLON;}
    "?"                                             {return CappuccinoTokenTypes.QUESTION;}
    "|"                                             {return CappuccinoTokenTypes.FILTER;}
    "=="                                            {return CappuccinoTokenTypes.EQ_EQ;}
    "!="                                            {return CappuccinoTokenTypes.NOT_EQ;}
    ">"                                             {return CappuccinoTokenTypes.GT;}
    "<"                                             {return CappuccinoTokenTypes.LT;}
    ">="                                            {return CappuccinoTokenTypes.GE;}
    "<="                                            {return CappuccinoTokenTypes.LE;}
    "!"                                             {return CappuccinoTokenTypes.NOT;}

    /* PARENTHS */
    "("                                             {return CappuccinoTokenTypes.LBRACE;}
    ")"                                             {return CappuccinoTokenTypes.RBRACE;}
    "["                                             {return CappuccinoTokenTypes.LBRACE_SQ;}
    "]"                                             {return CappuccinoTokenTypes.RBRACE_SQ;}
    "{"                                             {return CappuccinoTokenTypes.LBRACE_CURL;}
    "}"                                             {return CappuccinoTokenTypes.RBRACE_CURL;}

    /* OTHERS */
    "~"                                             {return CappuccinoTokenTypes.CONCAT;}
    "="                                             {return CappuccinoTokenTypes.EQ;}
    "%"                                             {return CappuccinoTokenTypes.MOD;}
    "+"                                             {return CappuccinoTokenTypes.PLUS;}
    "-"                                             {return CappuccinoTokenTypes.MINUS;}
    "**"                                            {return CappuccinoTokenTypes.POW;}
    "*"                                             {return CappuccinoTokenTypes.MUL;}
    "//"                                            {return CappuccinoTokenTypes.DIV_DIV;}
    "/"                                             {return CappuccinoTokenTypes.DIV;}

    {IDENTIFIER}                                    {return CappuccinoTokenTypes.IDENTIFIER;}

    {WHITESPACE}                                    {return CappuccinoTokenTypes.WHITE_SPACE;}
    .                                               {return CappuccinoTokenTypes.BAD_CHARACTER;}

}
<ST_BLOCK_EXPRESSION>
{
    {CAPPUCCINO_STMT_CLOSE}
    {
        yybegin(YYINITIAL);
        return CappuccinoTokenTypes.STATEMENT_BLOCK_END;
    }
}
<ST_EXPRESSION_EXPRESSION>
{
    {CAPPUCCINO_VAR_CLOSE}
    {
        yybegin(YYINITIAL);
        return CappuccinoTokenTypes.PRINT_BLOCK_END;
    }
}
