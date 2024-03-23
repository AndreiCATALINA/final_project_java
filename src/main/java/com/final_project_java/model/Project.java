package com.final_project_java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    private String projectName;
    private String description;
    private double budget;
    private String status;

    //Many-to-one relationship with customer entity
    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    private List<User> users;

    //Many-to-many relationship with book entity
    @ManyToMany
    @JoinTable(name = "orders_items",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("orderList")
    private List<Item> itemList;

}
