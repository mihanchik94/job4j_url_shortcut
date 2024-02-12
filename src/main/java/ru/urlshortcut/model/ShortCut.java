package ru.urlshortcut.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "shortcuts")
@Getter
@Setter
public class ShortCut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @EqualsAndHashCode.Include
    @NotNull(message = "Link mustn't be null")
    @NotBlank(message = "Link mustn't be empty")
    private String link;
    @NotNull(message = "Unique code mustn't be null")
    @NotBlank(message = "Unique code mustn't be empty")
    private String code;
    @Min(value = 0)
    private int total;
    @ManyToOne
    @JoinColumn(name = "website_id", foreignKey = @ForeignKey(name = "WEBSITE_ID"))
    private WebSite webSite;
}