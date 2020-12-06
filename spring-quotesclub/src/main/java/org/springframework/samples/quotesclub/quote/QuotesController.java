package org.springframework.samples.quotesclub.quote;
import java.time.LocalDate;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("quotes/{quoteId}")

class QuoteController{

    private final QuoteRepository quotes;

    public QuoteController(QuoteRepository quotes) {
        this.quotes = quotes;
    }

    @GetMapping("/quotes")
    public Iterable<Quote> getQuotes() {
        return quotes.findAll();
    }

    @PostMapping("/quotes")
    public Quote addVisit(@RequestParam("author_id") Integer author_id,
            @RequestParam("content") String content, 
            @RequestParam("date") LocalDate date) {
        Quote quote = new Quote();
        quote.setAuthorId(author_id);
        quote.setContent(content);
        quote.setDate(date);
        return quotes.save(quote);
    }

    @DeleteMapping("/quotes/{id}")
    public void deleteQuote(@PathVariable("id") Integer id) {
        quotes.deleteById(id);
    }


}