/*
 * Copyright 2015 Johannes Rudolph
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.virtualvoid.sbt.graph.rendering

import net.virtualvoid.sbt.graph.{Module, ModuleGraph}

import scala.xml.{NodeBuffer, XML}

object CycloneDx {
  def saveAsCycloneDx(graph: ModuleGraph, outputFile: String): Unit = {
    val componentsXml =
      for (n ← graph.nodes) yield {
        val c: Module = n
        val licenseXml = n.license.map {
          licenseName ⇒
            <licenses>
              <license>
                <id>{ licenseName }</id>
              </license>
            </licenses>
        }.getOrElse(new NodeBuffer())

        <component type="library">
          <group>{ n.id.organisation }</group>
          <name>{ n.id.name }</name>
          <version>{ n.id.version }</version>
          { licenseXml }
        </component>
      }

    val xml =
      <bom xmlns="http://cyclonedx.org/schema/bom/1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1" xsi:schemaLocation="http://cyclonedx.org/schema/bom/1.0 http://cyclonedx.org/schema/bom/1.0">
        <components>
          { componentsXml }
        </components>
      </bom>

    XML.save(outputFile, xml)
  }
}
