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
package com.example.demo.coment;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	private final CommentRepository comments;
	
	private final QuoteRepository quotes;

	public CommentController(CommentRepository comments, QuoteRepository quotes) {
		this.comments = comments;
		this.quotes = quotes;
	}
	

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}


	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Quote object always has an id (Even though id is not part of the form fields)
	 * @param quoteId
	 * @return Quote
	 */
	@ModelAttribute("comment")
	public Comment loadQuoteWithComment(@PathVariable("quote_id") int quoteId, Map<String, Object> model) {
		Quote quote = this.quotes.findById(quoteId);
		quote.setCommentsInternal(this.comments.findByQuoteId(quoteId));
		model.put("quote", quote);
		Comment comment = new Comment();
		quote.addComment(comment);
		return comment;
	}

	// Spring MVC calls method loadQuoteWithComment(...) before initNewCommentForm is called
	@GetMapping("/owners/*/quotes/{quoteId}/comments/new")
	public String initNewCommentForm(@PathVariable("quote_id") int quoteId, Map<String, Object> model) {
		return "quotes/createOrUpdateCommentForm";
	}

	// Spring MVC calls method loadQuoteWithComment(...) before processNewCommentForm is called
	@PostMapping("/owners/{ownerId}/quotes/{quote_id}/comments/new")
	public String processNewCommentForm(@Valid Comment comment, BindingResult result) {
		if (result.hasErrors()) {
			return "quotes/createOrUpdateCommentForm";
		}
		else {
			this.comments.save(comment.getDate(), comment.getContent(), comment.getQuoteId());
			return "redirect:/owners/{ownerId}";
		}
	}
	

}
