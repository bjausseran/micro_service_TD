package org.springframework.samples.quotesclub.comment;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.samples.quotesclub.author.Author;
import org.springframework.samples.quotesclub.quote.Quote;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "comment-service")
public interface CommentRepository{

	@GetMapping(path = {"/comments/{id}"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Comment findById(@PathVariable("id") Integer id);

	@PostMapping(path = {"/comments/"})
	Comment save(@RequestBody Comment comment);

	@DeleteMapping("/comments/{id}")
	void deleteById(@PathVariable("id") Integer id);

	@GetMapping(path = { "/comments/" }, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	Collection<Comment> findAll();
}
