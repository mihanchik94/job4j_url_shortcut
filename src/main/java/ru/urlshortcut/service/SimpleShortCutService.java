package ru.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.urlshortcut.dto.ShortCutDto;
import ru.urlshortcut.model.ShortCut;
import ru.urlshortcut.repository.ShortCutRepository;
import ru.urlshortcut.utils.CodeGenerator;
import ru.urlshortcut.utils.LinkCodeGenerator;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleShortCutService implements ShortCutService {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleShortCutService.class);
    private static CodeGenerator linkCodeGenerator;
    private ShortCutRepository shortCutRepository;


    @Override
    public Optional<ShortCutDto> convert(ShortCut shortCut) {
        Optional<ShortCut> savedShortCut = shortCutRepository.findByLink(shortCut.getLink());
        if (savedShortCut.isEmpty()) {
            try {
                linkCodeGenerator = new LinkCodeGenerator();
                shortCut.setCode(linkCodeGenerator.generate());
                shortCutRepository.save(shortCut);
                return Optional.of(new ShortCutDto(shortCut.getCode(), shortCut.getTotal()));
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return Optional.empty();
    }
}