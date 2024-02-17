package ru.urlshortcut.service;

import ru.urlshortcut.dto.ShortCutDto;
import ru.urlshortcut.dto.StatisticDto;
import ru.urlshortcut.model.ShortCut;

import java.util.List;
import java.util.Optional;

public interface ShortCutService {
    Optional<ShortCutDto> save(ShortCut shortCut);
    Optional<ShortCut> findByCode(String code);
    List<StatisticDto> findAll();
    List<StatisticDto> findMyShortCuts(int id);
}