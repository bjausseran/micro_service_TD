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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.example.demo.base.Person;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@Table(name = "authors")
public class Author extends Person {


	@Column(name = "city")
	@NotEmpty
	private String city;

	private HashSet<Integer> quotes;
	private HashSet<Integer> comments;


	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public HashSet<Integer> getQuotes() {
		if (this.quotes == null) {
			this.quotes = new HashSet<>();
		}
		return this.quotes;
	}

	public void addQuote(Integer quote) {
		getQuotes().add(quote);
	}

	public HashSet<Integer> getComments() {
		if (this.comments == null) {
			this.comments = new HashSet<>();
		}
		return this.comments;
	}

	public void addComment(Integer comment) {
		getComments().add(comment);
	}

}
