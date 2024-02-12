package ru.urlshortcut.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "websites")
@Getter
@Setter
public class WebSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @EqualsAndHashCode.Include
    @NotNull(message = "Site mustn't be null")
    @NotBlank(message = "Site mustn't be empty")
    private String site;
    @NotNull(message = "Login mustn't be null")
    @NotBlank(message = "Login mustn't be empty")
    private String login;
    @NotNull(message = "Password mustn't be null")
    @NotBlank(message = "Password mustn't be empty")
    private String password;
}