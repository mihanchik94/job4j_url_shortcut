package ru.urlshortcut.service;

import ru.urlshortcut.dto.ShortCutDto;
import ru.urlshortcut.model.ShortCut;

import java.util.Optional;

public interface ShortCutService {
    Optional<ShortCutDto> convert(ShortCut shortCut);
}