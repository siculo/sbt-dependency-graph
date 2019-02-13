package net.virtualvoid.sbt.graph.util

import org.specs2.mutable.Specification

class LicenseRegisterSpec extends Specification {
  "LicenseRegister" should {
    "found no license id" in {
      val register = new LicenseRegister()

      register.findByUrl("http://www.apache.org/licenses/LICENSE-2.0") === "Apache-2.0" and
      register.findByUrl("http://www.opensource.org/licenses/MIT") === "MIT"
    }
  }
}
