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

package de.metanome.algorithm_integration.configuration;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for {@link de.metanome.algorithm_integration.configuration.DbSystem}
 * @author Jakob Zwiener
 */
public class DbSystemTest {

  /**
   * Test method for {@link DbSystem#names()} <p/> Returns a list of string representation of all
   * options of the enum.
   */
  @Test
  public void testNames() throws Exception {
    // Setup
    // Expected values
    Set<String> expectedEnumNames = new HashSet<>();
    for (DbSystem value : DbSystem.class.getEnumConstants()) {
      expectedEnumNames.add(value.name());
    }

    // Execute functionality
    String[] actualNames = DbSystem.names();

    // Check result
    assertEquals(expectedEnumNames, new HashSet<>(Arrays.asList(actualNames)));
  }
}
