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

package de.metanome.backend.configuration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.algorithm_types.ProgressEstimatingAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.TableInputParameterAlgorithm;
import de.metanome.algorithm_integration.input.TableInputGenerator;

/**
 * Tests for {@link ConfigurationValueTableInputGenerator}
 * @author Jakob Zwiener
 */
public class ConfigurationValueTableInputGeneratorTest {

  /**
   * Test method for {@link ConfigurationValueTableInputGenerator#triggerSetValue(de.metanome.algorithm_integration.Algorithm,
   * java.util.Set)} <p/> Parameters should be set on the algorithm through triggerSetValue. This is
   * the last call in a double dispatch call to determine the parameters type.
   */
  @Test
  public void testTriggerSetValue() throws AlgorithmConfigurationException {
    // Setup
    TableInputParameterAlgorithm algorithm = mock(TableInputParameterAlgorithm.class);
    Set<Class<?>> interfaces = new HashSet<>();
    interfaces.add(TableInputParameterAlgorithm.class);
    // Expected values
    String expectedIdentifier = "configId1";
    TableInputGenerator[]
      expectedConfigurationValue =
      { mock(TableInputGenerator.class), mock(TableInputGenerator.class) };

    // Execute functionality
    ConfigurationValueTableInputGenerator
      configValue =
      new ConfigurationValueTableInputGenerator(expectedIdentifier,
        expectedConfigurationValue);
    configValue.triggerSetValue(algorithm, interfaces);

    // Check result
    verify(algorithm)
      .setTableInputConfigurationValue(expectedIdentifier, expectedConfigurationValue);
  }

  /**
   * Test method for {@link de.metanome.backend.configuration.ConfigurationValueTableInputGenerator#triggerSetValue(de.metanome.algorithm_integration.Algorithm,
   * java.util.Set)} <p/> When the correct algorithm interface is missing an exception should be
   * thrown.
   */
  @Test
  public void testTriggerSetValueMissingInterface() {
    // Setup
    TableInputParameterAlgorithm algorithm = mock(TableInputParameterAlgorithm.class);
    // The file input parameter algorithm interface is missing.
    Set<Class<?>> interfaces = new HashSet<>();
    interfaces.add(ProgressEstimatingAlgorithm.class);
    // Expected values
    String expectedIdentifier = "configId1";
    TableInputGenerator[]
      expectedConfigurationValues =
      { mock(TableInputGenerator.class), mock(TableInputGenerator.class) };

    // Execute functionality
    ConfigurationValueTableInputGenerator
      configValue =
      new ConfigurationValueTableInputGenerator(expectedIdentifier,
        expectedConfigurationValues);
    try {
      configValue.triggerSetValue(algorithm, interfaces);
      fail("No exception was thrown.");
    }
    catch (AlgorithmConfigurationException e) {
      // Intentionally left blank
    }
  }

}
