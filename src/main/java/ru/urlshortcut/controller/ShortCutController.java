package ru.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.urlshortcut.dto.StatisticDto;
import ru.urlshortcut.exception.ServiceException;
import ru.urlshortcut.model.ShortCut;
import ru.urlshortcut.model.WebSite;
import ru.urlshortcut.service.ShortCutService;
import ru.urlshortcut.service.WebSiteService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shortcuts")
@AllArgsConstructor
public class ShortCutController {
    private final ShortCutService shortCutService;
    private final WebSiteService webSiteService;

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@Valid @RequestBody ShortCut shortCut, Authentication auth) throws ServiceException {
        String username = (String) auth.getPrincipal();
        Optional<WebSite> optionalWebSite = webSiteService.findByLogin(username);
        shortCut.setWebSite(optionalWebSite.get());
        shortCutService.save(shortCut);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("code: " + shortCut.getCode());
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
