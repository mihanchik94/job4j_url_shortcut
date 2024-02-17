package ru.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.urlshortcut.model.ShortCut;

import java.util.List;
import java.util.Optional;

public interface ShortCutRepository extends CrudRepository<ShortCut, Integer> {
    Optional<ShortCut> findByLink(String link);
    Optional<ShortCut> findByCode(String code);
    @Transactional
    @Modifying
    @Query("update ShortCut s set s.total = s.total + 1 where s.code = :code")
    void incrementTotalByCode(@Param("code") String code);
    List<ShortCut> findAll();

    @Query("from ShortCut s join fetch s.webSite where s.webSite.id = :webSiteId")
    List<ShortCut> findMyCuts(int webSiteId);
}