package ru.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShortCutDto {
    private String code;
    private int total;

    public ShortCutDto(String code) {
        this.code = code;
    }
}