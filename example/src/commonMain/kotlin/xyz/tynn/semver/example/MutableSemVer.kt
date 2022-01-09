//  Copyright 2022 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver.example

import xyz.tynn.semver.*
import kotlin.properties.Delegates.observable

public fun String.toMutableSemVer(): MutableSemVer = MutableSemVer(toSemVer())
public fun MutableSemVer.setSemVerString(
    version: String,
): Unit = setSemVer(version.toSemVer())

public class MutableSemVer internal constructor(version: SemVer) {

    private var listener: (MutableSemVer.() -> Unit)? = null

    private var version by observable(version) { _, _, _ ->
        listener?.invoke(this)
    }

    public val major: String get() = version.major.toString()
    public val minor: String get() = version.minor.toString()
    public val patch: String get() = version.patch.toString()

    public val preRelease: String get() = version.preRelease.joinToString(".")

    public fun setSemVer(version: SemVer) {
        this.version = version
    }

    public fun setUpdateListener(listener: (MutableSemVer.() -> Unit)?) {
        this.listener = listener
        listener?.invoke(this)
    }

    public fun incrementMajorRelease() {
        version = version.newMajorRelease()
    }

    public fun incrementMinorRelease() {
        version = version.newMinorRelease()
    }

    public fun incrementPatchRelease() {
        version = version.newPatchRelease()
    }

    public fun incrementAlphaPreRelease() {
        version = version.newAlphaPreRelease()
    }

    public fun incrementBetaPreRelease() {
        version = version.newBetaPreRelease()
    }

    override fun toString(): String = version.toString()
}
