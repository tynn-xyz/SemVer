//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver

const val major = 1L
const val minor = 2L
const val patch = 3L

val preRelease = listOf(
    "foo-bar",
    "baz01",
    "0",
)
val build = listOf(
    "foo-bar",
    "baz01",
    "00",
)
