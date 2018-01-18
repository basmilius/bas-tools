package com.basmilius.math.mathcollection

/**
 * Object PhysicalConstants
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.mathcollection
 */
object PhysicalConstants
{

	val LIGHT_SPEED = 299792458.0 * Units.METRE_PER_SECOND
	val GRAVITATIONAL_CONSTANT = 6.67408E-11 * Units.METRE3 * (1.0 / Units.KILOGRAM) * (1.0 / (Units.SECOND * Units.SECOND))
	val GRAVIT_ACC_EARTH = 9.80665 * Units.METRE_PER_SECOND2
	val PLANCK_CONSTANT = 6.626070040E-34 * Units.METRE2 * Units.KILOGRAM / Units.SECOND
	val PLANCK_CONSTANT_REDUCED = PLANCK_CONSTANT / (2 * MathConstants.PI)
	val PLANCK_LENGTH = 1.616229E-35 * Units.METRE
	val PLANCK_MASS = 2.176470E-8 * Units.KILOGRAM
	val PLANCK_TIME = 5.39116E-44 * Units.SECOND

}
