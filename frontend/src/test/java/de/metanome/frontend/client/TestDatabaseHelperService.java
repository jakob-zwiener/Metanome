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

package de.metanome.frontend.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.metanome.backend.results_db.Algorithm;
import de.metanome.backend.results_db.EntityStorageException;

/**
 * {@link TestDatabaseHelperService}
 * @author Jakob Zwiener
 */
@RemoteServiceRelativePath("testService")
public interface TestDatabaseHelperService extends RemoteService {

  void resetDatabase();

  Algorithm storeAlgorithmInDatabase(Algorithm algorithm) throws EntityStorageException;

}
