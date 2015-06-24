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

package de.metanome.algorithm_integration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Abstract super class for the composite pattern used for column conditions.
 * @author Jens Ehrlich
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ColumnConditionAnd.class, name = "ColumnConditionAnd"),
  @JsonSubTypes.Type(value = ColumnConditionOr.class, name = "ColumnConditionOr"),
  @JsonSubTypes.Type(value = ColumnConditionValue.class, name = "ColumnConditionValue")
})
public interface ColumnCondition extends Serializable, Comparable<ColumnCondition> {

  String OPEN_BRACKET = "[";
  String CLOSE_BRACKET = "]";
  String AND = "^";
  String OR = "v";
  String NOT = "\u00AC";


  /**
   * Returns the condition as {@link java.lang.String}.
   * @return condition
   */
  String toString();

  /**
   * Adds a child to the called {@link de.metanome.algorithm_integration.ColumnCondition}.
   * @param value child to by added
   * @return this
   */
  ColumnCondition add(ColumnCondition value);

  /**
   * Returns the set coverage or calculates the coverage of the children if no coverage was set.
   * The calculation may be wrong if there are overlapping {@link de.metanome.algorithm_integration.ColumnConditionAnd} as child of a {@link de.metanome.algorithm_integration.ColumnConditionOr}.
   * @return coverage between 0 and 100
   */
  float getCoverage();

  /**
   * Sets the coverage on the element.
   * @param coverage to be set
   */
  void setCoverage(float coverage);

  /**
   * Returns all {@link de.metanome.algorithm_integration.ColumnIdentifier} that occur in the condition.
   * @return sorted set of contained {@link de.metanome.algorithm_integration.ColumnIdentifier}s
   */
  TreeSet<ColumnIdentifier> getContainedColumns();

  /**
   * Traverses all children and builds a pattern tableau, which represents the condition.
   * Each element in the list represents a OR condition and each entry in the map a AND condition.
   * The pattern tableau is only correct if the {@link de.metanome.algorithm_integration.ColumnCondition}
   * is in disjunctive normal form.
   * @return pattern tableau
   */
  List<Map<ColumnIdentifier, String>> getPatternConditions();

  boolean isNegated();

  void setNegated(boolean isNegated);

}
