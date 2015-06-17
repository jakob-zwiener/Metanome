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

import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.backend.input.file.CsvFileOneLineFixture;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MaskingRelationalInput}
 *
 * @author Jakob Zwiener
 */
public class MaskingRelationalInputTest {

  CsvFileOneLineFixture fixture;
  MaskingRelationalInput maskingRelationalInput;

  @Before
  public void setUp() throws Exception {
    this.fixture = new CsvFileOneLineFixture();
    this.maskingRelationalInput = new MaskingRelationalInput(fixture.getTestData(), 0, 2);
  }

  /**
   * Test method for {@link MaskingRelationalInput#MaskingRelationalInput(RelationalInput, int...)}
   *
   * The {@link MaskingRelationalInput} should raise an exception when the mask is null.
   */
  @Test
  public void testConstructorMaskNull()
      throws InputGenerationException, InputIterationException, InvalidMaskException {
    try {
      new MaskingRelationalInput(fixture.getTestData(), null);
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }
  }

  /**
   * Test method for {@link MaskingRelationalInput#MaskingRelationalInput(RelationalInput, int...)}
   *
   * The {@link MaskingRelationalInput} should raise an exception when the mask is empty.
   */
  @Test
  public void testConstructorMaskEmpty() throws InputGenerationException, InputIterationException {
    try {
      new MaskingRelationalInput(fixture.getTestData());
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }
  }

  /**
   * Test method for {@link MaskingRelationalInput#MaskingRelationalInput(RelationalInput, int...)}
   *
   * The {@link MaskingRelationalInput} should raise an exception when the mask contains invalid column
   * indices.
   */
  @Test
  public void testConstructorInvalidMaskContents()
      throws InputGenerationException, InputIterationException {
    try {
      new MaskingRelationalInput(fixture.getTestData(), 0, 2, 6);
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }

    try {
      new MaskingRelationalInput(fixture.getTestData(), 0, 2, -1);
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }
  }

  @Test
  public void testHasNext() throws InputIterationException {
    // Check result
    assertTrue(this.maskingRelationalInput.hasNext());
    this.maskingRelationalInput.next();
    assertFalse(this.maskingRelationalInput.hasNext());
  }

  /**
   * Test method for {@link MaskingRelationalInput#next()}
   *
   * All lines should be properly masked.
   */
  @Test
  public void testNext() throws InputIterationException {
    // Check result
    assertEquals(fixture.getExpectedMaskedStrings(), this.maskingRelationalInput.next());
  }

  /**
   * Test method for {@link MaskingRelationalInput#numberOfColumns()}, and {@link
   * MaskingRelationalInput#next()}
   *
   * Values can be in the table multiple times.
   */
  @Test
  public void testRepeatedValuesInMask()
      throws InputGenerationException, InputIterationException, InvalidMaskException {
    MaskingRelationalInput
        maskingRelationalInput =
        new MaskingRelationalInput(fixture.getTestData(), 0, 2, 2);
    assertEquals(3, maskingRelationalInput.numberOfColumns());
    assertTrue(maskingRelationalInput.hasNext());
    assertEquals(fixture.getExpectedMaskedStringDoubled(), maskingRelationalInput.next());
  }

  /**
   * Tets method for {@link MaskingRelationalInput#numberOfColumns()}
   *
   * The number of masked columns should be returned.
   */
  @Test
  public void testNumberOfColumns() {
    // Execute functionality
    // Check result
    assertEquals(fixture.getExpectedNumberOfMaskedColumns(),
                 maskingRelationalInput.numberOfColumns());
  }

  /**
   * Test method for {@link MaskingRelationalInput#relationName()}
   */
  @Test
  public void testRelationName() {
    assertEquals(fixture.getExpectedRelationName(), maskingRelationalInput.relationName());
  }

  /**
   * Test method for {@link MaskingRelationalInput#columnNames()}
   */
  @Test
  public void testColumnNames() {
    // Execute functionality
    // Check result
    assertEquals(fixture.getExpectedMaskedColumnNames(), maskingRelationalInput.columnNames());
  }

  /**
   * Test method for {@link MaskingRelationalInput#columnNames()}
   *
   * Generated masked headers do not necessarily have consecutive numbers. The numbers of the
   * generated headers correspond to the unmasked headers.
   */
  @Test
  public void testColumnNamesGenerated()
      throws InputGenerationException, InputIterationException, InvalidMaskException {
    // Setup
    CsvFileOneLineFixture fixtureSeparator = new CsvFileOneLineFixture(';');

    MaskingRelationalInput
        maskingRelationalInputWithHeader =
        new MaskingRelationalInput(fixture.getTestData(), 0, 2);
    MaskingRelationalInput
        maskingRelationalInputWithoutHeader =
        new MaskingRelationalInput(fixture.getTestDataWithoutHeader(), 0, 2);

    // Execute functionality
    // Check result
    assertEquals(fixture.getExpectedMaskedColumnNames(),
                 maskingRelationalInputWithHeader.columnNames());
    assertEquals(fixture.getExpectedMaskedDefaultColumnNames(),
                 maskingRelationalInputWithoutHeader.columnNames());
    assertEquals(fixture.getExpectedMaskedStrings(), maskingRelationalInputWithoutHeader.next());
  }

  /**
   * Test method for {@link MaskingRelationalInput#close()}
   */
  @Test
  public void testClose() throws Exception {
    // Setup
    RelationalInput mockedRelationalInput = mock(RelationalInput.class);
    when(mockedRelationalInput.numberOfColumns()).thenReturn(3);
    MaskingRelationalInput
        maskingRelationalInput =
        new MaskingRelationalInput(mockedRelationalInput, 0, 2);

    // Execute functionality
    maskingRelationalInput.close();

    // Check result
    verify(mockedRelationalInput).close();
  }

}
