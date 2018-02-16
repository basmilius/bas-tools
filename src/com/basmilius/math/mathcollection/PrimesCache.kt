/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.mathcollection

/**
 * Class PrimesCache
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.mathcollection
 */
class PrimesCache
{

	/**
	 * Companion Object PrimesCache
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.math.mathcollection
	 */
	companion object
	{

		val DEFAULT_MAX_NUM_IN_CACHE = 10000000
		val CACHE_EMPTY = false
		val CACHING_FINISHED = true
		val IS_PRIME = 1
		val IS_NOT_PRIME = 0
		val NOT_IN_CACHE = -1

	}

	var maxNumInCache = DEFAULT_MAX_NUM_IN_CACHE
	private var numberOfPrimes = 0
	private var computingTime = 0.0
	var cacheStatus = false
	private var isPrime: BooleanArray = BooleanArray(0)

	/**
	 * PrimesCache Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor (): this(DEFAULT_MAX_NUM_IN_CACHE)

	/**
	 * PrimesCache Constructor.
	 *
	 * @param maxNumInCache Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(maxNumInCache: Int)
	{
		this.maxNumInCache = if (maxNumInCache > 2) maxNumInCache else DEFAULT_MAX_NUM_IN_CACHE
		this.cacheStatus = CACHE_EMPTY
		this.eratosthenesSieve()
		this.countPrimes()
		this.cacheStatus = CACHING_FINISHED
	}

	/**
	 * Eratosthenes Sieve implementation.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun eratosthenesSieve()
	{
		this.isPrime = BooleanArray(maxNumInCache + 1)
		this.numberOfPrimes = 0

		val startTime = System.currentTimeMillis()

		isPrime[0] = false
		isPrime[1] = false

		for (i in 2..maxNumInCache)
			isPrime[i] = true

		var i = 2
		while (i * i <= maxNumInCache)
		{
			if (isPrime[i])
			{
				var j = i
				while (i * j <= maxNumInCache)
				{
					isPrime[i * j] = false
					j++
				}
			}

			i++
		}

		val endTime = System.currentTimeMillis()
		this.computingTime = (endTime - startTime) / 1000.0
	}

	/**
	 * Counts found primes.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun countPrimes()
	{
		(0..maxNumInCache)
				.filter { isPrime[it] }
				.forEach { numberOfPrimes++ }
	}

	/**
	 * Check whether given number is prime.
	 *
	 * @param n Int
	 *
	 * @return Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun primeTest(n: Int): Int
	{
		if (n <= maxNumInCache && cacheStatus == CACHING_FINISHED)
			return if (isPrime[n]) IS_PRIME else IS_NOT_PRIME

		return NOT_IN_CACHE
	}

	/**
	 * Gets underlying primes cache boolean table.
	 *
	 * @return BooleanArray
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getPrimes(): BooleanArray
	{
		return this.isPrime
	}

	/**
	 * Returns TRUE if a number is a prime.
	 *
	 * @param n Int
	 *
	 * @return Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun isPrime(n: Int): Boolean
	{
		return this.isPrime[n]
	}

}
