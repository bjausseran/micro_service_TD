package com.example.demo.quote;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "quotes")

public class Quote extends com.example.demo.base.BaseEntity{

    @Column(name = "type_id")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Integer author_id;

    @Column(name = "content")
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


    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }




}