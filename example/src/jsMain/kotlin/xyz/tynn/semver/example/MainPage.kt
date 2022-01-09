//  Copyright 2022 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver.example

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.appendText
import kotlinx.dom.clear
import kotlinx.html.button
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.br
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.span
import org.w3c.dom.Element
import org.w3c.dom.Node

public fun main() {
    window.onload = {
        val version = "0.0.0".toMutableSemVer()
        document.body?.renderPage(version)
        version.setUpdateListener {
            with(document) {
                getElementById("major")?.setText(major)
                getElementById("minor")?.setText(minor)
                getElementById("patch")?.setText(patch)
                getElementById("preRelease")?.setText(preRelease)
            }
        }
    }
}

private fun Node.renderPage(version: MutableSemVer) {
    append {
        button {
            id = "major"
            onClickFunction = {
                version.incrementMajorRelease()
            }
        }
        button {
            id = "minor"
            onClickFunction = {
                version.incrementMinorRelease()
            }
        }
        button {
            id = "patch"
            onClickFunction = {
                version.incrementPatchRelease()
            }
        }
        span {
            id = "preRelease"
        }
        br()
        button {
            text("alpha")
            onClickFunction = {
                version.incrementAlphaPreRelease()
            }
        }
        button {
            text("beta")
            onClickFunction = {
                version.incrementBetaPreRelease()
            }
        }
    }
}

private fun Element.setText(text: String) {
    clear()
    appendText(text)
}
