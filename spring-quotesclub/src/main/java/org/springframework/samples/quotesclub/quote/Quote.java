package org.springframework.samples.quotesclub.quote;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.quotesclub.comment.Comment;
import org.springframework.samples.quotesclub.base.BaseEntity;


public class Quote extends BaseEntity{

    private Integer author_id;

    private String content;


    public Integer getAuthorId() {
        return this.author_id;
    }

    public void setAuthorId(Integer Author) {
        this.author_id = Author;
    }


    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private Collection<Comment> comments;

	protected Collection<Comment> getCommentsInternal() {
		if (this.comments == null) {
			this.comments = new HashSet<>();
		}
		return this.comments;
	}

	public void setCommentsInternal(Collection<Comment> collection) {
		this.comments = collection;
	}

	@XmlElement
	public List<Comment> getComments() {
		List<Comment> sortedSpecs = new ArrayList<>(getCommentsInternal());
		PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedSpecs);
	}

	public int getNrOfComments() {
		return getCommentsInternal().size();
	}

	public void addComment(Comment comment) {
		getCommentsInternal().add(comment);
	}



}