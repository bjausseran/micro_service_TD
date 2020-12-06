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
package com.example.demo.author;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@RestController
class AuthorController {

	private final AuthorRepository authors;

	public AuthorController(AuthorRepository authors) {
		this.authors = authors;
	}

	//@HystrixCommand
	@GetMapping("/authors")
	public Iterable<Author> getAuthors() {
		return authors.findAll();
	}

	//@HystrixCommand
	@GetMapping("/authors/{id}")
	public Optional<Author> getAuthorById(@PathVariable("id") Integer id) {
		return authors.findById(id);
	}

	//@HystrixCommand
	@PostMapping("/authors/new")
	public Author addAuthor(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("city") String city) {
		Author author = new Author();
		author.setFirstName(firstName);
		author.setLastName(lastName);
		author.setCity(city);
		return authors.save(author);
	}
	
	//@HystrixCommand
	@PostMapping("/authors/")
	public Author save(@RequestBody Author author) {
		return authors.save(author);
	}


	//@HystrixCommand
	@DeleteMapping("/authors/{id}")
	public void deleteAuthor(@PathVariable("id") Integer id) {
		authors.deleteById(id);
	}

	//@HystrixCommand
	@PostMapping("/authors/{authorId}/addPet")
	public void addPetToAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("petId") Integer petId) {
		Optional<Author> authorOpt = authors.findById(authorId);
		if (authorOpt.isPresent()) {
			Author author = authorOpt.get();
			author.addPet(petId);
			authors.save(author);
		}
		
	}
	
	//@HystrixCommand(commandKey = "author-service-find-by-last-name")
	@GetMapping("/authors/findByLastName/{lastName}")
	public Iterable<Author> findAuthorByLastName(@PathVariable("lastName") String lastName) throws InterruptedException {
		return authors.findByLastName(lastName);
	}	

}
