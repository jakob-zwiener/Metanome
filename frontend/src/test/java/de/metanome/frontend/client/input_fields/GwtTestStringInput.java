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

package de.metanome.frontend.client.input_fields;

import com.google.gwt.junit.client.GWTTestCase;

import de.metanome.frontend.client.helpers.InputValidationException;


public class GwtTestStringInput extends GWTTestCase {

  /**
   * Test method for {@link de.metanome.frontend.client.input_fields.StringInput#getValue()} and for
   * {@link de.metanome.frontend.client.input_fields.StringInput#setValue(String)}
   */
  public void testGetSetValues() throws InputValidationException {
    StringInput input = new StringInput(false, false);

    input.setValue("test");
    assertEquals("test", input.getValue());
  }

  /**
   * Test method for {@link de.metanome.frontend.client.input_fields.StringInput#getValue()} and for
   * {@link de.metanome.frontend.client.input_fields.StringInput#setValue(String)}
   */
  public void testGetSetRequiredValues() {
    StringInput input = new StringInput(false, true);

    input.setValue("test");
    try {
      input.getValue();
    }
    catch (InputValidationException e) {
      // should throw an exception
    }
  }

  @Override
  public String getModuleName() {
    return "de.metanome.frontend.MetanomeTest";
  }

}
