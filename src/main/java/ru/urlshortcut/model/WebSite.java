package ru.urlshortcut.model;

import lombok.*;

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
    @NotBlank(message = "Please check your website address")
    private String site;
    private String login;
    private String password;
}