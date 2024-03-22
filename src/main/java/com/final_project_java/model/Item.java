package com.final_project_java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

//@Getter
//@Setter
//@AllArgsConstructor
@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "item_title",nullable = false)
    private String title;
    @Column(name = "item_overview",nullable = false)
    private String overview;
    @Column(name = "item_stepsToReproduce",nullable = false)
    private String stepsToReproduce;
    @Column(name = "item_expectedResults",nullable = false)
    private String expectedResults;
    @Enumerated(value = EnumType.STRING)
    private ItemSeverity severity;
    @Column(name = "item_image")
    private String image;


    //Many-to-many relationship with order entity
    @ManyToMany(mappedBy = "itemList", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("itemList")
    private List<Project> projectList;


}
