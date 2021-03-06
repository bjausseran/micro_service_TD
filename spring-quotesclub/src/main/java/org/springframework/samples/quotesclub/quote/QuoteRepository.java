package org.springframework.samples.quotesclub.quote;

import java.util.Collection;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@FeignClient(value = "quote-service")
public interface QuoteRepository  {

	@PostMapping(path = {"/quotes/"})
	Quote save(@RequestBody Quote quote);


	@Query("SELECT DISTINCT quote.content FROM Quote quote.content WHERE quote.authorId = :authorId")
	@Transactional(readOnly = true)
	@GetMapping(path = {"/authors/{authorId}/quotes"})
	Collection<Quote> findByAuthor(@PathVariable("authorId") Integer authorId);

	@Query("SELECT DISTINCT quote.content FROM Quote quote.content WHERE quote.authorId = :authorId")
	@Transactional(readOnly = true)
	@GetMapping(path = {"/authors/{authorId}/quoteList"})
	Collection<String> findByAuthorString(@PathVariable("authorId") Integer authorId);
	


	@GetMapping(path = { "/quotes/" }, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	Collection<Quote> findAll();

	@GetMapping(path = {"/quotes/{id}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Quote findById(@PathVariable("id") Integer id);


	@DeleteMapping("/quotes/{id}")
	void deleteById(@PathVariable("id") Integer id);

}