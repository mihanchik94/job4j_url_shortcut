package ru.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.urlshortcut.dto.WebSiteDto;
import ru.urlshortcut.model.WebSite;
import ru.urlshortcut.service.WebSiteService;

import javax.validation.Valid;

@RestController
@RequestMapping("/websites")
@AllArgsConstructor
public class WebSiteController {
    private final WebSiteService webSiteService;

    @PostMapping("/registration")
    public ResponseEntity<WebSiteDto> create(@Valid @RequestBody WebSite webSite) {
        return webSiteService.save(webSite)
                .map(webSiteDto -> new ResponseEntity<>(webSiteDto, HttpStatus.CREATED))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "This site has already been registered. " +
                        "Please log in or come up with another one"));
    }
}