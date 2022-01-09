//  Copyright 2022 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver.example

import xyz.tynn.semver.*

public fun main(args: Array<String>) {
    val versionString = args.firstOrNull()
        ?: "0.0.0-alpha01+debug"
    val version = versionString.toSemVer()
    println(version)
    println("=".repeat(80))
    println(version.withBuildMetaData("example"))
    println(version.removeBuildMetaData())
    println(version.newAlphaPreRelease())
    println(version.newBetaPreRelease())
    println(version.newRelease())
    println(version.newPatchRelease())
    println(version.newMinorRelease())
    println(version.newMajorRelease())
}
