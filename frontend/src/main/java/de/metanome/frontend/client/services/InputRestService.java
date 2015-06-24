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

import de.metanome.backend.results_db.Input;

@Path("/api/inputs")
public interface InputRestService extends RestService {

  @GET void listInputs(MethodCallback<List<Input>> callback);

  @GET
  @Path("/relationalInputs") void listRelationalInputs(MethodCallback<List<Input>> callback);

  @GET
  @Path("/get/{id}") void getInput(@PathParam("id") long id, MethodCallback<Input> callback);

  @POST
  @Path("/store") void storeInput(Input fileInput, MethodCallback<Input> callback);

  @DELETE
  @Path("/delete/{id}") void deleteInput(@PathParam("id") long id, MethodCallback<Void> callback);

  @POST
  @Path("/update") void updateInput(Input input, MethodCallback<Input> callback);

}
