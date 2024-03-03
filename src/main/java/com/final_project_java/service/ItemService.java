package com.final_project_java.service;



import com.final_project_java.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    //HTTP verbs : GET,POST,PUT,DELETE,PATCH
    //GET - READ
    //POST - CREATE
    //PUT/PATCH - UPDATE
    //DELETE - DELETE

    //this methods will call the default CRUD impl defined in CrudRepository interface
    //read all books from DB -> GET HTTP endpoint
    List<Item> readAllItems();

    //Read data for a book by id -> GET (by id) HTTP endpoint
    Optional<Item> getItemById(Long id);

    // CREATE new book and save it to DB -> POST HTTP endpoint
    Item saveItem(Item item);

    // DELETE book by id-> DELETE (by id) HTTP endpoint
    void deleteItemById(Long id);

    //Update a book -> PUT HTTP endpoint
    Item updateItem(Item item);

    //Custom CRUD methods defined
    List<Item> getAllItemsByName(String name);

    List<Item> getAllItemsByCategory(String category);

}
