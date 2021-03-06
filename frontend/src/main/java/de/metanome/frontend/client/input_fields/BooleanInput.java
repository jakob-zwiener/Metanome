/*
 * Copyright 2014 by the Metanome project
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

import com.google.gwt.user.client.ui.CheckBox;

/**
 * A wrapper for a checkbox that can contain a remove button. If the remove button is clicked, the
 * checkbox is removed from the parent widget.
 *
 * @author Claudia Exeler
 */
public class BooleanInput extends InputField {

  protected CheckBox checkbox;

  /**
   * @param optional If true, a remove button will be rendered, to remove this widget from its
   *                 parent.
   * @param required If true, a value has to be set.
   */
  public BooleanInput(boolean optional, boolean required) {
    super(optional, required);

    this.checkbox = new CheckBox();
    this.add(this.checkbox);
  }

  /**
   * @return the value of its checkbox
   */
  public boolean getValue() {
    return this.checkbox.getValue();
  }

  /**
   * Sets the value of the checkbox
   *
   * @param value the value to set the checkbox to
   */
  public void setValue(boolean value) {
    this.checkbox.setValue(value);
  }
}
