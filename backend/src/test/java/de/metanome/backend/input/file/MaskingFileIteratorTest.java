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

package de.metanome.backend.input.file;

import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link MaskingFileIterator}
 *
 * @author Jakob Zwiener
 */
public class MaskingFileIteratorTest {

  CsvFileOneLineFixture fixture;
  MaskingFileIterator maskingFileIterator;

  @Before
  public void setUp() throws Exception {
    this.fixture = new CsvFileOneLineFixture();
    this.maskingFileIterator = new MaskingFileIterator(fixture.getTestData(), 0, 2);
  }

  /**
   * Test method for {@link MaskingFileIterator#MaskingFileIterator(FileIterator, int...)}
   *
   * The {@link MaskingFileIterator} should raise an exception when the mask is null.
   */
  @Test
  public void testConstructorMaskNull()
      throws InputGenerationException, InputIterationException, InvalidMaskException {
    try {
      new MaskingFileIterator(fixture.getTestData(), null);
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }
  }

  /**
   * Test method for {@link MaskingFileIterator#MaskingFileIterator(FileIterator, int...)}
   *
   * The {@link MaskingFileIterator} should raise an exception when the mask is empty.
   */
  @Test
  public void testConstructorMaskEmpty() throws InputGenerationException, InputIterationException {
    try {
      new MaskingFileIterator(fixture.getTestData());
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }
  }

  /**
   * Test method for {@link MaskingFileIterator#MaskingFileIterator(FileIterator, int...)}
   *
   * The {@link MaskingFileIterator} should raise an exception when the mask contains invalid column
   * indices.
   */
  @Test
  public void testConstructorInvalidMaskContents()
      throws InputGenerationException, InputIterationException {
    try {
      new MaskingFileIterator(fixture.getTestData(), 0, 2, 6);
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }

    try {
      new MaskingFileIterator(fixture.getTestData(), 0, 2, -1);
      fail("Exception not thrown.");
    } catch (InvalidMaskException e) {
      // Intentionally left blank.
    }
  }

  @Test
  public void testHasNext() throws InputIterationException {
    // Check result
    assertTrue(this.maskingFileIterator.hasNext());
    this.maskingFileIterator.next();
    assertFalse(this.maskingFileIterator.hasNext());
  }

  /**
   * Test method for {@link MaskingFileIterator#next()}
   *
   * All lines should be properly masked.
   */
  @Test
  public void testNext() throws InputIterationException {
    // Check result
    assertEquals(fixture.getExpectedMaskedStrings(), this.maskingFileIterator.next());
  }

  /**
   * Test method for {@link MaskingFileIterator#numberOfColumns()}, and {@link
   * MaskingFileIterator#next()}
   *
   * Values can be in the table multiple times.
   */
  @Test
  public void testRepeatedValuesInMask()
      throws InputGenerationException, InputIterationException, InvalidMaskException {
    MaskingFileIterator
        maskingFileIterator =
        new MaskingFileIterator(fixture.getTestData(), 0, 2, 2);
    assertEquals(3, maskingFileIterator.numberOfColumns());
    assertTrue(maskingFileIterator.hasNext());
    assertEquals(fixture.getExpectedMaskedStringDoubled(), maskingFileIterator.next());
  }

  /**
   * Tets method for {@link MaskingFileIterator#numberOfColumns()}
   *
   * The number of masked columns should be returned.
   */
  @Test
  public void testNumberOfColumns() {
    // Execute functionality
    // Check result
    assertEquals(fixture.getExpectedNumberOfMaskedColumns(), maskingFileIterator.numberOfColumns());
  }

  /**
   * Test method for {@link MaskingFileIterator#relationName()}
   */
  @Test
  public void testRelationName() {
    assertEquals(fixture.getExpectedRelationName(), maskingFileIterator.relationName());
  }

  /**
   * Test method for {@link MaskingFileIterator#columnNames()}
   */
  @Test
  public void testColumnNames() {
    // Execute functionality
    // Check result
    assertEquals(fixture.getExpectedMaskedColumnNames(), maskingFileIterator.columnNames());
  }

  /**
   * Test method for {@link MaskingFileIterator#columnNames()}
   *
   * Generated masked headers do not necessarily have consecutive numbers. The numbers of the
   * generated headers correspond to the unmasked headers.
   */
  @Test
  public void testColumnNamesGenerated()
      throws InputGenerationException, InputIterationException, InvalidMaskException {
    // Setup
    CsvFileOneLineFixture fixtureSeparator = new CsvFileOneLineFixture(';');

    MaskingFileIterator
        maskingFileIteratorWithHeader =
        new MaskingFileIterator(fixture.getTestData(), 0, 2);
    MaskingFileIterator
        maskingFileIteratorWithoutHeader =
        new MaskingFileIterator(fixture.getTestDataWithoutHeader(), 0, 2);

    // Execute functionality
    // Check result
    assertEquals(fixture.getExpectedMaskedColumnNames(),
                 maskingFileIteratorWithHeader.columnNames());
    assertEquals(fixture.getExpectedMaskedDefaultColumnNames(),
                 maskingFileIteratorWithoutHeader.columnNames());
    assertEquals(fixture.getExpectedMaskedStrings(), maskingFileIteratorWithoutHeader.next());
  }

}
