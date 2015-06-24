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

package de.metanome.algorithms.testing.example_fd_algorithm;

import java.util.ArrayList;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.ColumnCombination;
import de.metanome.algorithm_integration.ColumnIdentifier;
import de.metanome.algorithm_integration.algorithm_types.DatabaseConnectionParameterAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.FileInputParameterAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.FunctionalDependencyAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.ListBoxParameterAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.StringParameterAlgorithm;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementDatabaseConnection;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementFileInput;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementListBox;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementString;
import de.metanome.algorithm_integration.input.DatabaseConnectionGenerator;
import de.metanome.algorithm_integration.input.FileInputGenerator;
import de.metanome.algorithm_integration.result_receiver.CouldNotReceiveResultException;
import de.metanome.algorithm_integration.result_receiver.FunctionalDependencyResultReceiver;
import de.metanome.algorithm_integration.results.FunctionalDependency;

public class ExampleAlgorithm
  implements FunctionalDependencyAlgorithm, StringParameterAlgorithm, FileInputParameterAlgorithm,
  ListBoxParameterAlgorithm, DatabaseConnectionParameterAlgorithm
{

  public final static String LISTBOX_IDENTIFIER = "column names";
  public final static String STRING_IDENTIFIER = "pathToOutputFile";
  public final static String CSVFILE_IDENTIFIER = "input file";
  public final static String DATABASE_IDENTIFIER = "DB-connection";
  protected String path = null;
  protected String selectedColumn = null;
  protected DatabaseConnectionGenerator inputGenerator;
  protected FunctionalDependencyResultReceiver resultReceiver;

  @Override
  public ArrayList<ConfigurationRequirement> getConfigurationRequirements() {
    ArrayList<ConfigurationRequirement> configurationRequirement = new ArrayList<>();

    ConfigurationRequirementString requirementString =
      new ConfigurationRequirementString(STRING_IDENTIFIER);
    ConfigurationRequirementFileInput requirementFileInput =
      new ConfigurationRequirementFileInput(CSVFILE_IDENTIFIER);
    ConfigurationRequirementDatabaseConnection requirementDatabaseConnection =
      new ConfigurationRequirementDatabaseConnection(DATABASE_IDENTIFIER);

    ArrayList<String> listBoxValues = new ArrayList<>();
    listBoxValues.add("column 1");
    listBoxValues.add("column 2");
    listBoxValues.add("column 3");
    ConfigurationRequirementListBox
      requirementListBox =
      new ConfigurationRequirementListBox(LISTBOX_IDENTIFIER, listBoxValues, 1);

    requirementString.setRequired(false);
    requirementFileInput.setRequired(false);
    requirementDatabaseConnection.setRequired(false);
    requirementListBox.setRequired(false);

    configurationRequirement.add(requirementString);
    configurationRequirement.add(requirementListBox);
    configurationRequirement.add(requirementFileInput);
    configurationRequirement.add(requirementDatabaseConnection);

    return configurationRequirement;
  }

  @Override
  public void execute() {
    try {
      resultReceiver.receiveResult(
        new FunctionalDependency(
          new ColumnCombination(
            new ColumnIdentifier("table1", "column1"),
            new ColumnIdentifier("table1", "column2")),
          new ColumnIdentifier("table1", "column5")
        )
      );
    }
    catch (CouldNotReceiveResultException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void setResultReceiver(FunctionalDependencyResultReceiver resultReceiver) {
    this.resultReceiver = resultReceiver;
  }

  @Override
  public void setStringConfigurationValue(String identifier, String... values)
    throws AlgorithmConfigurationException
  {
    if (!identifier.equals(STRING_IDENTIFIER)) {
      throw new AlgorithmConfigurationException("Incorrect identifier or value list length.");
    }
  }

  @Override
  public void setFileInputConfigurationValue(String identifier,
                                             FileInputGenerator... values)
    throws AlgorithmConfigurationException
  {
    if (!identifier.equals(CSVFILE_IDENTIFIER)) {
      throw new AlgorithmConfigurationException("Incorrect identifier or value list length.");
    }
  }

  @Override
  public void setListBoxConfigurationValue(String identifier, String... selectedValues)
    throws AlgorithmConfigurationException
  {
    if (!identifier.equals(LISTBOX_IDENTIFIER)) {
      throw new AlgorithmConfigurationException("Incorrect identifier or value list length.");
    }
  }

  @Override
  public void setDatabaseConnectionGeneratorConfigurationValue(String identifier,
                                                               DatabaseConnectionGenerator... values)
    throws AlgorithmConfigurationException
  {

    if (!identifier.equals(DATABASE_IDENTIFIER)) {
      throw new AlgorithmConfigurationException("Incorrect identifier or value list length.");
    }
  }
}
