//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MetaDataTests {

    @Test
    fun test_SemVer_hasBuildMetaData() {
        assertTrue {
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).hasBuildMetaData
        }
        assertTrue {
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                build,
            ).hasBuildMetaData
        }
        assertFalse {
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                listOf(),
            ).hasBuildMetaData
        }
        assertFalse {
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                listOf(),
            ).hasBuildMetaData
        }
    }

    @Test
    fun test_SemVer_removeBuildMetaData() {
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
                preRelease,
            ),
            SemVer(
                major,
                minor,
                patch,
                preRelease,
                build,
            ).removeBuildMetaData()
        )
        assertEquals(
            SemVer(
                major,
                minor,
                patch,
            ),
            SemVer(
                major,
                minor,
                patch,
                listOf(),
                build,
            ).removeBuildMetaData()
        )
    }
}
