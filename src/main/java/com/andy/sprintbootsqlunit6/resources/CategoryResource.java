package com.andy.sprintbootsqlunit6.resources;

import com.andy.sprintbootsqlunit6.domain.Category;
import com.andy.sprintbootsqlunit6.domain.DTO.CategoryDTO;
import com.andy.sprintbootsqlunit6.service.BookService;
import com.andy.sprintbootsqlunit6.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

  private final CategoryService categoryService;
  private final BookService bookService;

  public CategoryResource(CategoryService categoryService, BookService bookService) {
    this.categoryService = categoryService;
    this.bookService = bookService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getOne(@PathVariable Integer id) {
      Optional<Category> optionalCategory = categoryService.getCategory(id);
      if (optionalCategory.isEmpty()) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
      }
      return new ResponseEntity<>(optionalCategory.get().getDto(), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAll() {
    List<CategoryDTO> categories = categoryService.getCategories()
            .stream()
            .map(Category::getDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> create(@RequestBody Map<String, Object> requestBody) {
      CategoryDTO category = categoryService.createCategory(requestBody).getDto();
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }
}
