package ru.urlshortcut.service;

import ru.urlshortcut.dto.WebSiteDto;
import ru.urlshortcut.model.WebSite;

import java.util.Optional;

public interface WebSiteService {
    Optional<WebSiteDto> save(WebSite webSite);
    Optional<WebSite> findByLogin(String login);
}
