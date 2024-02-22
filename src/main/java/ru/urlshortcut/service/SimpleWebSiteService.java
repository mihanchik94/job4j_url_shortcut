package ru.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urlshortcut.dto.WebSiteDto;
import ru.urlshortcut.exception.ServiceException;
import ru.urlshortcut.model.WebSite;
import ru.urlshortcut.repository.WebSiteRepository;
import ru.urlshortcut.utils.CodeGenerator;
import ru.urlshortcut.utils.LoginCodeGenerator;
import ru.urlshortcut.utils.PasswordCodeGenerator;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleWebSiteService implements WebSiteService, UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSiteService.class);
    private final WebSiteRepository webSiteRepository;
    private final BCryptPasswordEncoder encoder;


    @Override
    public Optional<WebSiteDto> save(WebSite webSite) throws ServiceException {
        try {
            CodeGenerator loginCodeGenerator = new LoginCodeGenerator(webSite.getSite());
            CodeGenerator passwordCodeGenerator = new PasswordCodeGenerator();
            webSite.setLogin(loginCodeGenerator.generate());
            String password = passwordCodeGenerator.generate();
            webSite.setPassword(encoder.encode(password));
            webSiteRepository.save(webSite);
            return Optional.of(new WebSiteDto(true, webSite.getLogin(), password));
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Can't save website. Check link", e);
        }
    }


    @Override
    public Optional<WebSite> findByLogin(String login) {
        return Optional.ofNullable(webSiteRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return webSiteRepository.findByLogin(username)
                .map(webSite -> new User(webSite.getLogin(), webSite.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}