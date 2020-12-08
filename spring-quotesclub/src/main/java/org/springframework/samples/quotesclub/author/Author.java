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

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.samples.quotesclub.comment.Comment;

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
    
	private HashSet<String> quotesString;

	private Set<Comment> comments;
    
	private HashSet<String> commentsString;

	

	public HashSet<String> getCommentsString() {
		if (this.commentsString == null) {
			this.commentsString = new HashSet<>();
		}
		return this.commentsString;
	}

	public void addCommentString(String com) {
		getCommentsString().add(com);
	}
	public HashSet<String> getQuotesString() {
		if (this.quotesString == null) {
			this.quotesString = new HashSet<>();
		}
		return this.quotesString;
	}

	public void addQuoteString(String quote) {
		getQuotesString().add(quote);
	}

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

	public Quote addQuote(Quote quote) {
		if (quote.isNew()) {
			getQuotesInternal().add(quote);
		}
		quote.setAuthorId(this.getId());
		return quote;
	}
	
	
	protected Set<Comment> getCommentsInternal() {
		if (this.comments == null) {
			this.comments = new HashSet<>();
		}
		return this.comments;
	}

	protected void setCommentsInternal(Set<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getComments() {
		List<Comment> sortedComments = new ArrayList<>(getCommentsInternal());
		PropertyComparator.sort(sortedComments, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedComments);
	}

	public void addComment(Comment comment) {
		if (comment.isNew()) {
			getCommentsInternal().add(comment);
		}
		comment.setAuthorId(this.getId());
		getCommentsString().add(comment.getContent());
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