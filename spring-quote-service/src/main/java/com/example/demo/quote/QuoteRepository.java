package com.example.demo.quote;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



public interface QuoteRepository extends CrudRepository<Quote, Integer> {


	
	@Query("SELECT DISTINCT quote FROM Quote quote WHERE quote.authorId = :authorId")
	@Transactional(readOnly = true)
	@GetMapping(path = {"/authors/{authorId}/quotes"})
	Collection<Quote> findByAuthor(@PathVariable("authorId") Integer authorId);
	
}
