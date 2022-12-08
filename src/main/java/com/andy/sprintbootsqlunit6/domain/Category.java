package com.andy.sprintbootsqlunit6.domain;

import com.andy.sprintbootsqlunit6.domain.DTO.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<Book> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @JsonIgnore
    public CategoryDTO getDto() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(this.id);
        categoryDTO.setDescription(this.description);
        categoryDTO.setName(this.name);
        if (this.books == null) {
            categoryDTO.setBooks(Collections.emptyList());
        } else {
            categoryDTO.setBooks(this.books.stream().map(Book::getDto).collect(Collectors.toList()));
        }
        return categoryDTO;
    }
}
