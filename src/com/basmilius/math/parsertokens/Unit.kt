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
 * Object Unit
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
object Unit
{

	// Unit - token type id.
	val TYPE_ID = 12
	val TYPE_DESC = "Unit"

	// Unit - tokens id.
	val PERC_ID = 1
	val PROMIL_ID = 2
	val YOTTA_ID = 101
	val ZETTA_ID = 102
	val EXA_ID = 103
	val PETA_ID = 104
	val TERA_ID = 105
	val GIGA_ID = 106
	val MEGA_ID = 107
	val KILO_ID = 108
	val HECTO_ID = 109
	val DECA_ID = 110
	val DECI_ID = 111
	val CENTI_ID = 112
	val MILLI_ID = 113
	val MICRO_ID = 114
	val NANO_ID = 115
	val PICO_ID = 116
	val FEMTO_ID = 117
	val ATTO_ID = 118
	val ZEPTO_ID = 119
	val YOCTO_ID = 120
	val METRE_ID = 201
	val KILOMETRE_ID = 202
	val CENTIMETRE_ID = 203
	val MILLIMETRE_ID = 204
	val INCH_ID = 205
	val YARD_ID = 206
	val FEET_ID = 207
	val MILE_ID = 208
	val NAUTICAL_MILE_ID = 209
	val METRE2_ID = 301
	val CENTIMETRE2_ID = 302
	val MILLIMETRE2_ID = 303
	val ARE_ID = 304
	val HECTARE_ID = 305
	val ACRE_ID = 306
	val KILOMETRE2_ID = 307
	val MILLIMETRE3_ID = 401
	val CENTIMETRE3_ID = 402
	val METRE3_ID = 403
	val KILOMETRE3_ID = 404
	val MILLILITRE_ID = 405
	val LITRE_ID = 406
	val GALLON_ID = 407
	val PINT_ID = 408
	val SECOND_ID = 501
	val MILLISECOND_ID = 502
	val MINUTE_ID = 503
	val HOUR_ID = 504
	val DAY_ID = 505
	val WEEK_ID = 506
	val JULIAN_YEAR_ID = 507
	val KILOGRAM_ID = 508
	val GRAM_ID = 509
	val MILLIGRAM_ID = 510
	val DECAGRAM_ID = 511
	val TONNE_ID = 512
	val OUNCE_ID = 513
	val POUND_ID = 514
	val BIT_ID = 601
	val KILOBIT_ID = 602
	val MEGABIT_ID = 603
	val GIGABIT_ID = 604
	val TERABIT_ID = 605
	val PETABIT_ID = 606
	val EXABIT_ID = 607
	val ZETTABIT_ID = 608
	val YOTTABIT_ID = 609
	val BYTE_ID = 610
	val KILOBYTE_ID = 611
	val MEGABYTE_ID = 612
	val GIGABYTE_ID = 613
	val TERABYTE_ID = 614
	val PETABYTE_ID = 615
	val EXABYTE_ID = 616
	val ZETTABYTE_ID = 617
	val YOTTABYTE_ID = 618
	val JOULE_ID = 701
	val ELECTRONO_VOLT_ID = 702
	val KILO_ELECTRONO_VOLT_ID = 703
	val MEGA_ELECTRONO_VOLT_ID = 704
	val GIGA_ELECTRONO_VOLT_ID = 705
	val TERA_ELECTRONO_VOLT_ID = 706
	val METRE_PER_SECOND_ID = 801
	val KILOMETRE_PER_HOUR_ID = 802
	val MILE_PER_HOUR_ID = 803
	val KNOT_ID = 804
	val METRE_PER_SECOND2_ID = 901
	val KILOMETRE_PER_HOUR2_ID = 902
	val MILE_PER_HOUR2_ID = 903
	val RADIAN_ARC_ID = 1001
	val DEGREE_ARC_ID = 1002
	val MINUTE_ARC_ID = 1003
	val SECOND_ARC_ID = 1004

	// Unit - tokens key words.
	val PERC_STR = "[%]"
	val PROMIL_STR = "[%%]"
	val YOTTA_STR = "[Y]"
	val YOTTA_SEPT_STR = "[sept]"
	val ZETTA_STR = "[Z]"
	val ZETTA_SEXT_STR = "[sext]"
	val EXA_STR = "[E]"
	val EXA_QUINT_STR = "[quint]"
	val PETA_STR = "[P]"
	val PETA_QUAD_STR = "[quad]"
	val TERA_STR = "[T]"
	val TERA_TRIL_STR = "[tril]"
	val GIGA_STR = "[G]"
	val GIGA_BIL_STR = "[bil]"
	val MEGA_STR = "[M]"
	val MEGA_MIL_STR = "[mil]"
	val KILO_STR = "[k]"
	val KILO_TH_STR = "[th]"
	val HECTO_STR = "[hecto]"
	val HECTO_HUND_STR = "[hund]"
	val DECA_STR = "[deca]"
	val DECA_TEN_STR = "[ten]"
	val DECI_STR = "[deci]"
	val CENTI_STR = "[centi]"
	val MILLI_STR = "[milli]"
	val MICRO_STR = "[mic]"
	val NANO_STR = "[n]"
	val PICO_STR = "[p]"
	val FEMTO_STR = "[f]"
	val ATTO_STR = "[a]"
	val ZEPTO_STR = "[z]"
	val YOCTO_STR = "[y]"
	val METRE_STR = "[m]"
	val KILOMETRE_STR = "[km]"
	val CENTIMETRE_STR = "[cm]"
	val MILLIMETRE_STR = "[mm]"
	val INCH_STR = "[inch]"
	val YARD_STR = "[yd]"
	val FEET_STR = "[ft]"
	val MILE_STR = "[mile]"
	val NAUTICAL_MILE_STR = "[nmi]"
	val METRE2_STR = "[m2]"
	val CENTIMETRE2_STR = "[cm2]"
	val MILLIMETRE2_STR = "[mm2]"
	val ARE_STR = "[are]"
	val HECTARE_STR = "[ha]"
	val ACRE_STR = "[acre]"
	val KILOMETRE2_STR = "[km2]"
	val MILLIMETRE3_STR = "[mm3]"
	val CENTIMETRE3_STR = "[cm3]"
	val METRE3_STR = "[m3]"
	val KILOMETRE3_STR = "[km3]"
	val MILLILITRE_STR = "[ml]"
	val LITRE_STR = "[l]"
	val GALLON_STR = "[gall]"
	val PINT_STR = "[pint]"
	val SECOND_STR = "[s]"
	val MILLISECOND_STR = "[ms]"
	val MINUTE_STR = "[min]"
	val HOUR_STR = "[h]"
	val DAY_STR = "[day]"
	val WEEK_STR = "[week]"
	val JULIAN_YEAR_STR = "[yearj]"
	val KILOGRAM_STR = "[kg]"
	val GRAM_STR = "[gr]"
	val MILLIGRAM_STR = "[mg]"
	val DECAGRAM_STR = "[dag]"
	val TONNE_STR = "[t]"
	val OUNCE_STR = "[oz]"
	val POUND_STR = "[lb]"
	val BIT_STR = "[b]"
	val KILOBIT_STR = "[kb]"
	val MEGABIT_STR = "[Mb]"
	val GIGABIT_STR = "[Gb]"
	val TERABIT_STR = "[Tb]"
	val PETABIT_STR = "[Pb]"
	val EXABIT_STR = "[Eb]"
	val ZETTABIT_STR = "[Zb]"
	val YOTTABIT_STR = "[Yb]"
	val BYTE_STR = "[B]"
	val KILOBYTE_STR = "[kB]"
	val MEGABYTE_STR = "[MB]"
	val GIGABYTE_STR = "[GB]"
	val TERABYTE_STR = "[TB]"
	val PETABYTE_STR = "[PB]"
	val EXABYTE_STR = "[EB]"
	val ZETTABYTE_STR = "[ZB]"
	val YOTTABYTE_STR = "[YB]"
	val JOULE_STR = "[J]"
	val ELECTRONO_VOLT_STR = "[eV]"
	val KILO_ELECTRONO_VOLT_STR = "[keV]"
	val MEGA_ELECTRONO_VOLT_STR = "[MeV]"
	val GIGA_ELECTRONO_VOLT_STR = "[GeV]"
	val TERA_ELECTRONO_VOLT_STR = "[TeV]"
	val METRE_PER_SECOND_STR = "[m/s]"
	val KILOMETRE_PER_HOUR_STR = "[km/h]"
	val MILE_PER_HOUR_STR = "[mi/h]"
	val KNOT_STR = "[knot]"
	val METRE_PER_SECOND2_STR = "[m/s2]"
	val KILOMETRE_PER_HOUR2_STR = "[km/h2]"
	val MILE_PER_HOUR2_STR = "[mi/h2]"
	val RADIAN_ARC_STR = "[rad]"
	val DEGREE_ARC_STR = "[deg]"
	val MINUTE_ARC_STR = "[']"
	val SECOND_ARC_STR = "['']"

	// Unit - syntax.
	val PERC_SYN = PERC_STR
	val PROMIL_SYN = PROMIL_STR
	val YOTTA_SYN = YOTTA_STR
	val YOTTA_SEPT_SYN = YOTTA_SEPT_STR
	val ZETTA_SYN = ZETTA_STR
	val ZETTA_SEXT_SYN = ZETTA_SEXT_STR
	val EXA_SYN = EXA_STR
	val EXA_QUINT_SYN = EXA_QUINT_STR
	val PETA_SYN = PETA_STR
	val PETA_QUAD_SYN = PETA_QUAD_STR
	val TERA_SYN = TERA_STR
	val TERA_TRIL_SYN = TERA_TRIL_STR
	val GIGA_SYN = GIGA_STR
	val GIGA_BIL_SYN = GIGA_BIL_STR
	val MEGA_SYN = MEGA_STR
	val MEGA_MIL_SYN = MEGA_MIL_STR
	val KILO_SYN = KILO_STR
	val KILO_TH_SYN = KILO_TH_STR
	val HECTO_SYN = HECTO_STR
	val HECTO_HUND_SYN = HECTO_HUND_STR
	val DECA_SYN = DECA_STR
	val DECA_TEN_SYN = DECA_TEN_STR
	val DECI_SYN = DECI_STR
	val CENTI_SYN = CENTI_STR
	val MILLI_SYN = MILLI_STR
	val MICRO_SYN = MICRO_STR
	val NANO_SYN = NANO_STR
	val PICO_SYN = PICO_STR
	val FEMTO_SYN = FEMTO_STR
	val ATTO_SYN = ATTO_STR
	val ZEPTO_SYN = ZEPTO_STR
	val YOCTO_SYN = YOCTO_STR
	val METRE_SYN = METRE_STR
	val KILOMETRE_SYN = KILOMETRE_STR
	val CENTIMETRE_SYN = CENTIMETRE_STR
	val MILLIMETRE_SYN = MILLIMETRE_STR
	val INCH_SYN = INCH_STR
	val YARD_SYN = YARD_STR
	val FEET_SYN = FEET_STR
	val MILE_SYN = MILE_STR
	val NAUTICAL_MILE_SYN = NAUTICAL_MILE_STR
	val METRE2_SYN = METRE2_STR
	val CENTIMETRE2_SYN = CENTIMETRE2_STR
	val MILLIMETRE2_SYN = MILLIMETRE2_STR
	val ARE_SYN = ARE_STR
	val HECTARE_SYN = HECTARE_STR
	val ACRE_SYN = ACRE_STR
	val KILOMETRE2_SYN = KILOMETRE2_STR
	val MILLIMETRE3_SYN = MILLIMETRE3_STR
	val CENTIMETRE3_SYN = CENTIMETRE3_STR
	val METRE3_SYN = METRE3_STR
	val KILOMETRE3_SYN = KILOMETRE3_STR
	val MILLILITRE_SYN = MILLILITRE_STR
	val LITRE_SYN = LITRE_STR
	val GALLON_SYN = GALLON_STR
	val PINT_SYN = PINT_STR
	val SECOND_SYN = SECOND_STR
	val MILLISECOND_SYN = MILLISECOND_STR
	val MINUTE_SYN = MINUTE_STR
	val HOUR_SYN = HOUR_STR
	val DAY_SYN = DAY_STR
	val WEEK_SYN = WEEK_STR
	val JULIAN_YEAR_SYN = JULIAN_YEAR_STR
	val KILOGRAM_SYN = KILOGRAM_STR
	val GRAM_SYN = GRAM_STR
	val MILLIGRAM_SYN = MILLIGRAM_STR
	val DECAGRAM_SYN = DECAGRAM_STR
	val TONNE_SYN = TONNE_STR
	val OUNCE_SYN = OUNCE_STR
	val POUND_SYN = POUND_STR
	val BIT_SYN = BIT_STR
	val KILOBIT_SYN = KILOBIT_STR
	val MEGABIT_SYN = MEGABIT_STR
	val GIGABIT_SYN = GIGABIT_STR
	val TERABIT_SYN = TERABIT_STR
	val PETABIT_SYN = PETABIT_STR
	val EXABIT_SYN = EXABIT_STR
	val ZETTABIT_SYN = ZETTABIT_STR
	val YOTTABIT_SYN = YOTTABIT_STR
	val BYTE_SYN = BYTE_STR
	val KILOBYTE_SYN = KILOBYTE_STR
	val MEGABYTE_SYN = MEGABYTE_STR
	val GIGABYTE_SYN = GIGABYTE_STR
	val TERABYTE_SYN = TERABYTE_STR
	val PETABYTE_SYN = PETABYTE_STR
	val EXABYTE_SYN = EXABYTE_STR
	val ZETTABYTE_SYN = ZETTABYTE_STR
	val YOTTABYTE_SYN = YOTTABYTE_STR
	val JOULE_SYN = JOULE_STR
	val ELECTRONO_VOLT_SYN = ELECTRONO_VOLT_STR
	val KILO_ELECTRONO_VOLT_SYN = KILO_ELECTRONO_VOLT_STR
	val MEGA_ELECTRONO_VOLT_SYN = MEGA_ELECTRONO_VOLT_STR
	val GIGA_ELECTRONO_VOLT_SYN = GIGA_ELECTRONO_VOLT_STR
	val TERA_ELECTRONO_VOLT_SYN = TERA_ELECTRONO_VOLT_STR
	val METRE_PER_SECOND_SYN = METRE_PER_SECOND_STR
	val KILOMETRE_PER_HOUR_SYN = KILOMETRE_PER_HOUR_STR
	val MILE_PER_HOUR_SYN = MILE_PER_HOUR_STR
	val KNOT_SYN = KNOT_STR
	val METRE_PER_SECOND2_SYN = METRE_PER_SECOND2_STR
	val KILOMETRE_PER_HOUR2_SYN = KILOMETRE_PER_HOUR2_STR
	val MILE_PER_HOUR2_SYN = MILE_PER_HOUR2_STR
	val RADIAN_ARC_SYN = RADIAN_ARC_STR
	val DEGREE_ARC_SYN = DEGREE_ARC_STR
	val MINUTE_ARC_SYN = MINUTE_ARC_STR
	val SECOND_ARC_SYN = SECOND_ARC_STR

	// Unit - tokens description.
	val PERC_DESC = "<Ratio, Fraction> Percentage = 0.01"
	val PROMIL_DESC = "<Ratio, Fraction> Promil, Per mille = 0.001"
	val YOTTA_DESC = "<Metric prefix> Septillion / Yotta = 10^24"
	val ZETTA_DESC = "<Metric prefix> Sextillion / Zetta = 10^21"
	val EXA_DESC = "<Metric prefix> Quintillion / Exa = 10^18"
	val PETA_DESC = "<Metric prefix> Quadrillion / Peta = 10^15"
	val TERA_DESC = "<Metric prefix> Trillion / Tera = 10^12"
	val GIGA_DESC = "<Metric prefix> Billion / Giga = 10^9"
	val MEGA_DESC = "<Metric prefix> Million / Mega = 10^6"
	val KILO_DESC = "<Metric prefix> Thousand / Kilo = 10^3"
	val HECTO_DESC = "<Metric prefix> Hundred / Hecto = 10^2"
	val DECA_DESC = "<Metric prefix> Ten / Deca = 10"
	val DECI_DESC = "<Metric prefix> Tenth / Deci = 0.1"
	val CENTI_DESC = "<Metric prefix> Hundredth / Centi = 0.01"
	val MILLI_DESC = "<Metric prefix> Thousandth / Milli = 0.001"
	val MICRO_DESC = "<Metric prefix> Millionth / Micro = 10^-6"
	val NANO_DESC = "<Metric prefix> Billionth / Nano = 10^-9"
	val PICO_DESC = "<Metric prefix> Trillionth / Pico = 10^-12"
	val FEMTO_DESC = "<Metric prefix> Quadrillionth / Femto = 10^-15"
	val ATTO_DESC = "<Metric prefix> Quintillionth / Atoo = 10^-18"
	val ZEPTO_DESC = "<Metric prefix> Sextillionth / Zepto = 10^-21"
	val YOCTO_DESC = "<Metric prefix> Septillionth / Yocto = 10^-24"
	val METRE_DESC = "<Unit of length> Metre / Meter (m=1)"
	val KILOMETRE_DESC = "<Unit of length> Kilometre / Kilometer (m=1)"
	val CENTIMETRE_DESC = "<Unit of length> Centimetre / Centimeter (m=1)"
	val MILLIMETRE_DESC = "<Unit of length> Millimetre / Millimeter (m=1)"
	val INCH_DESC = "<Unit of length> Inch (m=1)"
	val YARD_DESC = "<Unit of length> Yard (m=1)"
	val FEET_DESC = "<Unit of length> Feet (m=1)"
	val MILE_DESC = "<Unit of length> Mile (m=1)"
	val NAUTICAL_MILE_DESC = "<Unit of length> Nautical mile (m=1)"
	val METRE2_DESC = "<Unit of area> Square metre / Square meter (m=1)"
	val CENTIMETRE2_DESC = "<Unit of area> Square centimetre / Square centimeter (m=1)"
	val MILLIMETRE2_DESC = "<Unit of area> Square millimetre / Square millimeter (m=1)"
	val ARE_DESC = "<Unit of area> Are (m=1)"
	val HECTARE_DESC = "<Unit of area> Hectare (m=1)"
	val ACRE_DESC = "<Unit of area> Acre (m=1)"
	val KILOMETRE2_DESC = "<Unit of area> Square kilometre / Square kilometer (m=1)"
	val MILLIMETRE3_DESC = "<Unit of volume> Cubic millimetre / Cubic millimeter (m=1)"
	val CENTIMETRE3_DESC = "<Unit of volume> Cubic centimetre / Cubic centimeter (m=1)"
	val METRE3_DESC = "<Unit of volume> Cubic metre / Cubic meter (m=1)"
	val KILOMETRE3_DESC = "<Unit of volume> Cubic kilometre / Cubic kilometer (m=1)"
	val MILLILITRE_DESC = "<Unit of volume> Millilitre / Milliliter (m=1)"
	val LITRE_DESC = "<Unit of volume> Litre / Liter (m=1)"
	val GALLON_DESC = "<Unit of volume> Gallon (m=1)"
	val PINT_DESC = "<Unit of volume> Pint (m=1)"
	val SECOND_DESC = "<Unit of time> Second (s=1)"
	val MILLISECOND_DESC = "<Unit of time> Millisecond (s=1)"
	val MINUTE_DESC = "<Unit of time> Minute (s=1)"
	val HOUR_DESC = "<Unit of time> Hour (s=1)"
	val DAY_DESC = "<Unit of time> Day (s=1)"
	val WEEK_DESC = "<Unit of time> Week (s=1)"
	val JULIAN_YEAR_DESC = "<Unit of time> Julian year = 365.25 days (s=1)"
	val KILOGRAM_DESC = "<Unit of mass> Kilogram (kg=1)"
	val GRAM_DESC = "<Unit of mass> Gram (kg=1)"
	val MILLIGRAM_DESC = "<Unit of mass> Milligram (kg=1)"
	val DECAGRAM_DESC = "<Unit of mass> Decagram (kg=1)"
	val TONNE_DESC = "<Unit of mass> Tonne (kg=1)"
	val OUNCE_DESC = "<Unit of mass> Ounce (kg=1)"
	val POUND_DESC = "<Unit of mass> Pound (kg=1)"
	val BIT_DESC = "<Unit of information> Bit (bit=1)"
	val KILOBIT_DESC = "<Unit of information> Kilobit (bit=1)"
	val MEGABIT_DESC = "<Unit of information> Megabit (bit=1)"
	val GIGABIT_DESC = "<Unit of information> Gigabit (bit=1)"
	val TERABIT_DESC = "<Unit of information> Terabit (bit=1)"
	val PETABIT_DESC = "<Unit of information> Petabit (bit=1)"
	val EXABIT_DESC = "<Unit of information> Exabit (bit=1)"
	val ZETTABIT_DESC = "<Unit of information> Zettabit (bit=1)"
	val YOTTABIT_DESC = "<Unit of information> Yottabit (bit=1)"
	val BYTE_DESC = "<Unit of information> Byte (bit=1)"
	val KILOBYTE_DESC = "<Unit of information> Kilobyte (bit=1)"
	val MEGABYTE_DESC = "<Unit of information> Megabyte (bit=1)"
	val GIGABYTE_DESC = "<Unit of information> Gigabyte (bit=1)"
	val TERABYTE_DESC = "<Unit of information> Terabyte (bit=1)"
	val PETABYTE_DESC = "<Unit of information> Petabyte (bit=1)"
	val EXABYTE_DESC = "<Unit of information> Exabyte (bit=1)"
	val ZETTABYTE_DESC = "<Unit of information> Zettabyte (bit=1)"
	val YOTTABYTE_DESC = "<Unit of information> Yottabyte (bit=1)"
	val JOULE_DESC = "<Unit of energy> Joule (m=1, kg=1, s=1)"
	val ELECTRONO_VOLT_DESC = "<Unit of energy> Electronovolt (m=1, kg=1, s=1)"
	val KILO_ELECTRONO_VOLT_DESC = "<Unit of energy> Kiloelectronovolt (m=1, kg=1, s=1)"
	val MEGA_ELECTRONO_VOLT_DESC = "<Unit of energy> Megaelectronovolt (m=1, kg=1, s=1)"
	val GIGA_ELECTRONO_VOLT_DESC = "<Unit of energy> Gigaelectronovolt (m=1, kg=1, s=1)"
	val TERA_ELECTRONO_VOLT_DESC = "<Unit of energy> Teraelectronovolt (m=1, kg=1, s=1)"
	val METRE_PER_SECOND_DESC = "<Unit of speed> Metre / Meter per second (m=1, s=1)"
	val KILOMETRE_PER_HOUR_DESC = "<Unit of speed> Kilometre / Kilometer per hour (m=1, s=1)"
	val MILE_PER_HOUR_DESC = "<Unit of speed> Mile per hour (m=1, s=1)"
	val KNOT_DESC = "<Unit of speed> Knot (m=1, s=1)"
	val METRE_PER_SECOND2_DESC = "<Unit of acceleration> Metre / Meter per square second (m=1, s=1)"
	val KILOMETRE_PER_HOUR2_DESC = "<Unit of acceleration> Kilometre / Kilometer per square hour (m=1, s=1)"
	val MILE_PER_HOUR2_DESC = "<Unit of acceleration> Mile per square hour (m=1, s=1)"
	val RADIAN_ARC_DESC = "<Unit of angle> Radian (rad=1)"
	val DEGREE_ARC_DESC = "<Unit of angle> Degree of arc (rad=1)"
	val MINUTE_ARC_DESC = "<Unit of angle> Minute of arc (rad=1)"
	val SECOND_ARC_DESC = "<Unit of angle> Second of arc (rad=1)"

	// Unit - since.
	val PERC_SINCE = MathParser.NAMEv40
	val PROMIL_SINCE = MathParser.NAMEv40
	val YOTTA_SINCE = MathParser.NAMEv40
	val YOTTA_SEPT_SINCE = MathParser.NAMEv40
	val ZETTA_SINCE = MathParser.NAMEv40
	val ZETTA_SEXT_SINCE = MathParser.NAMEv40
	val EXA_SINCE = MathParser.NAMEv40
	val EXA_QUINT_SINCE = MathParser.NAMEv40
	val PETA_SINCE = MathParser.NAMEv40
	val PETA_QUAD_SINCE = MathParser.NAMEv40
	val TERA_SINCE = MathParser.NAMEv40
	val TERA_TRIL_SINCE = MathParser.NAMEv40
	val GIGA_SINCE = MathParser.NAMEv40
	val GIGA_BIL_SINCE = MathParser.NAMEv40
	val MEGA_SINCE = MathParser.NAMEv40
	val MEGA_MIL_SINCE = MathParser.NAMEv40
	val KILO_SINCE = MathParser.NAMEv40
	val KILO_TH_SINCE = MathParser.NAMEv40
	val HECTO_SINCE = MathParser.NAMEv40
	val HECTO_HUND_SINCE = MathParser.NAMEv40
	val DECA_SINCE = MathParser.NAMEv40
	val DECA_TEN_SINCE = MathParser.NAMEv40
	val DECI_SINCE = MathParser.NAMEv40
	val CENTI_SINCE = MathParser.NAMEv40
	val MILLI_SINCE = MathParser.NAMEv40
	val MICRO_SINCE = MathParser.NAMEv40
	val NANO_SINCE = MathParser.NAMEv40
	val PICO_SINCE = MathParser.NAMEv40
	val FEMTO_SINCE = MathParser.NAMEv40
	val ATTO_SINCE = MathParser.NAMEv40
	val ZEPTO_SINCE = MathParser.NAMEv40
	val YOCTO_SINCE = MathParser.NAMEv40
	val METRE_SINCE = MathParser.NAMEv40
	val KILOMETRE_SINCE = MathParser.NAMEv40
	val CENTIMETRE_SINCE = MathParser.NAMEv40
	val MILLIMETRE_SINCE = MathParser.NAMEv40
	val INCH_SINCE = MathParser.NAMEv40
	val YARD_SINCE = MathParser.NAMEv40
	val FEET_SINCE = MathParser.NAMEv40
	val MILE_SINCE = MathParser.NAMEv40
	val NAUTICAL_MILE_SINCE = MathParser.NAMEv40
	val METRE2_SINCE = MathParser.NAMEv40
	val CENTIMETRE2_SINCE = MathParser.NAMEv40
	val MILLIMETRE2_SINCE = MathParser.NAMEv40
	val ARE_SINCE = MathParser.NAMEv40
	val HECTARE_SINCE = MathParser.NAMEv40
	val ACRE_SINCE = MathParser.NAMEv40
	val KILOMETRE2_SINCE = MathParser.NAMEv40
	val MILLIMETRE3_SINCE = MathParser.NAMEv40
	val CENTIMETRE3_SINCE = MathParser.NAMEv40
	val METRE3_SINCE = MathParser.NAMEv40
	val KILOMETRE3_SINCE = MathParser.NAMEv40
	val MILLILITRE_SINCE = MathParser.NAMEv40
	val LITRE_SINCE = MathParser.NAMEv40
	val GALLON_SINCE = MathParser.NAMEv40
	val PINT_SINCE = MathParser.NAMEv40
	val SECOND_SINCE = MathParser.NAMEv40
	val MILLISECOND_SINCE = MathParser.NAMEv40
	val MINUTE_SINCE = MathParser.NAMEv40
	val HOUR_SINCE = MathParser.NAMEv40
	val DAY_SINCE = MathParser.NAMEv40
	val WEEK_SINCE = MathParser.NAMEv40
	val JULIAN_YEAR_SINCE = MathParser.NAMEv40
	val KILOGRAM_SINCE = MathParser.NAMEv40
	val GRAM_SINCE = MathParser.NAMEv40
	val MILLIGRAM_SINCE = MathParser.NAMEv40
	val DECAGRAM_SINCE = MathParser.NAMEv40
	val TONNE_SINCE = MathParser.NAMEv40
	val OUNCE_SINCE = MathParser.NAMEv40
	val POUND_SINCE = MathParser.NAMEv40
	val BIT_SINCE = MathParser.NAMEv40
	val KILOBIT_SINCE = MathParser.NAMEv40
	val MEGABIT_SINCE = MathParser.NAMEv40
	val GIGABIT_SINCE = MathParser.NAMEv40
	val TERABIT_SINCE = MathParser.NAMEv40
	val PETABIT_SINCE = MathParser.NAMEv40
	val EXABIT_SINCE = MathParser.NAMEv40
	val ZETTABIT_SINCE = MathParser.NAMEv40
	val YOTTABIT_SINCE = MathParser.NAMEv40
	val BYTE_SINCE = MathParser.NAMEv40
	val KILOBYTE_SINCE = MathParser.NAMEv40
	val MEGABYTE_SINCE = MathParser.NAMEv40
	val GIGABYTE_SINCE = MathParser.NAMEv40
	val TERABYTE_SINCE = MathParser.NAMEv40
	val PETABYTE_SINCE = MathParser.NAMEv40
	val EXABYTE_SINCE = MathParser.NAMEv40
	val ZETTABYTE_SINCE = MathParser.NAMEv40
	val YOTTABYTE_SINCE = MathParser.NAMEv40
	val JOULE_SINCE = MathParser.NAMEv40
	val ELECTRONO_VOLT_SINCE = MathParser.NAMEv40
	val KILO_ELECTRONO_VOLT_SINCE = MathParser.NAMEv40
	val MEGA_ELECTRONO_VOLT_SINCE = MathParser.NAMEv40
	val GIGA_ELECTRONO_VOLT_SINCE = MathParser.NAMEv40
	val TERA_ELECTRONO_VOLT_SINCE = MathParser.NAMEv40
	val METRE_PER_SECOND_SINCE = MathParser.NAMEv40
	val KILOMETRE_PER_HOUR_SINCE = MathParser.NAMEv40
	val MILE_PER_HOUR_SINCE = MathParser.NAMEv40
	val KNOT_SINCE = MathParser.NAMEv40
	val METRE_PER_SECOND2_SINCE = MathParser.NAMEv40
	val KILOMETRE_PER_HOUR2_SINCE = MathParser.NAMEv40
	val MILE_PER_HOUR2_SINCE = MathParser.NAMEv40
	val RADIAN_ARC_SINCE = MathParser.NAMEv40
	val DEGREE_ARC_SINCE = MathParser.NAMEv40
	val MINUTE_ARC_SINCE = MathParser.NAMEv40
	val SECOND_ARC_SINCE = MathParser.NAMEv40

}
