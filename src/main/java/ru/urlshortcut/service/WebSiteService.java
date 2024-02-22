package ru.urlshortcut.service;

import ru.urlshortcut.dto.WebSiteDto;
import ru.urlshortcut.exception.ServiceException;
import ru.urlshortcut.model.WebSite;

import java.util.Optional;

public interface WebSiteService {
    Optional<WebSiteDto> save(WebSite webSite) throws ServiceException;
    Optional<WebSite> findByLogin(String login);
}
