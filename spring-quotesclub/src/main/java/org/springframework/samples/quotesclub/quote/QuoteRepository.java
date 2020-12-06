package org.springframework.samples.quotesclub.quote;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.cloud.openfeign.FeignClient;


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
@FeignClient(value = "quote-service")
public interface QuoteRepository  {

	@PostMapping("/quotes")
	Quote save(Quote quote);


	@GetMapping(path = { "/quotes" }, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	Collection<Quote> findAll();

	@GetMapping(path = {"/quotes/{id}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Quote findById(@PathVariable("id") Integer id);


	@DeleteMapping("/quotes/{id}")
	void deleteById(@PathVariable("id") Integer id);

}