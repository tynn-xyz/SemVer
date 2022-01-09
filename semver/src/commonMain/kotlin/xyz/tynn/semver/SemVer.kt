//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver

import kotlin.math.sign

private fun String.throwInvalidSemVer(): Nothing =
    throw IllegalArgumentException("invalid semantic version '$this'")

/**
 * Parses a string as a semantic version or throws an [IllegalArgumentException]
 */
public fun String.toSemVer(): SemVer = semVerPattern.matchEntire(this)?.run {
    val (_, major, minor, patch) = groupValues
    SemVer(
        major.toLong(),
        minor.toLong(),
        patch.toLong(),
        groupValues.getOrNull(4)
            ?.takeUnless(String::isEmpty)
            ?.split(".")
            ?: emptyList(),
        groupValues.getOrNull(5)
            ?.takeUnless(String::isEmpty)
            ?.split(".")
            ?: emptyList(),
    )
} ?: throwInvalidSemVer()

/**
 * This class represents a [semantic version](https://semver.org)
 * in the form [major].[minor].[patch]-[preRelease]+[build]`
 *
 * **Note**: this class has a natural ordering that is inconsistent with equals.
 *
 * ```
 * 1.2.3+a <= 1.2.3+b == true
 * 1.2.3+a >= 1.2.3+b == true
 * 1.2.3+a == 1.2.3+b == false
 * ```
 *
 * @constructor validates the version or throws an [IllegalArgumentException]
 */
public class SemVer(
    public val major: Long,
    public val minor: Long,
    public val patch: Long,
    public val preRelease: List<String> = emptyList(),
    public val build: List<String> = emptyList(),
) : Comparable<SemVer> {

    private val versionString = buildString {
        append(major)
        append('.')
        append(minor)
        append('.')
        append(patch)
        append(preRelease, "-")
        append(build, "+")
    }

    init {
        semVerPattern matches versionString || versionString.throwInvalidSemVer()
    }

    private val preReleaseSegment = preRelease.map {
        numericPatter matches it to it
    }

    /** The [major] version */
    public operator fun component1(): Long = major

    /** The [minor] version */
    public operator fun component2(): Long = minor

    /** The [patch] version */
    public operator fun component3(): Long = patch

    override fun toString(): String = versionString

    override fun compareTo(other: SemVer): Int {
        if (this === other) return 0
        var diff = major - other.major
        if (diff != 0L) return diff.sign
        diff = minor - other.minor
        if (diff != 0L) return diff.sign
        diff = patch - other.patch
        if (diff != 0L) return diff.sign
        return when {
            preRelease.isEmpty() && other.preRelease.isEmpty() -> 0
            preRelease.isEmpty() -> 1
            other.preRelease.isEmpty() -> -1
            else -> comparePreReleaseSegments(
                preReleaseSegment,
                other.preReleaseSegment,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SemVer) return false
        if (major != other.major) return false
        if (minor != other.minor) return false
        if (patch != other.patch) return false
        if (preRelease != other.preRelease) return false
        if (build != other.build) return false
        return true
    }

    override fun hashCode(): Int {
        var result = major.hashCode()
        result = 43 * result + minor.hashCode()
        result = 43 * result + patch.hashCode()
        result = 43 * result + preRelease.hashCode()
        result = 43 * result + build.hashCode()
        return result
    }
}

private tailrec fun comparePreReleaseSegments(
    left: List<Pair<Boolean, String>>,
    right: List<Pair<Boolean, String>>,
    index: Int = 0,
): Int {
    val leftItem = left.getOrNull(index)
    val rightItem = right.getOrNull(index)
    return when {
        leftItem == null && rightItem == null -> 0
        leftItem == null -> -1
        rightItem == null -> 1
        leftItem.first && !rightItem.first -> -1
        !leftItem.first && rightItem.first -> 1
        else -> comparePreReleaseSegment(
            leftItem.second,
            rightItem.second,
            leftItem.first
        ).takeUnless { it == 0 }
    } ?: comparePreReleaseSegments(left, right, index + 1)
}

private fun comparePreReleaseSegment(
    left: String,
    right: String,
    isNumeric: Boolean,
): Int = when {
    !isNumeric -> left.compareTo(right)
    left.length != right.length ->
        (left.length - right.length).sign
    else -> comparePreReleaseSegment(left, right, false)
}

private fun StringBuilder.append(
    segments: List<String>,
    prefix: String,
) = segments.takeUnless {
    it.isEmpty()
}?.joinTo(
    buffer = this,
    separator = ".",
    prefix = prefix
)

// https://regex101.com/r/vkijKf/1/
private val semVerPattern =
    "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?\$"
        .toRegex()
private val numericPatter = "\\d+".toRegex()
