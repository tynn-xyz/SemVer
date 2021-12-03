//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver

/**
 * Whether or not this semantic version has build meta data
 */
public val SemVer.hasBuildMetaData: Boolean
    get() = build.isNotEmpty()

/**
 * Creates a new semantic version without [SemVer.build] meta data
 */
public fun SemVer.removeBuildMetaData(): SemVer = SemVer(
    major = major,
    minor = minor,
    patch = patch,
    preRelease = preRelease,
)
