//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver

/**
 * Whether or not this semantic version is a pre release version
 */
val SemVer.isPreRelease
    get() = preRelease.isNotEmpty()

/**
 * Whether or not this semantic version is a release version
 */
val SemVer.isRelease
    get() = preRelease.isEmpty()

/**
 * Creates a new major release version
 *
 * The [SemVer.major] version is incremented.
 * The [SemVer.minor] and [SemVer.patch] versions is `0`.
 * The [SemVer.preRelease] and [SemVer.build] meta data is removed.
 */
fun SemVer.newMajorRelease() = SemVer(
    major = major + 1,
    minor = 0,
    patch = 0,
)

/**
 * Creates a new minor release version
 *
 * The [SemVer.minor] version is incremented.
 * The [SemVer.patch] versions is 0.
 * The [SemVer.preRelease] and [SemVer.build] meta data is removed.
 */
fun SemVer.newMinorRelease() = SemVer(
    major = major,
    minor = minor + 1,
    patch = 0,
)

/**
 * Creates a new patch release version
 *
 * The [SemVer.patch] version is incremented.
 * The [SemVer.preRelease] and [SemVer.build] meta data is removed.
 */
fun SemVer.newPatchRelease() = SemVer(
    major = major,
    minor = minor,
    patch = patch + 1,
)

/**
 * Creates a new pre release version
 *
 * The [SemVer.preRelease] version is replaced with [preRelease].
 * The [SemVer.build] meta data is removed.
 */
fun SemVer.newPreRelease(
    preRelease: List<String>
) = newPreRelease {
    preRelease
}

/**
 * Creates a new major release version
 *
 * The [SemVer.preRelease] version is replaced with the result of [update].
 * The [SemVer.build] meta data is removed.
 */
inline fun SemVer.newPreRelease(
    crossinline update: List<String>.() -> List<String>
) = SemVer(
    major = major,
    minor = minor,
    patch = patch,
    preRelease = preRelease.update(),
)

/**
 * Creates a new release version
 *
 * The [SemVer.preRelease] and [SemVer.build] meta data is removed.
 */
fun SemVer.newRelease() = SemVer(
    major = major,
    minor = minor,
    patch = patch,
)
