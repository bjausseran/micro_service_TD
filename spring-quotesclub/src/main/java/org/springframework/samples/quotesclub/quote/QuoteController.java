package org.springframework.samples.quotesclub.quote;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

@Controller
class QuoteController{
	private static final String VIEWS_QUOTE_CREATE_OR_UPDATE_FORM = "quotes/createOrUpdateQuoteForm";

    private final QuoteRepository quotes;

    public QuoteController(QuoteRepository quotes) {
        this.quotes = quotes;
    }

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
    @GetMapping("/quotes")
    public Iterable<Quote> getQuotes() {
        return quotes.findAll();
    }

	@GetMapping("/quotes/new/")
	public String initCreationForm(Map<String, Object> model) {
		Quote quote = new Quote();
		model.put("quote", quote);
		return VIEWS_QUOTE_CREATE_OR_UPDATE_FORM;
	}


	@RequestMapping(value = "/quotes/new", method = {RequestMethod.POST})
	public String processCreationForm(@Valid Quote quote, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_QUOTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.quotes.save(quote);
			//return "redirect:/authors/" + author.getId();
			return "redirect:/quote/new";
		}
	}

    @DeleteMapping("/quotes/{id}")
    public void deleteQuote(@PathVariable("id") Integer id) {
        quotes.deleteById(id);
    }


}