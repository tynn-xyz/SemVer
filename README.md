# SemVer
[![Build][build-shield]][build]

_SemVer_ is a Kotlin Multiplatform implementation of
[Semantic Versioning](https://semver.org).


## Usage

    val version = "1.2.3-a1+b123".toSemVer()
    val (major, minor, patch) = version

### Ordering

Build metadata does not figure into precedence. Therefore `SemVer` has a
natural ordering that is inconsistent with equals.

    1.2.3+a <= 1.2.3+b == true
    1.2.3+a >= 1.2.3+b == true
    1.2.3+a == 1.2.3+b == false


## License

    Copyright (C) 2020 Christian Schmitz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [build]: https://github.com/tynn-xyz/SemVer/actions
  [build-shield]: https://img.shields.io/github/workflow/status/tynn-xyz/SemVer/Build
