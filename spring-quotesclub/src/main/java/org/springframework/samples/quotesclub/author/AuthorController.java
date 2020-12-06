package org.springframework.samples.quotesclub.author;

import org.springframework.samples.quotesclub.comment.CommentRepository;
import org.springframework.samples.quotesclub.quote.Quote;
import org.springframework.samples.quotesclub.quote.QuoteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
class AuthorController {

	private static final String VIEWS_AUTHOR_CREATE_OR_UPDATE_FORM = "authors/createOrUpdateAuthorForm";

	private final AuthorRepository authors;

	private QuoteRepository quotes;
	
	private CommentRepository comments;

	private Collection<Quote> quoteList;

	public AuthorController(AuthorRepository authors, QuoteRepository quotes) {
		this.authors = authors;
		this.quotes = quotes;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/authors/new")
	public String initCreationForm(Map<String, Object> model) {
		Author author = new Author();
		model.put("author", author);
		return VIEWS_AUTHOR_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/authors/new")
	public String processCreationForm(@Valid Author author, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_AUTHOR_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.authors.save(author);
			return "redirect:/authors/" + author.getId();
		}
	}

	@GetMapping("/authors/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("author", new Author());
		return "authors/findAuthors";
	}

	@GetMapping("/authors")
	public String processFindForm(Author author, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /authors to return all records
		if (author.getLastName() == null) {
			author.setLastName(""); // empty string signifies broadest possible search
		}

		// find authors by last name
		Collection<Author> results = this.authors.findByLastName(author.getLastName());
		if (results.isEmpty()) {
			// no authors found
			result.rejectValue("lastName", "notFound", "not found");
			return "authors/findAuthors";
		}
		else if (results.size() == 1) {
			// 1 author found
			author = results.iterator().next();
			return "redirect:/authors/" + author.getId();
		}
		else {
			// multiple authors found
			model.put("selections", results);
			return "authors/authorsList";
		}
	}

	@GetMapping("/authors/{authorId}/edit")
	public String initUpdateAuthorForm(@PathVariable("authorId") int authorId, Model model) {
		Author author = this.authors.findById(authorId);
		model.addAttribute(author);
		return VIEWS_AUTHOR_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/authors/{authorId}/edit")
	public String processUpdateAuthorForm(@Valid Author author, BindingResult result,
			@PathVariable("authorId") int authorId) {
		if (result.hasErrors()) {
			return VIEWS_AUTHOR_CREATE_OR_UPDATE_FORM;
		}
		else {
			author.setId(authorId);
			this.authors.save(author);
			return "redirect:/authors/{authorId}";
		}
	}

	/**
	 * Custom handler for displaying an author.
	 * @param authorId the ID of the author to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/authors/{authorId}")
	public ModelAndView showAuthor(@PathVariable("authorId") int authorId) {
		ModelAndView mav = new ModelAndView("authors/authorDetails");
		Author author = this.authors.findById(authorId);
		for (Quote quote : author.getQuotes()) {
			quoteList.add(quote);
			quote.setCommentsInternal(comments.findByQuoteId(quote.getId()));
		}
		mav.addObject(author);
		return mav;
	}

}