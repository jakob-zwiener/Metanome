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

package de.metanome.backend.resources;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Test;

import de.metanome.backend.algorithm_loading.AlgorithmLoadingException;
import de.metanome.backend.results_db.Algorithm;
import de.metanome.backend.results_db.EntityStorageException;
import de.metanome.backend.results_db.Execution;
import de.metanome.backend.results_db.FileInput;
import de.metanome.backend.results_db.HibernateUtil;
import de.metanome.backend.results_db.Result;
import de.metanome.backend.results_db.ResultType;

public class ResultResourceTest {

  ResultResource resultResource = new ResultResource();
  ExecutionResource executionResource = new ExecutionResource();
  FileInputResource fileInputResource = new FileInputResource();

  /**
   * Test method for {@link de.metanome.backend.resources.ResultResource#store(de.metanome.backend.results_db.Result)} and
   * {@link de.metanome.backend.resources.ResultResource#get(long)}
   * @throws Exception
   */
  @Test
  public void testStoreAndGet() throws Exception {
    // Setup
    HibernateUtil.clear();

    // Expected values
    // Execute functionality
    Result expectedResult = new Result("result_file");

    expectedResult = resultResource.store(expectedResult);
    Result actualResult = resultResource.get(expectedResult.getId());

    // Check result
    assertEquals(expectedResult, actualResult);

    // Cleanup
    HibernateUtil.clear();
  }

  @Test
  public void testDelete() throws Exception {
    // Setup
    HibernateUtil.clear();

    // Expected values
    Result expectedResult = new Result("result_file");
    expectedResult = resultResource.store(expectedResult);

    // Check pre condition
    List<Result> actualResults = resultResource.getAll();
    assertFalse(actualResults.isEmpty());

    // Execute functionality
    resultResource.delete(expectedResult.getId());

    // Check result
    actualResults = resultResource.getAll();
    assertTrue(actualResults.isEmpty());

    // Cleanup
    HibernateUtil.clear();
  }

  @Test
  public void testGetAll() throws Exception {
    // Setup
    HibernateUtil.clear();

    // Expected values
    Result expectedResult1 = new Result("result_file_1");
    Result expectedResult2 = new Result("result_file_2");
    expectedResult1 = resultResource.store(expectedResult1);
    expectedResult2 = resultResource.store(expectedResult2);

    // Execute functionality
    List<Result> actualResults = resultResource.getAll();

    // Check result
    assertTrue(actualResults.size() == 2);
    assertThat(actualResults,
      IsIterableContainingInAnyOrder.containsInAnyOrder(expectedResult1, expectedResult2));

    // Cleanup
    HibernateUtil.clear();
  }

  /**
   * Test method for {@link de.metanome.backend.resources.ResultResource#store(de.metanome.backend.results_db.Result)}
   * and {@link de.metanome.backend.resources.ResultResource#get(long)}
   * <p/>
   * Tests persistence of a Result with an attached {@link de.metanome.backend.results_db.Execution}.
   */
  @Test
  public void testPersistenceWithExecution()
    throws EntityStorageException, AlgorithmLoadingException
  {
    // Setup
    HibernateUtil.clear();

    // Store prerequisite objects in the database
    Algorithm algorithm = new Algorithm("example_ind_algorithm.jar");
    AlgorithmResource algorithmResource = new AlgorithmResource();
    algorithmResource.store(algorithm);

    Execution execution = new Execution(algorithm, new Date().getTime());
    ExecutionResource executionResource = new ExecutionResource();
    executionResource.store(execution);

    // Expected values
    String filePath = "some file name";
    Result expectedResult = new Result(filePath);
    expectedResult.setExecution(execution);

    // Execute functionality
    expectedResult = resultResource.store(expectedResult);
    Result actualResult = resultResource.get(expectedResult.getId());

    // Check result
    assertEquals(expectedResult, actualResult);

    // Cleanup
    HibernateUtil.clear();
  }

  /**
   * Test method for {@link de.metanome.backend.resources.ResultResource#update(de.metanome.backend.results_db.Result)}
   * Results should be storable and updatable.
   */
  @Test
  public void testUpdate() throws EntityStorageException {
    // Setup
    HibernateUtil.clear();

    // Expected values
    Result result = new Result("old file");

    // Execute functionality
    Result actualResult = resultResource.store(result);

    // Check result
    assertEquals(result, actualResult);

    // Execute functionality
    result.setFileName("new file").setType(ResultType.OD);
    resultResource.update(result);

    // Check result
    actualResult = resultResource.get(result.getId());
    assertEquals(result, actualResult);

    // Cleanup
    HibernateUtil.clear();
  }

  /**
   * Test method for {@link de.metanome.backend.resources.ResultResource#getResultsForFileInput(long)}
   * Only one result for each result type should be returned.
   * @throws Exception
   */
  @Test
  public void testGetResultsForFileInput() throws Exception {
    // Setup
    HibernateUtil.clear();

    FileInput input = new FileInput("file");
    input = fileInputResource.store(input);

    Result expectedResult1 = new Result("result_file_1");
    expectedResult1.setType(ResultType.CUCC);
    Result expectedResult2 = new Result("result_file_2");
    expectedResult2.setType(ResultType.CUCC);
    Result expectedResult3 = new Result("result_file_3");
    expectedResult3.setType(ResultType.UCC);

    Execution execution = new Execution(null);
    execution.addInput(input);
    execution.addResult(expectedResult1);
    execution.addResult(expectedResult2);
    execution.addResult(expectedResult3);
    executionResource.store(execution);

    // Execute functionality
    List<Result> actualResults = resultResource.getResultsForFileInput(input.getId());

    // Check result
    assertTrue(actualResults.size() == 2);
    assertTrue(actualResults.contains(expectedResult3));
    assertTrue(actualResults.contains(expectedResult2));

    // Cleanup
    HibernateUtil.clear();
  }

}
