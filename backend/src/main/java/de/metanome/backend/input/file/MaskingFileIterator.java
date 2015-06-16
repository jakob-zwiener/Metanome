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

import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.RelationalInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Masks columns of a {@link FileIterator}. Only the specified columns will be in the masked
 * iterator. Columns are specified by their column index.
 *
 * @author Jakob Zwiener
 */
public class MaskingFileIterator implements RelationalInput {

  protected final FileIterator fileIterator;
  protected final int[] mask;

  public MaskingFileIterator(FileIterator fileIterator, int... mask) throws InvalidMaskException {

    this.fileIterator = fileIterator;

    if (mask == null) {
      throw new InvalidMaskException("The mask should not be null.");
    }

    if (mask.length < 1) {
      throw new InvalidMaskException("The mask should not be empty.");
    }

    for (int columnIndex : mask) {
      if ((columnIndex < 0) || (columnIndex >= this.fileIterator.numberOfColumns())) {
        throw new InvalidMaskException(
            String.format("Column index %d is invalid. Number of columns is %d.", columnIndex,
                          this.fileIterator.numberOfColumns()));
      }
    }

    this.mask = mask;
  }

  @Override
  public boolean hasNext() throws InputIterationException {
    return fileIterator.hasNext();
  }

  @Override
  public List<String> next() throws InputIterationException {
    return maskLine(fileIterator.next());
  }

  @Override
  public int numberOfColumns() {
    return mask.length;
  }

  @Override
  public String relationName() {
    return fileIterator.relationName();
  }

  @Override
  public List<String> columnNames() {
    return maskLine(fileIterator.columnNames());
  }

  /**
   * Masks a line.
   *
   * @param line the line to mask
   * @return the masked line
   */
  protected List<String> maskLine(List<String> line) {
    List<String> maskedLine = new ArrayList<>(mask.length);

    for (int columnIndex : mask) {
      maskedLine.add(line.get(columnIndex));
    }

    return Collections.unmodifiableList(maskedLine);
  }

  @Override
  public void close() throws Exception {

  }
}
