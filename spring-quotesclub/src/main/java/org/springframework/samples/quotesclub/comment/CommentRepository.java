package org.springframework.samples.quotesclub.comment;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.samples.quotesclub.author.Author;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "comment-service")
public interface CommentRepository{

	@PostMapping("/comments")
	void save(@RequestParam("date") LocalDate date, @RequestParam("content") String content,
			@RequestParam("quote_id") Integer quoteId);

	@GetMapping(path = {"/comments/findByQuoteId/{quoteId}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Collection<Comment> findByQuoteId(@PathVariable("quote_id") Integer quoteId);

	@GetMapping(path = {"/authors/{id}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Author findById(@PathVariable("id") Integer id);
}
