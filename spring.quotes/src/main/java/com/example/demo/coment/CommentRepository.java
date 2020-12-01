package com.example.demo.coment;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import com.example.demo.base.BaseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "comment-service")
public interface CommentRepository{

	@PostMapping("/comments")
	void save(@RequestParam("date") LocalDate date, @RequestParam("content") String content,
			@RequestParam("quote_id") Integer quoteId);

	@GetMapping("/comments/findByQuoteId/{quoteId}")
	public Collection<Comment> findByQuoteId(@PathVariable("quote_id") Integer quoteId);

}
