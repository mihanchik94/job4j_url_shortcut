package ru.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.urlshortcut.model.WebSite;

import java.util.Optional;

public interface WebSiteRepository extends CrudRepository<WebSite, Integer> {
    Optional<WebSite> findBySite(String site);
    Optional<WebSite> findByLogin(String login);
}