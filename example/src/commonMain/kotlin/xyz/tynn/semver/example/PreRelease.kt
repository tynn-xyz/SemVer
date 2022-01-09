//  Copyright 2022 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver.example

import xyz.tynn.semver.SemVer
import xyz.tynn.semver.newPreRelease

public fun SemVer.newAlphaPreRelease(): SemVer = newPreRelease {
    asPreReleaseModel().newAlphaPreRelease()
}

public fun SemVer.newBetaPreRelease(): SemVer = newPreRelease {
    asPreReleaseModel().newBetaPreRelease()
}

private typealias PreRelease = List<String>

private fun PreRelease.asPreReleaseModel() =
    this as? PreReleaseModel ?: PreReleaseModel(this)

private val alphaPatter = "alpha(\\d\\d)".toRegex()
private val betaPatter = "beta(\\d\\d)".toRegex()

private fun String.versionOf(
    pattern: Regex
) = pattern.matchEntire(this)
    ?.groupValues?.get(1)?.toInt()

private infix fun String?.format(
    version: Int
) = this?.let {
    "$it${version.toString().padStart(2, '0')}"
}

private class PreReleaseModel(
    segments: PreRelease
) : PreRelease by segments {

    val alpha: Int
    val beta: Int

    init {
        val (alpha, beta) = when (segments.size) {
            0 -> 0 to 0
            1 -> segments[0].versionOf(alphaPatter) to segments[0].versionOf(betaPatter)
            2 -> segments[1].versionOf(alphaPatter) to segments[0].versionOf(betaPatter)
            else -> null to null
        }
        if (alpha == null && beta == null)
            throw IllegalArgumentException("not a valid pre release")
        this.alpha = alpha ?: 0
        this.beta = beta ?: 0
    }

    fun newAlphaPreRelease(): PreRelease = listOfNotNull(
        "beta".takeUnless { beta == 0 } format beta,
        "alpha" format alpha + 1
    )

    fun newBetaPreRelease(): PreRelease = listOfNotNull(
        "beta" format beta + 1
    )
}
