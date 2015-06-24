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

package de.metanome.algorithms.testing.example_cucc_algorithm;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.algorithm_execution.ProgressReceiver;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementString;
import de.metanome.algorithm_integration.result_receiver.ConditionalUniqueColumnCombinationResultReceiver;
import de.metanome.algorithm_integration.result_receiver.CouldNotReceiveResultException;
import de.metanome.algorithm_integration.results.ConditionalUniqueColumnCombination;

/**
 * Test for {@link de.metanome.algorithms.testing.example_cucc_algorithm.ExampleAlgorithm}
 * @author Jens Ehrlich
 */
public class ExampleAlgorithmTest {

  protected ExampleAlgorithm algorithm;
  protected String pathIdentifier;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    algorithm = new ExampleAlgorithm();
    pathIdentifier = "pathToInputFile";
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {

  }

  /**
   * Test method for {@link ExampleAlgorithm#getConfigurationRequirements()} <p/> The algorithm
   * should return one configuration specification of string type
   */
  @Test
  public void testGetConfigurationRequirements() {
    // Execute functionality
    List<ConfigurationRequirement>
      actualConfigurationRequirements =
      this.algorithm.getConfigurationRequirements();

    // Check result
    assertEquals(2, actualConfigurationRequirements.size());
    assertThat(actualConfigurationRequirements.get(0),
      instanceOf(ConfigurationRequirementString.class));
  }

  /**
   * Test method for {@link ExampleAlgorithm#setStringConfigurationValue(String, String...)} <p/>
   * The algorithm should store the path when it is supplied through setConfigurationValue.
   */
  @Test
  public void testSetConfigurationValue() throws AlgorithmConfigurationException {
    // Setup
    // Expected values
    String[] expectedConfigurationValues = { "test1", "test2" };

    // Execute functionality
    this.algorithm.setStringConfigurationValue(pathIdentifier, expectedConfigurationValues);

    // Check result
    assertEquals(expectedConfigurationValues[0], this.algorithm.path1);
    assertEquals(expectedConfigurationValues[1], this.algorithm.path2);
  }

  /**
   * Test method for {@link ExampleAlgorithm#execute()} <p/> When the algorithm is started after
   * configuration a result should be received.
   */
  @Test
  public void testExecute() throws AlgorithmConfigurationException, CouldNotReceiveResultException {
    // Setup
    ConditionalUniqueColumnCombinationResultReceiver
      resultReceiver =
      mock(ConditionalUniqueColumnCombinationResultReceiver.class);
    ProgressReceiver progressCache = mock(ProgressReceiver.class);
    String[] configurationValues = { "something1", "something2" };
    this.algorithm.setStringConfigurationValue(pathIdentifier, configurationValues);

    // Execute functionality
    this.algorithm.setResultReceiver(resultReceiver);
    this.algorithm.setProgressReceiver(progressCache);
    this.algorithm.execute();

    // Check result
    verify(resultReceiver).receiveResult(isA(ConditionalUniqueColumnCombination.class));
  }
}
