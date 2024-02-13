package ru.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebSiteDto {
    private boolean registration;
    @NotNull(message = "Login mustn't be null")
    @NotBlank(message = "Login mustn't be empty")
    private String login;
    @NotNull(message = "Password mustn't be null")
    @NotBlank(message = "Password mustn't be empty")
    private String password;
}