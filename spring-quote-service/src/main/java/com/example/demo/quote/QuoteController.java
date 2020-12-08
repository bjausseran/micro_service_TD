package com.example.demo.quote;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class QuoteController {

	@Autowired
	private QuoteRepository quotes;
	
	@PostMapping("authors/{authorId}/quotes/new")
	public Quote addQuote(@PathVariable("authorId") Integer authorId, 
						@RequestParam("content") String content) {
		Quote quote = new Quote();
		quote.setContent(content);
		quote.setAuthorId(authorId);
		return quotes.save(quote);
	}
	@GetMapping("/quotes")
	public Iterable<Quote> getAuthors() {
		return quotes.findAll();
	}
	
	@PostMapping("/quotes/")
	public Quote save(@RequestBody Quote quote) {
		return quotes.save(quote);
	}
	@GetMapping("/quotes/{id}")
	public Optional<Quote> getQuoteById(@PathVariable("id") Integer id) {
		return quotes.findById(id);
	}

    @DeleteMapping("/quotes/{id}")
    public void deleteQuote(@PathVariable("id") Integer id) {
        quotes.deleteById(id);
    }
}
