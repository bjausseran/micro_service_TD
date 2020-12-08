/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.quotesclub.author;

import java.util.Collection;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.quotesclub.quote.Quote;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Repository class for <code>Author</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@FeignClient(value = "author-service")
public interface AuthorRepository {

	/**
	 * Retrieve {@link Author}s from the data store by last name, returning all owners
	 * whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a Collection of matching {@link Author}s (or an empty Collection if none
	 * found)
	 */

	@GetMapping(path = {"/authors/findByLastName/{lastName}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	Collection<Author> findByLastName(@PathVariable("last_name") String lastName);

	@GetMapping(path = {"/authors/{id}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Optional<Author> findById(@PathVariable("id") Integer id);

	@PostMapping(path = {"/authors/"})
	Author save(@RequestBody Author author);

	@PostMapping(path = {"/authors/"})
	Author save(@RequestParam Integer authorId);
	
	@GetMapping(path = {"/authors/{authorId}/quotes"})	
	public Optional<Author> findById2(@PathVariable("authorId") Integer id);

	
	@GetMapping(path = {"/authors/"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	Collection<Author> findAll();

	@DeleteMapping(path = {"/authors/{id}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Optional<Author> deleteById(@PathVariable("id") Integer id);

	
}
