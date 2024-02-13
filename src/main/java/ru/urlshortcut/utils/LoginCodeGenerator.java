package ru.urlshortcut.utils;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class LoginCodeGenerator implements CodeGenerator {
    private String site;

    @Override
    public String generate() {
        return UUID.nameUUIDFromBytes(site.getBytes()).toString().substring(0, 16);
    }
}