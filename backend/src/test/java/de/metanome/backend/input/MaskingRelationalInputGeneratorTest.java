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

package de.metanome.backend.input;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.backend.input.file.CsvFileOneLineFixture;

/**
 * Tests for {@link MaskingRelationalInputGenerator}
 * @author Jakob Zwiener
 */
public class MaskingRelationalInputGeneratorTest {

  /**
   * Test for {@link MaskingRelationalInputGenerator#generateNewCopy()}
   * <p>
   * The generated {@link MaskingRelationalInput} should have the correct columns masked.
   */
  @Test
  public void testGenerateNewCopy() throws InputGenerationException, InputIterationException {
    // Setup
    CsvFileOneLineFixture fixture = new CsvFileOneLineFixture();
    RelationalInputGenerator inputGenerator = mock(RelationalInputGenerator.class);
    when(inputGenerator.generateNewCopy()).thenReturn(fixture.getTestData());
    RelationalInputGenerator maskingInputGenerator = new MaskingRelationalInputGenerator(inputGenerator, 0, 2);

    // Execute functionality
    RelationalInput actualMaskingInput = maskingInputGenerator.generateNewCopy();

    // Check result
    assertEquals(fixture.getExpectedNumberOfMaskedColumns(), actualMaskingInput.numberOfColumns());
    assertEquals(fixture.getExpectedMaskedStrings(), actualMaskingInput.next());
  }
}
