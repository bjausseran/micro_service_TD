package com.example.demo.quote;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import com.example.demo.base.BaseEntity;

@Entity
@Table(name = "quotes")
public class Quote extends BaseEntity{

	@Column(name = "author_id")
	private Integer authorId;
	@Column(name = "content")
	@NotEmpty
	private String content;
	private HashSet<Integer> comments;

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	protected Set<Integer> getCommentsInternal() {
		if (this.comments == null) {
			this.comments = new HashSet<>();
		}
		return this.comments;
	}

	protected void setCommentsInternal(Set<Integer> quotes) {
		this.comments = new HashSet<>(quotes);
	}

	public List<Integer> getComments() {
		return new ArrayList<>(getCommentsInternal());

	}

	public void addComment(Integer commentId) {
			getCommentsInternal().add(commentId);
	}
}
