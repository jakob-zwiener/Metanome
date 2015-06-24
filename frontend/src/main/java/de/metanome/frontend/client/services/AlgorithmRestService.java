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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import de.metanome.backend.results_db.Algorithm;

@Path("/api/algorithms")
public interface AlgorithmRestService extends RestService {

  @GET void listAlgorithms(MethodCallback<List<Algorithm>> callback);

  @GET
  @Path("/inclusion_dependency_algorithms/")
  void listInclusionDependencyAlgorithms(MethodCallback<List<Algorithm>> callback);

  @GET
  @Path("/functional_dependency_algorithms/")
  void listFunctionalDependencyAlgorithms(MethodCallback<List<Algorithm>> callback);

  @GET
  @Path("/unique_column_combination_algorithms/")
  void listUniqueColumnCombinationsAlgorithms(MethodCallback<List<Algorithm>> callback);

  @GET
  @Path("/conditional_unique_column_combination_algorithms/") void listConditionalUniqueColumnCombinationsAlgorithms(
    MethodCallback<List<Algorithm>> callback);

  @GET
  @Path("/order_dependency_algorithms/") void listOrderDependencyAlgorithms(MethodCallback<List<Algorithm>> callback);

  @GET
  @Path("/basic_statistics_algorithms/") void listBasicStatisticsAlgorithms(MethodCallback<List<Algorithm>> callback);

  @GET
  @Path("/files/") void listAvailableAlgorithmFiles(MethodCallback<List<String>> callback);

  @GET
  @Path("/get/{id}") void getAlgorithm(@PathParam("id") long id, MethodCallback<Algorithm> callback);

  @POST
  @Path("/store") void storeAlgorithm(Algorithm algorithm, MethodCallback<Algorithm> callback);

  @DELETE
  @Path("/delete/{id}") void deleteAlgorithm(@PathParam("id") long id, MethodCallback<Void> callback);

  @POST
  @Path("/update") void updateAlgorithm(Algorithm algorithm, MethodCallback<Algorithm> callback);
}
