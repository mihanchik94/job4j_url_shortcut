package ru.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.urlshortcut.dto.ShortCutDto;
import ru.urlshortcut.dto.StatisticDto;
import ru.urlshortcut.dto.WebSiteDto;
import ru.urlshortcut.model.ShortCut;
import ru.urlshortcut.model.WebSite;
import ru.urlshortcut.service.ShortCutService;
import ru.urlshortcut.service.WebSiteService;

import javax.validation.Valid;
import java.util.List;
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
        Optional<ShortCutDto> savedShortCut = shortCutService.save(shortCut);
        if (savedShortCut.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("code: " + shortCut.getCode());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This url has already been registered.");
        }
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        Optional<ShortCut> shortCut = shortCutService.findByCode(code);
        String message = "HTTP CODE - 302 REDIRECT " + shortCut.get().getLink();
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticDto>> getStatistic() {
        List<StatisticDto> body = shortCutService.findAll();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @GetMapping("/mystatistic")
    public ResponseEntity<List<StatisticDto>> getMyStatistic(Authentication auth) {
        String username = (String) auth.getPrincipal();
        Optional<WebSite> optionalWebSite = webSiteService.findByLogin(username);
        List<StatisticDto> body = shortCutService.findMyShortCuts(optionalWebSite.get().getId());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}