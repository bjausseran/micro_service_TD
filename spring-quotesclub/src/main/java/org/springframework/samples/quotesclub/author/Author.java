package org.springframework.samples.quotesclub.author;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.quotesclub.base.Person;
import org.springframework.samples.quotesclub.quote.Quote;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public class Author extends Person {

	@NotEmpty
	private String city;


	private Set<Quote> quotes;


	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	protected Set<Quote> getQuotesInternal() {
		if (this.quotes == null) {
			this.quotes = new HashSet<>();
		}
		return this.quotes;
	}

	protected void setQuotesInternal(Set<Quote> quotes) {
		this.quotes = quotes;
	}

	public List<Quote> getQuotes() {
		List<Quote> sortedQuotes = new ArrayList<>(getQuotesInternal());
		PropertyComparator.sort(sortedQuotes, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedQuotes);
	}

	public void addQuote(Quote quote) {
		if (quote.isNew()) {
			getQuotesInternal().add(quote);
		}
		quote.setAuthorId(this.getId());
	}

	/**
	 * Return the Quote with the given name, or null if none found for this Author.
	 * @param name to test
	 * @return true if quote name is already in use
	 */
	public Quote getQuote(String name) {
		return getQuote(name, false);
	}

	/**
	 * Return the Quote with the given name, or null if none found for this Author.
	 * @param name to test
	 * @return true if quote name is already in use
	 */
	public Quote getQuote(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Quote quote : getQuotesInternal()) {
			if (!ignoreNew || !quote.isNew()) {
				String content = quote.getContent();
				content = content.toLowerCase();
				if (content.equals(name)) {
					return quote;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName())
				.append("firstName", this.getFirstName()).append("city", this.city).toString();
	}

}