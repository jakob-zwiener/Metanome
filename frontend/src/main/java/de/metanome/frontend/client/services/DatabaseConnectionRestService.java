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

import de.metanome.backend.results_db.DatabaseConnection;

@Path("/api/dbConnections")
public interface DatabaseConnectionRestService extends RestService {

  @GET void listDatabaseConnections(MethodCallback<List<DatabaseConnection>> callback);

  @GET
  @Path("/get/{id}") void getDatabaseConnection(@PathParam("id") long id, MethodCallback<DatabaseConnection> callback);

  @POST
  @Path("/store") void storeDatabaseConnection(DatabaseConnection dbConnection,
                                               MethodCallback<DatabaseConnection> callback);

  @DELETE
  @Path("/delete/{id}") void deleteDatabaseConnection(@PathParam("id") long id, MethodCallback<Void> callback);

  @POST
  @Path("/update") void updateDatabaseConnection(DatabaseConnection databaseConnection,
                                                 MethodCallback<DatabaseConnection> callback);
}


