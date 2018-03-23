/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.renderer

/**
 * Gets the bottom weight of a character.
 *
 * @param c Int
 *
 * @return Float
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.4.0
 */
fun getBottomWeight(c: Int): Float
{
	when (c)
	{
		in 0..32 -> return 0.0f

		33 -> return 0.1779f //  = '!'
		34 -> return 0.0000f //  = '"'
		35 -> return 0.5714f //  = '#'
		36 -> return 0.6160f //  = '$'
		37 -> return 0.5423f //  = '%'
		38 -> return 0.7204f //  = '&'
		39 -> return 0.0000f //  = '''
		40 -> return 0.5134f //  = '('
		41 -> return 0.5089f //  = ')'
		42 -> return 0.1042f //  = '*'
		43 -> return 0.2286f //  = '+'
		44 -> return 0.2983f //  = ','
		45 -> return 0.0000f //  = '-'
		46 -> return 0.1777f //  = '.'
		47 -> return 0.5943f //  = '/'
		48 -> return 0.5831f //  = '0'
		49 -> return 0.5143f //  = '1'
		50 -> return 0.4724f //  = '2'
		51 -> return 0.4715f //  = '3'
		52 -> return 0.5712f //  = '4'
		53 -> return 0.4421f //  = '5'
		54 -> return 0.6080f //  = '6'
		55 -> return 0.3399f //  = '7'
		56 -> return 0.6481f //  = '8'
		57 -> return 0.4235f //  = '9'
		58 -> return 0.1777f //  = ':'
		59 -> return 0.2983f //  = ';'
		60 -> return 0.3509f //  = '<'
		61 -> return 0.2857f //  = '='
		62 -> return 0.3942f //  = '>'
		63 -> return 0.1766f //  = '?'
		64 -> return 0.7926f //  = '@'
		65 -> return 0.6156f //  = 'A'
		66 -> return 0.6830f //  = 'B'
		67 -> return 0.4296f //  = 'C'
		68 -> return 0.6364f //  = 'D'
		69 -> return 0.5143f //  = 'E'
		70 -> return 0.3429f //  = 'F'
		71 -> return 0.6476f //  = 'G'
		72 -> return 0.6857f //  = 'H'
		73 -> return 0.5714f //  = 'I'
		74 -> return 0.4083f //  = 'J'
		75 -> return 0.6617f //  = 'K'
		76 -> return 0.5143f //  = 'L'
		77 -> return 0.3139f //  = 'M'
		78 -> return 0.5416f //  = 'N'
		79 -> return 0.5759f //  = 'O'
		80 -> return 0.4576f //  = 'P'
		81 -> return 0.7689f //  = 'Q'
		82 -> return 0.6761f //  = 'R'
		83 -> return 0.4923f //  = 'S'
		84 -> return 0.3429f //  = 'T'
		85 -> return 0.6283f //  = 'U'
		86 -> return 0.3516f //  = 'V'
		87 -> return 0.5564f //  = 'W'
		88 -> return 0.5479f //  = 'X'
		89 -> return 0.3460f //  = 'Y'
		90 -> return 0.4715f //  = 'Z'
		91 -> return 0.6857f //  = '['
		92 -> return 0.5941f //  = '\'
		93 -> return 0.6857f //  = ']'
		94 -> return 0.0000f //  = '^'
		95 -> return 0.3429f //  = '_'
		96 -> return 0.0000f //  = '`'
		97 -> return 0.6853f //  = 'a'
		98 -> return 0.6431f //  = 'b'
		99 -> return 0.3966f //  = 'c'
		100 -> return 0.6521f //  = 'd'
		101 -> return 0.6844f //  = 'e'
		102 -> return 0.3429f //  = 'f'
		103 -> return 0.9927f //  = 'g'
		104 -> return 0.6857f //  = 'h'
		105 -> return 0.4052f //  = 'i'
		106 -> return 0.6463f //  = 'j'
		107 -> return 0.6015f //  = 'k'
		108 -> return 0.4013f //  = 'l'
		109 -> return 0.4000f //  = 'm'
		110 -> return 0.6857f //  = 'n'
		111 -> return 0.5799f //  = 'o'
		112 -> return 0.9109f //  = 'p'
		113 -> return 0.9154f //  = 'q'
		114 -> return 0.3429f //  = 'r'
		115 -> return 0.5450f //  = 's'
		116 -> return 0.4627f //  = 't'
		117 -> return 0.6678f //  = 'u'
		118 -> return 0.3538f //  = 'v'
		119 -> return 0.6884f //  = 'w'
		120 -> return 0.5461f //  = 'x'
		121 -> return 0.6521f //  = 'y'
		122 -> return 0.5183f //  = 'z'
		123 -> return 0.6649f //  = '{'
		124 -> return 0.5714f //  = '|'
		125 -> return 0.6136f //  = '}'
		126 -> return 0.1950f //  = '~'
		else -> return 0.4f
	}
}

/**
 * Gets the top weight of a character.
 *
 * @param c Int
 *
 * @return Float
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.4.0
 */
fun getTopWeight(c: Int): Float
{
	when (c)
	{
		in 0..32 -> return 0.0f
		33 -> return 0.2816f // = '!'
		34 -> return 0.4865f // = '"'
		35 -> return 0.4769f // = '#'
		36 -> return 0.5066f // = '$'
		37 -> return 0.4510f // = '%'
		38 -> return 0.2971f // = '&'
		39 -> return 0.3274f // = '''
		40 -> return 0.4275f // = '('
		41 -> return 0.4273f // = ')'
		42 -> return 0.3643f // = '*'
		43 -> return 0.1905f // = '+'
		44 -> return 0.0000f // = ','
		45 -> return 0.0000f // = '-'
		46 -> return 0.0000f // = '.'
		47 -> return 0.3961f // = '/'
		48 -> return 0.5221f // = '0'
		49 -> return 0.4045f // = '1'
		50 -> return 0.3419f // = '2'
		51 -> return 0.3830f // = '3'
		52 -> return 0.4157f // = '4'
		53 -> return 0.4060f // = '5'
		54 -> return 0.3501f // = '6'
		55 -> return 0.4228f // = '7'
		56 -> return 0.5798f // = '8'
		57 -> return 0.5005f // = '9'
		58 -> return 0.1481f // = ':'
		59 -> return 0.1481f // = ';'
		60 -> return 0.3989f // = '<'
		61 -> return 0.2381f // = '='
		62 -> return 0.4476f // = '>'
		63 -> return 0.3414f // = '?'
		64 -> return 0.3910f // = '@'
		65 -> return 0.3343f // = 'A'
		66 -> return 0.5707f // = 'B'
		67 -> return 0.3498f // = 'C'
		68 -> return 0.5326f // = 'D'
		69 -> return 0.4286f // = 'E'
		70 -> return 0.3810f // = 'F'
		71 -> return 0.3488f // = 'G'
		72 -> return 0.5714f // = 'H'
		73 -> return 0.4762f // = 'I'
		74 -> return 0.3810f // = 'J'
		75 -> return 0.5376f // = 'K'
		76 -> return 0.2857f // = 'L'
		77 -> return 0.4928f // = 'M'
		78 -> return 0.5173f // = 'N'
		79 -> return 0.4781f // = 'O'
		80 -> return 0.5619f // = 'P'
		81 -> return 0.4775f // = 'Q'
		82 -> return 0.5716f // = 'R'
		83 -> return 0.3826f // = 'S'
		84 -> return 0.4762f // = 'T'
		85 -> return 0.5714f // = 'U'
		86 -> return 0.3707f // = 'V'
		87 -> return 0.3128f // = 'W'
		88 -> return 0.4452f // = 'X'
		89 -> return 0.4495f // = 'Y'
		90 -> return 0.4129f // = 'Z'
		91 -> return 0.4762f // = '['
		92 -> return 0.3959f // = '\'
		93 -> return 0.4762f // = ']'
		94 -> return 0.3647f // = '^'
		95 -> return 0.0000f // = '_'
		96 -> return 0.1359f // = '`'
		97 -> return 0.2439f // = 'a'
		98 -> return 0.5729f // = 'b'
		99 -> return 0.2607f // = 'c'
		100 -> return 0.5729f // = 'd'
		101 -> return 0.3935f // = 'e'
		102 -> return 0.6232f // = 'f'
		103 -> return 0.3488f // = 'g'
		104 -> return 0.5744f // = 'h'
		105 -> return 0.3399f // = 'i'
		106 -> return 0.3399f // = 'j'
		107 -> return 0.5328f // = 'k'
		108 -> return 0.4762f // = 'l'
		109 -> return 0.3447f // = 'm'
		110 -> return 0.4062f // = 'n'
		111 -> return 0.2965f // = 'o'
		112 -> return 0.3511f // = 'p'
		113 -> return 0.3500f // = 'q'
		114 -> return 0.3057f // = 'r'
		115 -> return 0.2893f // = 's'
		116 -> return 0.5238f // = 't'
		117 -> return 0.3810f // = 'u'
		118 -> return 0.2458f // = 'v'
		119 -> return 0.4218f // = 'w'
		120 -> return 0.3266f // = 'x'
		121 -> return 0.3613f // = 'y'
		122 -> return 0.3417f // = 'z'
		123 -> return 0.4424f // = '{'
		124 -> return 0.3810f // = '|'
		125 -> return 0.4006f // = '}'
		126 -> return 0.0000f // = '~'
		else -> return 0.4f
	}
}
