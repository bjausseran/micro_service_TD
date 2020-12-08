package org.springframework.samples.quotesclub.author;

import org.springframework.samples.quotesclub.comment.CommentRepository;
import org.springframework.samples.quotesclub.quote.Quote;
import org.springframework.samples.quotesclub.quote.QuoteRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@RestController
class AuthorController {

	private final AuthorRepository authors;

	private QuoteRepository quotes;
	
	private CommentRepository comments;

	private Collection<Quote> quoteList;

	public AuthorController(AuthorRepository authors, QuoteRepository quotes) {
		this.authors = authors;
		this.quotes = quotes;
	}

	@GetMapping("/authors")
	public Iterable<Author> GetAllAuthors() {

		return authors.findAll();
		
	}

	@PostMapping("/authors/new")
	public Author addAuthor(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("city") String city) {
		Author author = new Author();
		author.setFirstName(firstName);
		author.setLastName(lastName);
		author.setCity(city);
		return authors.save(author);
	}
	
	@PostMapping("/authors/")
	public Author save(@RequestBody Author author) {
		return authors.save(author);
	}

	/**
	 * Custom handler for displaying an author.
	 * @param authorId the ID of the author to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/authors/{id}")
	public Optional<Author> getAuthorById(@PathVariable("id") Integer id) {
		return authors.findById(id);
	}
	@DeleteMapping("/authors/{id}")
	public Optional<Author> deleteAuthorById(@PathVariable("id") Integer id) {
		return authors.deleteById(id);
	}
	
}