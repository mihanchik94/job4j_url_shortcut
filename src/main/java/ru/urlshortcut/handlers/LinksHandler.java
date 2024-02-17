package ru.urlshortcut.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@ControllerAdvice
@AllArgsConstructor
public class LinksHandler {
    private static final Logger LOG = LoggerFactory.getLogger(LinksHandler.class);
    private ObjectMapper objectMapper;

    @ExceptionHandler(value = {NoSuchElementException.class})
    public void handleBadRequests(NoSuchElementException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", "This value is not found");
            put("details", e.getMessage());
        }}));
        LOG.error(e.getMessage());
    }
}