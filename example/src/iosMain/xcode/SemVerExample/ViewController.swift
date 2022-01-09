//  Copyright 2022 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

import UIKit
import SemVerExampleKt

class ViewController: UIViewController {
    
    private var version = MutableSemVerKt.toMutableSemVer(Bundle.main.infoDictionary?["CFBundleShortVersionString"] as! String)
    
    @IBOutlet weak var buttonMajor: UIButton!
    @IBOutlet weak var buttonMinor: UIButton!
    @IBOutlet weak var buttonPatch: UIButton!
    
    @IBOutlet weak var textPreRelease: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        version.setUpdateListener { [self] version in
            buttonMajor.setTitle(version.major, for: .normal)
            buttonMinor.setTitle(version.minor, for: .normal)
            buttonPatch.setTitle(version.patch, for: .normal)
            textPreRelease.text = version.preRelease
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        version.setUpdateListener(listener: nil)
        super.viewDidDisappear(animated)
    }
    
    @IBAction func onButtonMajorTab() {
        version.incrementMajorRelease()
    }
    
    @IBAction func onButtonMinorTab() {
        version.incrementMinorRelease()
    }
    
    @IBAction func onButtonPatchab() {
        version.incrementPatchRelease()
    }
    
    @IBAction func onButtonAlphaTab() {
        version.incrementAlphaPreRelease()
    }
    
    @IBAction func onButtonBetaTab() {
        version.incrementBetaPreRelease()
    }
    
}
