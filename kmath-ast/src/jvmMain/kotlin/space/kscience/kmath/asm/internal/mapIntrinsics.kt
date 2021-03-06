/*
 * Copyright 2018-2021 KMath contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:JvmName("MapIntrinsics")

package space.kscience.kmath.asm.internal

import space.kscience.kmath.misc.StringSymbol
import space.kscience.kmath.misc.Symbol

/**
 * Gets value with given [key] or throws [NoSuchElementException] whenever it is not present.
 *
 * @author Iaroslav Postovalov
 */
internal fun <V> Map<Symbol, V>.getOrFail(key: String): V = getValue(StringSymbol(key))
