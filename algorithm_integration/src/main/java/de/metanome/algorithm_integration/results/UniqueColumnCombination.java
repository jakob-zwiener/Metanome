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

package de.metanome.algorithm_integration.results;


import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonTypeName;

import de.metanome.algorithm_integration.ColumnCombination;
import de.metanome.algorithm_integration.ColumnIdentifier;
import de.metanome.algorithm_integration.result_receiver.CouldNotReceiveResultException;
import de.metanome.algorithm_integration.result_receiver.OmniscientResultReceiver;


/**
 * Represents a unique column combination.
 * @author Jakob Zwiener
 */
@JsonTypeName("UniqueColumnCombination")
public class UniqueColumnCombination implements Result {

  protected ColumnCombination columnCombination;

  /**
   * Exists for GWT serialization.
   */
  protected UniqueColumnCombination() {
    this.columnCombination = new ColumnCombination();
  }

  /**
   * Constructs a {@link UniqueColumnCombination} from a number of {@link ColumnIdentifier}s.
   * @param columnIdentifier the column identifier comprising the column combination
   */
  public UniqueColumnCombination(ColumnIdentifier... columnIdentifier) {
    this.columnCombination = new ColumnCombination(columnIdentifier);
  }

  /**
   * Constructs a {@link UniqueColumnCombination} from a {@link ColumnCombination}.
   * @param columnCombination a supposedly unique column combination
   */
  public UniqueColumnCombination(ColumnCombination columnCombination) {
    this.columnCombination = columnCombination;
  }

  /**
   * @return the column combination
   */
  public ColumnCombination getColumnCombination() {
    return columnCombination;
  }

  public void setColumnCombination(ColumnCombination columnCombination) {
    this.columnCombination = columnCombination;
  }

  @Override
  @XmlTransient
  public void sendResultTo(OmniscientResultReceiver resultReceiver)
    throws CouldNotReceiveResultException
  {
    resultReceiver.receiveResult(this);
  }

  @Override
  public String toString() {
    return columnCombination.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime
      * result
      + ((columnCombination == null) ? 0 : columnCombination
      .hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UniqueColumnCombination other = (UniqueColumnCombination) obj;
    if (columnCombination == null) {
      if (other.columnCombination != null) {
        return false;
      }
    }
    else if (!columnCombination.equals(other.columnCombination)) {
      return false;
    }
    return true;
  }

}
