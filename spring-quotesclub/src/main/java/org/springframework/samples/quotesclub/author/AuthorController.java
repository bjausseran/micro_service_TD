package org.springframework.samples.quotesclub.author;

import org.springframework.samples.quotesclub.comment.CommentRepository;
import org.springframework.samples.quotesclub.quote.Quote;
import org.springframework.samples.quotesclub.quote.QuoteRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

	public AuthorController(AuthorRepository authors, QuoteRepository quotes, CommentRepository comments) {
		this.authors = authors;
		this.quotes = quotes;
		this.comments = comments;
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

	@PostMapping("/authors/{authorId}/addquote")
	public String addQuoteToAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("quoteId") Integer quoteId) {
		Optional<Author> authorOpt = authors.findById(authorId);
		Author author = null;
		if (authorOpt.isPresent()) {
			author = authorOpt.get();
			author.addQuote(quotes.findById(quoteId));
			author.addQuoteString(quotes.findById(quoteId).getContent());
			authors.save(authorId);
		}
		return author.getQuotes().toString();
	}
	
	@PostMapping("/authors/*/addComment")
	public void addComToAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("commentId") Integer commentId) {
		Optional<Author> authorOpt = authors.findById(authorId);
		if (authorOpt.isPresent()) {
			Author author = authorOpt.get();
			author.addComment(comments.findById(commentId));
			authors.save(author);
		}
		
	}
}