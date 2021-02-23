package com.test.shortener.controller;

import com.test.shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ViewController {
    private final UrlService urlService;

    @GetMapping()
    public String index() {
        return "main";
    }

    @PostMapping(value = "/shorten")
    public String shortenUrl(@RequestParam String longUrl,
                             Model model) {
        String shortUrl = urlService.makeShortURL(longUrl);
        model.addAttribute("shortUrl", shortUrl);
        return "result";
    }

    @ExceptionHandler({Exception.class})
    public String databaseError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }
}
