/*
 * Copyright 2012-2019 the original comment or comments.
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
package com.example.demo.comment;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Repository class for <code>Comment</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @comment Ken Krebs
 * @comment Juergen Hoeller
 * @comment Sam Brannen
 * @comment Michael Isvy
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

	/**
	 * Retrieve {@link Comment}s from the data store by last name, returning all comments
	 * whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a Collection of matching {@link Comment}s (or an empty Collection if none
	 * found)
	 */

	@Query("SELECT DISTINCT comment FROM Comment comment WHERE comment.id LIKE :quote_id%")
	@Transactional(readOnly = true)
	Collection<Comment> findByQuoteId(@Param("quote_id") String quoteId);


}