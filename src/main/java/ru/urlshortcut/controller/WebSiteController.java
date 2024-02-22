package ru.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urlshortcut.dto.WebSiteDto;
import ru.urlshortcut.exception.ServiceException;
import ru.urlshortcut.model.WebSite;
import ru.urlshortcut.service.WebSiteService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/websites")
@AllArgsConstructor
public class WebSiteController {
    private final WebSiteService webSiteService;

    @PostMapping("/registration")
    public ResponseEntity<WebSiteDto> create(@Valid @RequestBody WebSite webSite) throws ServiceException {
        Optional<WebSiteDto> webSiteDto = webSiteService.save(webSite);
        return new ResponseEntity<>(webSiteDto.get(), HttpStatus.CREATED);
    }
}