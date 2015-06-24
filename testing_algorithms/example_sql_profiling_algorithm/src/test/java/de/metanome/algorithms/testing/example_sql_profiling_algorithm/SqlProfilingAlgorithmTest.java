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

package de.metanome.algorithms.testing.example_sql_profiling_algorithm;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementDatabaseConnection;
import de.metanome.algorithm_integration.input.DatabaseConnectionGenerator;

/**
 * Tests for {@link de.metanome.algorithms.testing.example_sql_profiling_algorithm.SqlProfilingAlgorithm}
 * @author Jakob Zwiener
 */
public class SqlProfilingAlgorithmTest {

  protected SqlProfilingAlgorithm algorithm;

  @Before
  public void setUp() throws Exception {
    algorithm = new SqlProfilingAlgorithm();
  }

  /**
   * Test method for {@link SqlProfilingAlgorithm#getConfigurationRequirements()}
   */
  @Test
  public void testGetConfigurationRequirements() {
    // Execute functionality
    List<ConfigurationRequirement>
      actualConfigurationRequirements =
      algorithm.getConfigurationRequirements();

    // Check result
    assertThat(actualConfigurationRequirements.get(0),
      instanceOf(ConfigurationRequirementDatabaseConnection.class));
  }

  /**
   * Test method for {@link de.metanome.algorithms.testing.example_sql_profiling_algorithm.SqlProfilingAlgorithm#setDatabaseConnectionGeneratorConfigurationValue(String,
   * de.metanome.algorithm_integration.input.DatabaseConnectionGenerator...)}
   */
  @Test
  public void testSetSqlInputConfigurationValue() throws AlgorithmConfigurationException {
    // Setup
    // Expected values
    DatabaseConnectionGenerator expectedInputGenerator = mock(DatabaseConnectionGenerator.class);

    // Execute functionality
    algorithm
      .setDatabaseConnectionGeneratorConfigurationValue(SqlProfilingAlgorithm.DATABASE_IDENTIFIER,
        expectedInputGenerator);

    // Check result
    assertSame(expectedInputGenerator, algorithm.inputGenerator);
  }

  /**
   * TODO docs
   */
  @Test
  public void testExecute() throws AlgorithmConfigurationException {
    // Setup
    DatabaseConnectionGenerator inputGenerator = mock(DatabaseConnectionGenerator.class);
    algorithm
      .setDatabaseConnectionGeneratorConfigurationValue(SqlProfilingAlgorithm.DATABASE_IDENTIFIER,
        inputGenerator);
    // Expected values
    // TODO add asserts
    // the systems property should be output from an database connection

  }
}
