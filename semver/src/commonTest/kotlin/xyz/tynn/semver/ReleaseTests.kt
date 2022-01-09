//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ReleaseTests {

    @Test
    fun test_SemVer_isPreRelease() {
        assertTrue {
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).isPreRelease
        }
        assertFalse {
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                build,
            ).isPreRelease
        }
    }

    @Test
    fun test_SemVer_isRelease() {
        assertFalse {
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).isRelease
        }
        assertTrue {
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                build,
            ).isRelease
        }
    }

    @Test
    fun test_SemVer_newMajorRelease() {
        assertEquals(
            SemVer(
                major + 1,
                0,
                0,
                listOf(),
                listOf(),
            ),
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).newMajorRelease()
        )
    }

    @Test
    fun test_SemVer_newMinorRelease() {
        assertEquals(
            SemVer(
                major,
                minor + 1,
                0,
                listOf(),
                listOf(),
            ),
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).newMinorRelease()
        )
    }

    @Test
    fun test_SemVer_newPatchRelease() {
        assertEquals(
            SemVer(
                major,
                minor,
                patch + 1,
                listOf(),
                listOf(),
            ),
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).newPatchRelease()
        )
    }

    @Test
    fun test_SemVer_newPreRelease() {
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
                preRelease + "new01",
                listOf(),
            ),
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).newPreRelease(
                preRelease + "new01",
            )
        )
    }

    @Test
    fun test_SemVer_newRelease() {
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                listOf(),
            ),
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).newRelease()
        )
    }
}
