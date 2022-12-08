package com.andy.sprintbootsqlunit6.service;

import com.andy.sprintbootsqlunit6.domain.Category;
import com.andy.sprintbootsqlunit6.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getCategories() {
    return categoryRepository.findAll();
  }

  public Optional<Category> getCategory(Integer id) {
    return categoryRepository.findById(id);
  }

  public Category createCategory(Map<String, Object> categoryJson) {
    Category category = mapCategoryNoId(categoryJson);
    return categoryRepository.save(category);
  }

  private Category mapCategoryNoId(Map<String, Object> categoryJson) {
    String name = (String) categoryJson.getOrDefault("name", "");
    String description = (String) categoryJson.getOrDefault("description", "");
    if (name.isEmpty() || description.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing name or description");
    }
    Category category = new Category();
    category.setName(name);
    category.setDescription(description);
    return category;
  }

  private Category mapCategoryWithId(Map<String, Object> categoryJson) {
    Integer categoryId = (Integer) categoryJson.getOrDefault("id", -1);
    if (categoryId == -1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing id");
    }
    Category category = mapCategoryNoId(categoryJson);
    category.setId(categoryId);
    return category;
  }
}
