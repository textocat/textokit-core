/*
 *    Copyright 2015 Textocat
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
package com.textocat.textokit.benchmarking

import org.scalatest.FlatSpecLike

/**
 * @author Rinat Gareev
 *
 */
class AEBenchmarkSpec extends FlatSpecLike {

  "AEBenchmark" should "write csv with timings" in {
    AEBenchmark.main(Array(
      "--ae-name", "com.textocat.textokit.tokenizer.tokenizer-ae",
      "--data", "test-data/col-reader-desc.xml",
      "-o", "target/ae-benchmark-test-output.csv"))
  }

}