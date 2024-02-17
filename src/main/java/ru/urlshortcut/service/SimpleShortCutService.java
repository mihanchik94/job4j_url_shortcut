package ru.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.urlshortcut.dto.ShortCutDto;
import ru.urlshortcut.dto.StatisticDto;
import ru.urlshortcut.model.ShortCut;
import ru.urlshortcut.repository.ShortCutRepository;
import ru.urlshortcut.utils.CodeGenerator;
import ru.urlshortcut.utils.LinkCodeGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleShortCutService implements ShortCutService {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleShortCutService.class);
    private ShortCutRepository shortCutRepository;


    @Override
    public Optional<ShortCutDto> save(ShortCut shortCut) {
        Optional<ShortCut> savedShortCut = shortCutRepository.findByLink(shortCut.getLink());
        if (savedShortCut.isEmpty()) {
            try {
                CodeGenerator linkCodeGenerator = new LinkCodeGenerator();
                shortCut.setCode(linkCodeGenerator.generate());
                shortCutRepository.save(shortCut);
                return Optional.of(new ShortCutDto(shortCut.getCode(), shortCut.getTotal()));
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShortCut> findByCode(String code) {
        Optional<ShortCut> optionalShortCut = shortCutRepository.findByCode(code);
        if (optionalShortCut.isPresent()) {shortCutRepository.incrementTotalByCode(code);
            return optionalShortCut;
        }
        return Optional.empty();
    }

    @Override
    public List<StatisticDto> findAll() {
        return shortCutRepository.findAll().stream()
                .map(shortCut -> new StatisticDto(shortCut.getLink(), shortCut.getTotal()))
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticDto> findMyShortCuts(int id) {
        return shortCutRepository.findMyCuts(id).stream()
                .map(shortCut -> new StatisticDto(shortCut.getLink(), shortCut.getTotal()))
                .collect(Collectors.toList());
    }
}