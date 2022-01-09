//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver

import kotlin.test.*

internal class SemVerTests {

    val releaseVersionString = "$major.$minor.$patch"
    val preReleaseVersionString = "$major.$minor.$patch-${
        preRelease.joinToString(".")
    }"
    val buildVersionString = "$major.$minor.$patch+${
        build.joinToString(".")
    }"
    val versionString = buildVersionString.replace(
        releaseVersionString,
        preReleaseVersionString,
    )

    @Test
    fun test_String_toSemVer() {
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ),
            versionString.toSemVer()
        )
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                build,
            ),
            buildVersionString.toSemVer()
        )
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                listOf(),
            ),
            preReleaseVersionString.toSemVer()
        )
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
            ),
            releaseVersionString.toSemVer()
        )
    }

    @Test
    fun test_SemVer() {
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ),
            SemVer(
                major,
                minor,
                patch,
                preRelease.toList(),
                build.toList(),
            )
        )
    }

    @Test
    fun test_SemVer_components() {
        val (major, minor, patch) = SemVer(1, 2, 3)

        assertEquals(
            Triple(1L, 2L, 3L),
            Triple(major, minor, patch),
        )
    }

    @Test
    fun test_SemVer_compareTo() {
        assertTrue {
            "1.2.3".toSemVer() < "2.2.3".toSemVer()
        }
        assertTrue {
            "1.2.3".toSemVer() < "1.3.3".toSemVer()
        }
        assertTrue {
            "1.2.3".toSemVer() < "1.2.4".toSemVer()
        }
        assertTrue {
            "1.2.3-0".toSemVer() < "1.2.3".toSemVer()
        }
        assertTrue {
            "1.2.3".toSemVer() < "1.2.4-0".toSemVer()
        }
        assertTrue {
            "1.2.3-z".toSemVer() < "1.2.3".toSemVer()
        }
        assertTrue {
            "1.2.3-0".toSemVer() < "1.2.3-z".toSemVer()
        }
        assertTrue {
            "1.2.3-0".toSemVer() < "1.2.3-0.0".toSemVer()
        }
        assertTrue {
            "1.2.3-z".toSemVer() < "1.2.3-zz".toSemVer()
        }
        assertEquals(
            0,
            "1.2.3+a".toSemVer().compareTo("1.2.3+b".toSemVer()),
        )
        assertTrue {
            "1.2.3+a".toSemVer() <= "1.2.3+b".toSemVer()
        }
        assertTrue {
            "1.2.3+a".toSemVer() >= "1.2.3+b".toSemVer()
        }
        assertTrue {
            "1.2.3+a".toSemVer() != "1.2.3+b".toSemVer()
        }
        assertFalse {
            "1.2.3+a".toSemVer() == "1.2.3+b".toSemVer()
        }
    }

    @Test
    fun test_SemVer_toString() {
        assertEquals(
            versionString,
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).toString()
        )
        assertEquals(
            buildVersionString,
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                build,
            ).toString()
        )
        assertEquals(
            preReleaseVersionString,
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                listOf(),
            ).toString()
        )
        assertEquals(
            releaseVersionString,
            SemVer(
                major,
                minor,
                patch,
            ).toString()
        )
    }

    @Test
    fun test_invalid_SemVer() {
        assertFailsWith<IllegalArgumentException> {
            "1.2.3-00".toSemVer()
        }
        assertFailsWith<IllegalArgumentException> {
            "1.2.3+a+b".toSemVer()
        }
        assertFailsWith<IllegalArgumentException> {
            "1.2".toSemVer()
        }
        assertFailsWith<IllegalArgumentException> {
            "1".toSemVer()
        }
        assertFailsWith<IllegalArgumentException> {
            SemVer(1, 2, 3, listOf("00"))
        }
        assertFailsWith<IllegalArgumentException> {
            SemVer(1, 2, 3, listOf("~"))
        }
        assertFailsWith<IllegalArgumentException> {
            SemVer(1, 2, 3, listOf(), listOf("~"))
        }
    }
}
