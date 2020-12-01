package com.example.demo.quote;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;


/**
 * Repository class for <code>Pet</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface QuoteInterface extends CrudRepository<Quote, Integer> {


    @Transactional(readOnly = true)
    @Cacheable("quotes")
    Collection<Quote> findAll() throws DataAccessException;


}