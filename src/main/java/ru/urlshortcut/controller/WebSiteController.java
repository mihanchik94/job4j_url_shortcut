package ru.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.urlshortcut.dto.ShortCutDto;
import ru.urlshortcut.dto.WebSiteDto;
import ru.urlshortcut.model.ShortCut;
import ru.urlshortcut.model.WebSite;
import ru.urlshortcut.service.ShortCutService;
import ru.urlshortcut.service.WebSiteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/websites")
@AllArgsConstructor
public class WebSiteController {
    private final WebSiteService webSiteService;
    private final ShortCutService shortCutService;

    @PostMapping("/registration")
    public ResponseEntity<WebSiteDto> create(@Valid @RequestBody WebSite webSite) {
        return webSiteService.save(webSite)
                .map(webSiteDto -> new ResponseEntity<>(webSiteDto, HttpStatus.CREATED))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "This site has already been registered. "
                        + "Please log in or come up with another one"));
    }

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@Valid @RequestBody ShortCut shortCut, Authentication auth) {
        String username = (String) auth.getPrincipal();
        Optional<WebSite> optionalWebSite = webSiteService.findByLogin(username);
        shortCut.setWebSite(optionalWebSite.get());
        Optional<ShortCutDto> savedShortCut = shortCutService.convert(shortCut);
        if (savedShortCut.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("code: " + shortCut.getCode());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This url has already been registered.");
        }
    }
}