/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.quotesclub.comment;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.quotesclub.author.Author;
import org.springframework.samples.quotesclub.quote.Quote;
import org.springframework.samples.quotesclub.quote.QuoteRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@RestController
class CommentController {

	@Autowired
	private final CommentRepository comments;

	@Autowired
	private QuoteRepository quotes;


	public CommentController(CommentRepository comments, QuoteRepository quotes) {
		this.comments = comments;
		this.quotes = quotes;
	}

	@GetMapping("/comments")
	public Iterable<Comment> getComments() {
		return comments.findAll();
	}

	@GetMapping("/comments/{id}")
	public Comment getQuoteById(@PathVariable("id") Integer id) {
		return comments.findById(id);
	}


	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Quote object always has an id (Even though id is not part of the form fields)
	 * @param quoteId
	 * @return Quote
	 */
//	@ModelAttribute("comment")
//	public Comment loadQuoteWithComment(@PathVariable("quoteId") int quoteId, Map<String, Object> model) {
//		Quote quote = this.quotes.findById(quoteId);
//		quote.setCommentsInternal(this.comments.findByQuoteId(quoteId));
//		model.put("quote", quote);
//		Comment comment = new Comment();
//		quote.addComment(comment);
//		return comment;
//	}


	@PostMapping("/authors/{authorId}/quotes/{quoteId}/comments/new")
	public Comment addComment(@RequestParam("content") String content,
			@PathVariable("quoteId") Integer quoteId, @RequestParam("authorId") Integer authorId) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setQuoteId(quoteId);
		comment.setAuthorId(authorId);
		return comments.save(comment);
	}

	@PostMapping("/comments/")
	public Comment save(@RequestBody Comment comment) {
		return comments.save(comment);
	}
	@DeleteMapping("/comments/{id}")
	public void deleteComment(@PathVariable("id") Integer id) {
		comments.deleteById(id);
	}

}
