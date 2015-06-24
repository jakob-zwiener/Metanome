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

package de.metanome.backend.algorithm_loading;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import de.metanome.algorithm_integration.Algorithm;
import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.algorithm_execution.ProgressReceiver;
import de.metanome.algorithm_integration.algorithm_types.ProgressEstimatingAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.UniqueColumnCombinationsAlgorithm;
import de.metanome.algorithm_integration.result_receiver.OmniscientResultReceiver;

/**
 * Test for {@link AlgorithmJarLoader}
 */
public class AlgorithmJarLoaderTest {

  /**
   * A valid algorithm jar should be loadable and of correct class.
   */
  @Test
  public void loadAlgorithm()
    throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
    IllegalArgumentException, SecurityException, InvocationTargetException,
    NoSuchMethodException, AlgorithmExecutionException
  {
    // Setup
    AlgorithmJarLoader loader = new AlgorithmJarLoader();

    // Execute functionality
    Algorithm algorithm = loader.loadAlgorithm("example_ucc_algorithm.jar");

    // Check result
    assertNotNull(algorithm);
    assertTrue(algorithm instanceof UniqueColumnCombinationsAlgorithm);

    UniqueColumnCombinationsAlgorithm uccAlgorithm = (UniqueColumnCombinationsAlgorithm) algorithm;
    uccAlgorithm.setResultReceiver(mock(OmniscientResultReceiver.class));

    ProgressEstimatingAlgorithm progressAlgorithm = (ProgressEstimatingAlgorithm) algorithm;
    progressAlgorithm.setProgressReceiver(mock(ProgressReceiver.class));

    algorithm.execute();
  }
}
