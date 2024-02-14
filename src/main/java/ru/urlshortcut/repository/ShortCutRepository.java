package ru.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.urlshortcut.model.ShortCut;

import java.util.Optional;

public interface ShortCutRepository extends CrudRepository<ShortCut, Integer> {
    Optional<ShortCut> findByLink(String link);
}