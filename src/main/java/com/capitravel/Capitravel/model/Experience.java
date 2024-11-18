package com.capitravel.Capitravel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String country;
    private String ubication;
    private String description;
    private Integer duration;
    private double reputation;

    @Lob
    @ElementCollection
    private List<String> images;

    @ManyToMany
    @JoinTable(
            name = "experience_category",
            joinColumns = @JoinColumn(name = "experience_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "experience_property",
            joinColumns = @JoinColumn(name = "experience_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )

    private List<Property> properties;

}
