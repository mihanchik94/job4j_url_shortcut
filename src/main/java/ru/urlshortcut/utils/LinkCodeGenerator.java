package ru.urlshortcut.utils;

import java.util.UUID;

public class LinkCodeGenerator implements CodeGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString().substring(0, 7);
    }
}
