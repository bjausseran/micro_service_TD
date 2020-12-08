package com.example.demo.quote;

import com.example.demo.quote.QuoteRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;


import com.example.demo.quote.Quote;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
public class QuoteController {

	private QuoteRepository quotes;
	
	@PostMapping("/quotes/new")
	public Quote addQuote(@RequestParam("authorId") Integer authorId, 
						@RequestParam("content") String content) {
		Quote quote = new Quote();
		quote.setContent(content);
		quote.setAuthorId(authorId);
		return quotes.save(quote);
	}
	
	@PostMapping("/quotes/")
	public Quote save(@RequestBody Quote quote) {
		return quotes.save(quote);
	}

}
