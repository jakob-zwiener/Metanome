/*
 * Copyright 2015 by the Metanome project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.metanome.frontend.client.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilePathHelperTest {

  @Test
  public void testGetFileName() throws Exception {
    // Set up
    String filePath = "this/is/a/test.csv";
    // Expected Values
    String expectedFileName = "test.csv";
    // Execute
    String actualFileName = FilePathHelper.getFileName(filePath);
    // Check
    assertEquals(expectedFileName, actualFileName);
  }

  @Test
  public void testGetDirectory() throws Exception {
    // Set up
    String filePath = "this/is/a/test.csv";
    // Expected Values
    String expectedFilePath = "this/is/a/";
    // Execute
    String actualFilePath = FilePathHelper.getFilePath(filePath);
    // Check
    assertEquals(expectedFilePath, actualFilePath);
  }
}
