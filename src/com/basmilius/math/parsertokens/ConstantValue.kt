/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object ConstantValue
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
object ConstantValue
{

	// ConstantValue - token type id
	val TYPE_ID = 9
	val TYPE_DESC = "Constant Value"

	// Math Constants
	val PI_ID = 1
	val EULER_ID = 2
	val EULER_MASCHERONI_ID = 3
	val GOLDEN_RATIO_ID = 4
	val PLASTIC_ID = 5
	val EMBREE_TREFETHEN_ID = 6
	val FEIGENBAUM_DELTA_ID = 7
	val FEIGENBAUM_ALFA_ID = 8
	val TWIN_PRIME_ID = 9
	val MEISSEL_MERTEENS_ID = 10
	val BRAUN_TWIN_PRIME_ID = 11
	val BRAUN_PRIME_QUADR_ID = 12
	val BRUIJN_NEWMAN_ID = 13
	val CATALAN_ID = 14
	val LANDAU_RAMANUJAN_ID = 15
	val VISWANATH_ID = 16
	val LEGENDRE_ID = 17
	val RAMANUJAN_SOLDNER_ID = 18
	val ERDOS_BORWEIN_ID = 19
	val BERNSTEIN_ID = 20
	val GAUSS_KUZMIN_WIRSING_ID = 21
	val HAFNER_SARNAK_MCCURLEY_ID = 22
	val GOLOMB_DICKMAN_ID = 23
	val CAHEN_ID = 24
	val LAPLACE_LIMIT_ID = 25
	val ALLADI_GRINSTEAD_ID = 26
	val LENGYEL_ID = 27
	val LEVY_ID = 28
	val APERY_ID = 29
	val MILLS_ID = 30
	val BACKHOUSE_ID = 31
	val PORTER_ID = 32
	val LIEB_QUARE_ICE_ID = 33
	val NIVEN_ID = 34
	val SIERPINSKI_ID = 35
	val KHINCHIN_ID = 36
	val FRANSEN_ROBINSON_ID = 37
	val LANDAU_ID = 38
	val PARABOLIC_ID = 39
	val OMEGA_ID = 40
	val MRB_ID = 41
	val LI2_ID = 42
	val GOMPERTZ_ID = 43

	// Physical Constants
	val LIGHT_SPEED_ID = 101
	val GRAVITATIONAL_CONSTANT_ID = 102
	val GRAVIT_ACC_EARTH_ID = 103
	val PLANCK_CONSTANT_ID = 104
	val PLANCK_CONSTANT_REDUCED_ID = 105
	val PLANCK_LENGTH_ID = 106
	val PLANCK_MASS_ID = 107
	val PLANCK_TIME_ID = 108

	// Astronomical Constants
	val LIGHT_YEAR_ID = 201
	val ASTRONOMICAL_UNIT_ID = 202
	val PARSEC_ID = 203
	val KILOPARSEC_ID = 204
	val EARTH_RADIUS_EQUATORIAL_ID = 205
	val EARTH_RADIUS_POLAR_ID = 206
	val EARTH_RADIUS_MEAN_ID = 207
	val EARTH_MASS_ID = 208
	val EARTH_SEMI_MAJOR_AXIS_ID = 209
	val MOON_RADIUS_MEAN_ID = 210
	val MOON_MASS_ID = 211
	val MONN_SEMI_MAJOR_AXIS_ID = 212
	val SOLAR_RADIUS_ID = 213
	val SOLAR_MASS_ID = 214
	val MERCURY_RADIUS_MEAN_ID = 215
	val MERCURY_MASS_ID = 216
	val MERCURY_SEMI_MAJOR_AXIS_ID = 217
	val VENUS_RADIUS_MEAN_ID = 218
	val VENUS_MASS_ID = 219
	val VENUS_SEMI_MAJOR_AXIS_ID = 220
	val MARS_RADIUS_MEAN_ID = 221
	val MARS_MASS_ID = 222
	val MARS_SEMI_MAJOR_AXIS_ID = 223
	val JUPITER_RADIUS_MEAN_ID = 224
	val JUPITER_MASS_ID = 225
	val JUPITER_SEMI_MAJOR_AXIS_ID = 226
	val SATURN_RADIUS_MEAN_ID = 227
	val SATURN_MASS_ID = 228
	val SATURN_SEMI_MAJOR_AXIS_ID = 229
	val URANUS_RADIUS_MEAN_ID = 230
	val URANUS_MASS_ID = 231
	val URANUS_SEMI_MAJOR_AXIS_ID = 232
	val NEPTUNE_RADIUS_MEAN_ID = 233
	val NEPTUNE_MASS_ID = 234
	val NEPTUNE_SEMI_MAJOR_AXIS_ID = 235

	// Boolean values
	val TRUE_ID = 301
	val FALSE_ID = 302

	// Other values
	val NAN_ID = 999
	val NaN = -1

	// ConstantValue - tokens key words.
	val PI_STR = "pi"
	val EULER_STR = "e"
	val EULER_MASCHERONI_STR = "[gam]"
	val GOLDEN_RATIO_STR = "[phi]"
	val PLASTIC_STR = "[PN]"
	val EMBREE_TREFETHEN_STR = "[B*]"
	val FEIGENBAUM_DELTA_STR = "[F'd]"
	val FEIGENBAUM_ALFA_STR = "[F'a]"
	val TWIN_PRIME_STR = "[C2]"
	val MEISSEL_MERTEENS_STR = "[M1]"
	val BRAUN_TWIN_PRIME_STR = "[B2]"
	val BRAUN_PRIME_QUADR_STR = "[B4]"
	val BRUIJN_NEWMAN_STR = "[BN'L]"
	val CATALAN_STR = "[Kat]"
	val LANDAU_RAMANUJAN_STR = "[K*]"
	val VISWANATH_STR = "[K.]"
	val LEGENDRE_STR = "[B'L]"
	val RAMANUJAN_SOLDNER_STR = "[RS'm]"
	val ERDOS_BORWEIN_STR = "[EB'e]"
	val BERNSTEIN_STR = "[Bern]"
	val GAUSS_KUZMIN_WIRSING_STR = "[GKW'l]"
	val HAFNER_SARNAK_MCCURLEY_STR = "[HSM's]"
	val GOLOMB_DICKMAN_STR = "[lm]"
	val CAHEN_STR = "[Cah]"
	val LAPLACE_LIMIT_STR = "[Ll]"
	val ALLADI_GRINSTEAD_STR = "[AG]"
	val LENGYEL_STR = "[L*]"
	val LEVY_STR = "[L.]"
	val APERY_STR = "[Dz3]"
	val MILLS_STR = "[A3n]"
	val BACKHOUSE_STR = "[Bh]"
	val PORTER_STR = "[Pt]"
	val LIEB_QUARE_ICE_STR = "[L2]"
	val NIVEN_STR = "[Nv]"
	val SIERPINSKI_STR = "[Ks]"
	val KHINCHIN_STR = "[Kh]"
	val FRANSEN_ROBINSON_STR = "[FR]"
	val LANDAU_STR = "[La]"
	val PARABOLIC_STR = "[P2]"
	val OMEGA_STR = "[Om]"
	val MRB_STR = "[MRB]"
	val LI2_STR = "[li2]"
	val GOMPERTZ_STR = "[EG]"

	// Physical Constants
	val LIGHT_SPEED_STR = "[c]"
	val GRAVITATIONAL_CONSTANT_STR = "[G.]"
	val GRAVIT_ACC_EARTH_STR = "[g]"
	val PLANCK_CONSTANT_STR = "[hP]"
	val PLANCK_CONSTANT_REDUCED_STR = "[h-]"
	val PLANCK_LENGTH_STR = "[lP]"
	val PLANCK_MASS_STR = "[mP]"
	val PLANCK_TIME_STR = "[tP]"

	// Astronomical Constants
	val LIGHT_YEAR_STR = "[ly]"
	val ASTRONOMICAL_UNIT_STR = "[au]"
	val PARSEC_STR = "[pc]"
	val KILOPARSEC_STR = "[kpc]"
	val EARTH_RADIUS_EQUATORIAL_STR = "[Earth-R-eq]"
	val EARTH_RADIUS_POLAR_STR = "[Earth-R-po]"
	val EARTH_RADIUS_MEAN_STR = "[Earth-R]"
	val EARTH_MASS_STR = "[Earth-M]"
	val EARTH_SEMI_MAJOR_AXIS_STR = "[Earth-D]"
	val MOON_RADIUS_MEAN_STR = "[Moon-R]"
	val MOON_MASS_STR = "[Moon-M]"
	val MONN_SEMI_MAJOR_AXIS_STR = "[Moon-D]"
	val SOLAR_RADIUS_STR = "[Solar-R]"
	val SOLAR_MASS_STR = "[Solar-M]"
	val MERCURY_RADIUS_MEAN_STR = "[Mercury-R]"
	val MERCURY_MASS_STR = "[Mercury-M]"
	val MERCURY_SEMI_MAJOR_AXIS_STR = "[Mercury-D]"
	val VENUS_RADIUS_MEAN_STR = "[Venus-R]"
	val VENUS_MASS_STR = "[Venus-M]"
	val VENUS_SEMI_MAJOR_AXIS_STR = "[Venus-D]"
	val MARS_RADIUS_MEAN_STR = "[Mars-R]"
	val MARS_MASS_STR = "[Mars-M]"
	val MARS_SEMI_MAJOR_AXIS_STR = "[Mars-D]"
	val JUPITER_RADIUS_MEAN_STR = "[Jupiter-R]"
	val JUPITER_MASS_STR = "[Jupiter-M]"
	val JUPITER_SEMI_MAJOR_AXIS_STR = "[Jupiter-D]"
	val SATURN_RADIUS_MEAN_STR = "[Saturn-R]"
	val SATURN_MASS_STR = "[Saturn-M]"
	val SATURN_SEMI_MAJOR_AXIS_STR = "[Saturn-D]"
	val URANUS_RADIUS_MEAN_STR = "[Uranus-R]"
	val URANUS_MASS_STR = "[Uranus-M]"
	val URANUS_SEMI_MAJOR_AXIS_STR = "[Uranus-D]"
	val NEPTUNE_RADIUS_MEAN_STR = "[Neptune-R]"
	val NEPTUNE_MASS_STR = "[Neptune-M]"
	val NEPTUNE_SEMI_MAJOR_AXIS_STR = "[Neptune-D]"

	// Boolean values
	val TRUE_STR = "[true]"
	val FALSE_STR = "[false]"

	// Other values
	val NAN_STR = "[NaN]"

	// ConstantValue - syntax.
	val PI_SYN = PI_STR
	val EULER_SYN = EULER_STR
	val EULER_MASCHERONI_SYN = EULER_MASCHERONI_STR
	val GOLDEN_RATIO_SYN = GOLDEN_RATIO_STR
	val PLASTIC_SYN = PLASTIC_STR
	val EMBREE_TREFETHEN_SYN = EMBREE_TREFETHEN_STR
	val FEIGENBAUM_DELTA_SYN = FEIGENBAUM_DELTA_STR
	val FEIGENBAUM_ALFA_SYN = FEIGENBAUM_ALFA_STR
	val TWIN_PRIME_SYN = TWIN_PRIME_STR
	val MEISSEL_MERTEENS_SYN = MEISSEL_MERTEENS_STR
	val BRAUN_TWIN_PRIME_SYN = BRAUN_TWIN_PRIME_STR
	val BRAUN_PRIME_QUADR_SYN = BRAUN_PRIME_QUADR_STR
	val BRUIJN_NEWMAN_SYN = BRUIJN_NEWMAN_STR
	val CATALAN_SYN = CATALAN_STR
	val LANDAU_RAMANUJAN_SYN = LANDAU_RAMANUJAN_STR
	val VISWANATH_SYN = VISWANATH_STR
	val LEGENDRE_SYN = LEGENDRE_STR
	val RAMANUJAN_SOLDNER_SYN = RAMANUJAN_SOLDNER_STR
	val ERDOS_BORWEIN_SYN = ERDOS_BORWEIN_STR
	val BERNSTEIN_SYN = BERNSTEIN_STR
	val GAUSS_KUZMIN_WIRSING_SYN = GAUSS_KUZMIN_WIRSING_STR
	val HAFNER_SARNAK_MCCURLEY_SYN = HAFNER_SARNAK_MCCURLEY_STR
	val GOLOMB_DICKMAN_SYN = GOLOMB_DICKMAN_STR
	val CAHEN_SYN = CAHEN_STR
	val LAPLACE_LIMIT_SYN = LAPLACE_LIMIT_STR
	val ALLADI_GRINSTEAD_SYN = ALLADI_GRINSTEAD_STR
	val LENGYEL_SYN = LENGYEL_STR
	val LEVY_SYN = LEVY_STR
	val APERY_SYN = APERY_STR
	val MILLS_SYN = MILLS_STR
	val BACKHOUSE_SYN = BACKHOUSE_STR
	val PORTER_SYN = PORTER_STR
	val LIEB_QUARE_ICE_SYN = LIEB_QUARE_ICE_STR
	val NIVEN_SYN = NIVEN_STR
	val SIERPINSKI_SYN = SIERPINSKI_STR
	val KHINCHIN_SYN = KHINCHIN_STR
	val FRANSEN_ROBINSON_SYN = FRANSEN_ROBINSON_STR
	val LANDAU_SYN = LANDAU_STR
	val PARABOLIC_SYN = PARABOLIC_STR
	val OMEGA_SYN = OMEGA_STR
	val MRB_SYN = MRB_STR
	val LI2_SYN = LI2_STR
	val GOMPERTZ_SYN = GOMPERTZ_STR

	// Physical Constants
	val LIGHT_SPEED_SYN = LIGHT_SPEED_STR
	val GRAVITATIONAL_CONSTANT_SYN = GRAVITATIONAL_CONSTANT_STR
	val GRAVIT_ACC_EARTH_SYN = GRAVIT_ACC_EARTH_STR
	val PLANCK_CONSTANT_SYN = PLANCK_CONSTANT_STR
	val PLANCK_CONSTANT_REDUCED_SYN = PLANCK_CONSTANT_REDUCED_STR
	val PLANCK_LENGTH_SYN = PLANCK_LENGTH_STR
	val PLANCK_MASS_SYN = PLANCK_MASS_STR
	val PLANCK_TIME_SYN = PLANCK_TIME_STR

	// Astronomical Constants
	val LIGHT_YEAR_SYN = LIGHT_YEAR_STR
	val ASTRONOMICAL_UNIT_SYN = ASTRONOMICAL_UNIT_STR
	val PARSEC_SYN = PARSEC_STR
	val KILOPARSEC_SYN = KILOPARSEC_STR
	val EARTH_RADIUS_EQUATORIAL_SYN = EARTH_RADIUS_EQUATORIAL_STR
	val EARTH_RADIUS_POLAR_SYN = EARTH_RADIUS_POLAR_STR
	val EARTH_RADIUS_MEAN_SYN = EARTH_RADIUS_MEAN_STR
	val EARTH_MASS_SYN = EARTH_MASS_STR
	val EARTH_SEMI_MAJOR_AXIS_SYN = EARTH_SEMI_MAJOR_AXIS_STR
	val MOON_RADIUS_MEAN_SYN = MOON_RADIUS_MEAN_STR
	val MOON_MASS_SYN = MOON_MASS_STR
	val MONN_SEMI_MAJOR_AXIS_SYN = MONN_SEMI_MAJOR_AXIS_STR
	val SOLAR_RADIUS_SYN = SOLAR_RADIUS_STR
	val SOLAR_MASS_SYN = SOLAR_MASS_STR
	val MERCURY_RADIUS_MEAN_SYN = MERCURY_RADIUS_MEAN_STR
	val MERCURY_MASS_SYN = MERCURY_MASS_STR
	val MERCURY_SEMI_MAJOR_AXIS_SYN = MERCURY_SEMI_MAJOR_AXIS_STR
	val VENUS_RADIUS_MEAN_SYN = VENUS_RADIUS_MEAN_STR
	val VENUS_MASS_SYN = VENUS_MASS_STR
	val VENUS_SEMI_MAJOR_AXIS_SYN = VENUS_SEMI_MAJOR_AXIS_STR
	val MARS_RADIUS_MEAN_SYN = MARS_RADIUS_MEAN_STR
	val MARS_MASS_SYN = MARS_MASS_STR
	val MARS_SEMI_MAJOR_AXIS_SYN = MARS_SEMI_MAJOR_AXIS_STR
	val JUPITER_RADIUS_MEAN_SYN = JUPITER_RADIUS_MEAN_STR
	val JUPITER_MASS_SYN = JUPITER_MASS_STR
	val JUPITER_SEMI_MAJOR_AXIS_SYN = JUPITER_SEMI_MAJOR_AXIS_STR
	val SATURN_RADIUS_MEAN_SYN = SATURN_RADIUS_MEAN_STR
	val SATURN_MASS_SYN = SATURN_MASS_STR
	val SATURN_SEMI_MAJOR_AXIS_SYN = SATURN_SEMI_MAJOR_AXIS_STR
	val URANUS_RADIUS_MEAN_SYN = URANUS_RADIUS_MEAN_STR
	val URANUS_MASS_SYN = URANUS_MASS_STR
	val URANUS_SEMI_MAJOR_AXIS_SYN = URANUS_SEMI_MAJOR_AXIS_STR
	val NEPTUNE_RADIUS_MEAN_SYN = NEPTUNE_RADIUS_MEAN_STR
	val NEPTUNE_MASS_SYN = NEPTUNE_MASS_STR
	val NEPTUNE_SEMI_MAJOR_AXIS_SYN = NEPTUNE_SEMI_MAJOR_AXIS_STR

	// Boolean values
	val TRUE_SYN = TRUE_STR
	val FALSE_SYN = FALSE_STR

	// Other values
	val NAN_SYN = NAN_STR

	// ConstantValue - tokens description.
	val PI_DESC = "Pi, Archimedes' constant or Ludolph's number"
	val EULER_DESC = "Napier's constant, or Euler's number, base of Natural logarithm"
	val EULER_MASCHERONI_DESC = "Euler-Mascheroni constant"
	val GOLDEN_RATIO_DESC = "Golden ratio"
	val PLASTIC_DESC = "Plastic constant"
	val EMBREE_TREFETHEN_DESC = "Embree-Trefethen constant"
	val FEIGENBAUM_DELTA_DESC = "Feigenbaum constant alfa"
	val FEIGENBAUM_ALFA_DESC = "Feigenbaum constant delta"
	val TWIN_PRIME_DESC = "Twin prime constant"
	val MEISSEL_MERTEENS_DESC = "Meissel-Mertens constant"
	val BRAUN_TWIN_PRIME_DESC = "Brun's constant for twin primes"
	val BRAUN_PRIME_QUADR_DESC = "Brun's constant for prime quadruplets"
	val BRUIJN_NEWMAN_DESC = "de Bruijn-Newman constant"
	val CATALAN_DESC = "Catalan's constant"
	val LANDAU_RAMANUJAN_DESC = "Landau-Ramanujan constant"
	val VISWANATH_DESC = "Viswanath's constant"
	val LEGENDRE_DESC = "Legendre's constant"
	val RAMANUJAN_SOLDNER_DESC = "Ramanujan-Soldner constant"
	val ERDOS_BORWEIN_DESC = "Erdos-Borwein constant"
	val BERNSTEIN_DESC = "Bernstein's constant"
	val GAUSS_KUZMIN_WIRSING_DESC = "Gauss-Kuzmin-Wirsing constant"
	val HAFNER_SARNAK_MCCURLEY_DESC = "Hafner-Sarnak-McCurley constant"
	val GOLOMB_DICKMAN_DESC = "Golomb-Dickman constant"
	val CAHEN_DESC = "Cahen's constant"
	val LAPLACE_LIMIT_DESC = "Laplace limit"
	val ALLADI_GRINSTEAD_DESC = "Alladi-Grinstead constant"
	val LENGYEL_DESC = "Lengyel's constant"
	val LEVY_DESC = "Levy's constant"
	val APERY_DESC = "Apery's constant"
	val MILLS_DESC = "Mills' constant"
	val BACKHOUSE_DESC = "Backhouse's constant"
	val PORTER_DESC = "Porter's constant"
	val LIEB_QUARE_ICE_DESC = "Lieb's square ice constant"
	val NIVEN_DESC = "Niven's constant"
	val SIERPINSKI_DESC = "Sierpinski's constant"
	val KHINCHIN_DESC = "Khinchin's constant"
	val FRANSEN_ROBINSON_DESC = "Fransen-Robinson constant"
	val LANDAU_DESC = "Landau's constant"
	val PARABOLIC_DESC = "Parabolic constant"
	val OMEGA_DESC = "Omega constant"
	val MRB_DESC = "MRB constant"
	val LI2_DESC = "li(2) - Logarithmic integral function at x=2"
	val GOMPERTZ_DESC = "Gompertz constant"

	// Physical Constants
	val LIGHT_SPEED_DESC = "<Physical Constant> Light speed in vacuum [m/s] (m=1, s=1)"
	val GRAVITATIONAL_CONSTANT_DESC = "<Physical Constant> Gravitational constant (m=1, kg=1, s=1)]"
	val GRAVIT_ACC_EARTH_DESC = "<Physical Constant> Gravitational acceleration on Earth [m/s^2] (m=1, s=1)"
	val PLANCK_CONSTANT_DESC = "<Physical Constant> Planck constant (m=1, kg=1, s=1)"
	val PLANCK_CONSTANT_REDUCED_DESC = "<Physical Constant> Reduced Planck constant / Dirac constant (m=1, kg=1, s=1)]"
	val PLANCK_LENGTH_DESC = "<Physical Constant> Planck length [m] (m=1)"
	val PLANCK_MASS_DESC = "<Physical Constant> Planck mass [kg] (kg=1)"
	val PLANCK_TIME_DESC = "<Physical Constant> Planck time [s] (s=1)"

	// Astronomical Constants
	val LIGHT_YEAR_DESC = "<Astronomical Constant> Light year [m] (m=1)"
	val ASTRONOMICAL_UNIT_DESC = "<Astronomical Constant> Astronomical unit [m] (m=1)"
	val PARSEC_DESC = "<Astronomical Constant> Parsec [m] (m=1)"
	val KILOPARSEC_DESC = "<Astronomical Constant> Kiloparsec [m] (m=1)"
	val EARTH_RADIUS_EQUATORIAL_DESC = "<Astronomical Constant> Earth equatorial radius [m] (m=1)"
	val EARTH_RADIUS_POLAR_DESC = "<Astronomical Constant> Earth polar radius [m] (m=1)"
	val EARTH_RADIUS_MEAN_DESC = "<Astronomical Constant> Earth mean radius (m=1)"
	val EARTH_MASS_DESC = "<Astronomical Constant> Earth mass [kg] (kg=1)"
	val EARTH_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Earth-Sun distance - semi major axis [m] (m=1)"
	val MOON_RADIUS_MEAN_DESC = "<Astronomical Constant> Moon mean radius [m] (m=1)"
	val MOON_MASS_DESC = "<Astronomical Constant> Moon mass [kg] (kg=1)"
	val MONN_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Moon-Earth distance - semi major axis [m] (m=1)"
	val SOLAR_RADIUS_DESC = "<Astronomical Constant> Solar mean radius [m] (m=1)"
	val SOLAR_MASS_DESC = "<Astronomical Constant> Solar mass [kg] (kg=1)"
	val MERCURY_RADIUS_MEAN_DESC = "<Astronomical Constant> Mercury mean radius [m] (m=1)"
	val MERCURY_MASS_DESC = "<Astronomical Constant> Mercury mass [kg] (kg=1)"
	val MERCURY_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Mercury-Sun distance - semi major axis [m] (m=1)"
	val VENUS_RADIUS_MEAN_DESC = "<Astronomical Constant> Venus mean radius [m] (m=1)"
	val VENUS_MASS_DESC = "<Astronomical Constant> Venus mass [kg] (kg=1)"
	val VENUS_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Venus-Sun distance - semi major axis [m] (m=1)"
	val MARS_RADIUS_MEAN_DESC = "<Astronomical Constant> Mars mean radius [m] (m=1)"
	val MARS_MASS_DESC = "<Astronomical Constant> Mars mass [kg] (kg=1)"
	val MARS_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Mars-Sun distance - semi major axis [m] (m=1)"
	val JUPITER_RADIUS_MEAN_DESC = "<Astronomical Constant> Jupiter mean radius [m] (m=1)"
	val JUPITER_MASS_DESC = "<Astronomical Constant> Jupiter mass [kg] (kg=1)"
	val JUPITER_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Jupiter-Sun distance - semi major axis [m] (m=1)"
	val SATURN_RADIUS_MEAN_DESC = "<Astronomical Constant> Saturn mean radius [m] (m=1)"
	val SATURN_MASS_DESC = "<Astronomical Constant> Saturn mass [kg] (kg=1)"
	val SATURN_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Saturn-Sun distance - semi major axis [m] (m=1)"
	val URANUS_RADIUS_MEAN_DESC = "<Astronomical Constant> Uranus mean radius [m] (m=1)"
	val URANUS_MASS_DESC = "<Astronomical Constant> Uranus mass [kg] (kg=1)"
	val URANUS_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Uranus-Sun distance - semi major axis [m] (m=1)"
	val NEPTUNE_RADIUS_MEAN_DESC = "<Astronomical Constant> Neptune mean radius [m] (m=1)"
	val NEPTUNE_MASS_DESC = "<Astronomical Constant> Neptune mass [kg] (kg=1)"
	val NEPTUNE_SEMI_MAJOR_AXIS_DESC = "<Astronomical Constant> Neptune-Sun distance - semi major axis [m] (m=1)"

	// Boolean values
	val TRUE_DESC = "Boolean True represented as double, [true] = 1"
	val FALSE_DESC = "Boolean False represented as double, [false] = 0"

	// Other values
	val NAN_DESC = "Not-a-Number"

	// ConstantValue - since.
	val PI_SINCE = MathParser.NAMEv10
	val EULER_SINCE = MathParser.NAMEv10
	val EULER_MASCHERONI_SINCE = MathParser.NAMEv10
	val GOLDEN_RATIO_SINCE = MathParser.NAMEv10
	val PLASTIC_SINCE = MathParser.NAMEv10
	val EMBREE_TREFETHEN_SINCE = MathParser.NAMEv10
	val FEIGENBAUM_DELTA_SINCE = MathParser.NAMEv10
	val FEIGENBAUM_ALFA_SINCE = MathParser.NAMEv10
	val TWIN_PRIME_SINCE = MathParser.NAMEv10
	val MEISSEL_MERTEENS_SINCE = MathParser.NAMEv10
	val BRAUN_TWIN_PRIME_SINCE = MathParser.NAMEv10
	val BRAUN_PRIME_QUADR_SINCE = MathParser.NAMEv10
	val BRUIJN_NEWMAN_SINCE = MathParser.NAMEv10
	val CATALAN_SINCE = MathParser.NAMEv10
	val LANDAU_RAMANUJAN_SINCE = MathParser.NAMEv10
	val VISWANATH_SINCE = MathParser.NAMEv10
	val LEGENDRE_SINCE = MathParser.NAMEv10
	val RAMANUJAN_SOLDNER_SINCE = MathParser.NAMEv10
	val ERDOS_BORWEIN_SINCE = MathParser.NAMEv10
	val BERNSTEIN_SINCE = MathParser.NAMEv10
	val GAUSS_KUZMIN_WIRSING_SINCE = MathParser.NAMEv10
	val HAFNER_SARNAK_MCCURLEY_SINCE = MathParser.NAMEv10
	val GOLOMB_DICKMAN_SINCE = MathParser.NAMEv10
	val CAHEN_SINCE = MathParser.NAMEv10
	val LAPLACE_LIMIT_SINCE = MathParser.NAMEv10
	val ALLADI_GRINSTEAD_SINCE = MathParser.NAMEv10
	val LENGYEL_SINCE = MathParser.NAMEv10
	val LEVY_SINCE = MathParser.NAMEv10
	val APERY_SINCE = MathParser.NAMEv10
	val MILLS_SINCE = MathParser.NAMEv10
	val BACKHOUSE_SINCE = MathParser.NAMEv10
	val PORTER_SINCE = MathParser.NAMEv10
	val LIEB_QUARE_ICE_SINCE = MathParser.NAMEv10
	val NIVEN_SINCE = MathParser.NAMEv10
	val SIERPINSKI_SINCE = MathParser.NAMEv10
	val KHINCHIN_SINCE = MathParser.NAMEv10
	val FRANSEN_ROBINSON_SINCE = MathParser.NAMEv10
	val LANDAU_SINCE = MathParser.NAMEv10
	val PARABOLIC_SINCE = MathParser.NAMEv10
	val OMEGA_SINCE = MathParser.NAMEv10
	val MRB_SINCE = MathParser.NAMEv10
	val LI2_SINCE = MathParser.NAMEv23
	val GOMPERTZ_SINCE = MathParser.NAMEv23

	// Physical Constants
	val LIGHT_SPEED_SINCE = MathParser.NAMEv40
	val GRAVITATIONAL_CONSTANT_SINCE = MathParser.NAMEv40
	val GRAVIT_ACC_EARTH_SINCE = MathParser.NAMEv40
	val PLANCK_CONSTANT_SINCE = MathParser.NAMEv40
	val PLANCK_CONSTANT_REDUCED_SINCE = MathParser.NAMEv40
	val PLANCK_LENGTH_SINCE = MathParser.NAMEv40
	val PLANCK_MASS_SINCE = MathParser.NAMEv40
	val PLANCK_TIME_SINCE = MathParser.NAMEv40

	// Astronomical Constants
	val LIGHT_YEAR_SINCE = MathParser.NAMEv40
	val ASTRONOMICAL_UNIT_SINCE = MathParser.NAMEv40
	val PARSEC_SINCE = MathParser.NAMEv40
	val KILOPARSEC_SINCE = MathParser.NAMEv40
	val EARTH_RADIUS_EQUATORIAL_SINCE = MathParser.NAMEv40
	val EARTH_RADIUS_POLAR_SINCE = MathParser.NAMEv40
	val EARTH_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val EARTH_MASS_SINCE = MathParser.NAMEv40
	val EARTH_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val MOON_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val MOON_MASS_SINCE = MathParser.NAMEv40
	val MONN_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val SOLAR_RADIUS_SINCE = MathParser.NAMEv40
	val SOLAR_MASS_SINCE = MathParser.NAMEv40
	val MERCURY_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val MERCURY_MASS_SINCE = MathParser.NAMEv40
	val MERCURY_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val VENUS_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val VENUS_MASS_SINCE = MathParser.NAMEv40
	val VENUS_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val MARS_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val MARS_MASS_SINCE = MathParser.NAMEv40
	val MARS_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val JUPITER_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val JUPITER_MASS_SINCE = MathParser.NAMEv40
	val JUPITER_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val SATURN_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val SATURN_MASS_SINCE = MathParser.NAMEv40
	val SATURN_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val URANUS_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val URANUS_MASS_SINCE = MathParser.NAMEv40
	val URANUS_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40
	val NEPTUNE_RADIUS_MEAN_SINCE = MathParser.NAMEv40
	val NEPTUNE_MASS_SINCE = MathParser.NAMEv40
	val NEPTUNE_SEMI_MAJOR_AXIS_SINCE = MathParser.NAMEv40

	// Boolean values
	val TRUE_SINCE = MathParser.NAMEv41
	val FALSE_SINCE = MathParser.NAMEv41

	// Other values
	val NAN_SINCE = MathParser.NAMEv41
}
