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
package com.example.demo.comment;

import java.time.LocalDate;
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

	public CommentController(CommentRepository comments) {
		this.comments = comments;
	}

	@GetMapping("/comments")
	public Iterable<Comment> getComments() {
		return comments.findAll();
	}

	@GetMapping("/comments/{id}")
	public Optional<Comment> getCommentById(@PathVariable("id") Integer id) {
		return comments.findById(id);
	}

	@PostMapping("/authors/*/quotes/{quoteId}/comments/new")
	public Comment addComment(@RequestParam("description") String content,
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
