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

package de.metanome.frontend.client.services;


import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import de.metanome.algorithm_integration.results.Result;
import de.metanome.backend.resources.AlgorithmExecutionParams;
import de.metanome.backend.results_db.Execution;
import de.metanome.backend.results_db.ResultType;

@Path("/api/algorithm_execution")
public interface AlgorithmExecutionRestService extends RestService {

  @POST void executeAlgorithm(AlgorithmExecutionParams params,
                              MethodCallback<Execution> callback);

  @GET
  @Path("/fetch_progress/{identifier}") void fetchProgress(@PathParam("identifier") String executionIdentifier,
                                                           MethodCallback<Float> callback);

  @GET
  @Path("/result_cache/{identifier}") void getCacheResults(@PathParam("identifier") String algorithmName,
                                                           MethodCallback<List<Result>> callback);

  @GET
  @Path("/result_counter/{identifier}") void getCounterResults(@PathParam("identifier") String executionIdentifier,
                                                               MethodCallback<Map<ResultType, Integer>> callback);

  @GET
  @Path("/result_printer/{identifier}") void getPrinterResults(@PathParam("identifier") String executionIdentifier,
                                                               MethodCallback<List<Result>> callback);

  @GET
  @Path("/read_result/{file_name}/{type}") void readResultFromFile(@PathParam("file_name") String fileName,
                                                                   @PathParam("type") String type,
                                                                   MethodCallback<List<Result>> callback);

}
