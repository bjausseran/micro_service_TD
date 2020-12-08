package org.springframework.samples.quotesclub.quote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.samples.quotesclub.quote.Quote;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.validation.BindingResult;
import org.springframework.samples.quotesclub.author.AuthorRepository;
import org.springframework.samples.quotesclub.author.Author;
import org.springframework.samples.quotesclub.comment.Comment;

class Output{
	
	public List<String> elements = new ArrayList<String>();
	
}

@RestController
class QuoteController{

	private final AuthorRepository authors;
    private final QuoteRepository quotes;
	private Collection<Comment> commentList;

    public QuoteController(QuoteRepository quotes, AuthorRepository authors) {
        this.quotes = quotes;
        this.authors = authors;
    }

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
    @GetMapping("/quotes")
    public Iterable<Quote> getQuotes() {
        return quotes.findAll();
    }


	@PostMapping("authors/{authorId}/quotes/new")
	public Quote addQuote(@PathVariable("authorId") Integer authorId, 
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

    @DeleteMapping("/quotes/{id}")
    public void deleteQuote(@PathVariable("id") Integer id) {
        quotes.deleteById(id);
    }

	@GetMapping("/quotes/{id}")
	public Quote getQuoteById(@PathVariable("id") Integer id) {
		return quotes.findById(id);
	}

	@GetMapping(path = {"/authors/{authorId}/quotes"})
	public Output getAuthorQuotes(@PathVariable("authorId") Integer authorId) {
		
		Output output = new Output();
		Optional<Author> authorEx = authors.findById(authorId);
		Author author = authorEx.get();
		output.elements.add(author.getFirstName()); 
		output.elements.add(author.getLastName()); 
		output.elements.add(author.getCity()); 
		List<Quote> quotesAuth = new ArrayList<Quote>();
		quotesAuth.addAll(quotes.findByAuthor(authorId));
		for (int i = 0; i < quotes.findByAuthor(authorId).size(); i++) {
			output.elements.add(quotesAuth.get(i).getContent());
			}
		
		
		return output;
	}

	@GetMapping(path = {"/authors/{authorId}/quoteList"})
	public Collection<String> getAuthorQuotesString(@PathVariable("authorId") Integer authorId) {
		return quotes.findByAuthorString(authorId);
	}
	


}