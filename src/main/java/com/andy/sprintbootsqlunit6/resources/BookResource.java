package com.andy.sprintbootsqlunit6.resources;

import com.andy.sprintbootsqlunit6.domain.Book;
import com.andy.sprintbootsqlunit6.domain.DTO.BookDTO;
import com.andy.sprintbootsqlunit6.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookResource {

  private final BookService bookService;

  public BookResource(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<List<BookDTO>> get() {
    List<BookDTO> books = bookService.getBooks().stream().map(Book::getDto).collect(Collectors.toList());
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDTO> get(HttpServletRequest request, @PathVariable("id") Integer id) {
    Optional<Book> optionalBook = bookService.getBook(id);
    if (optionalBook.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
    }
    return new ResponseEntity<>(optionalBook.get().getDto(), HttpStatus.OK);
  }

  private Book mapBookNoId(Map<String, Object> bookJson) {
    return new Book(
            (String) bookJson.getOrDefault("title", ""),
            (String) bookJson.getOrDefault("synopsis", "")
    );
  }

  private Book mapBookWithId(Map<String, Object> bookJson, Integer id) {
    Book book = mapBookNoId(bookJson);
    book.setId(id);
    return book;
  }

  private Integer getCategoryId(Map<String, Object> bookJson) {
    Integer categoryId = (Integer) bookJson.getOrDefault("categoryId", -1);
    if (categoryId == -1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing categoryId");
    }
    return categoryId;
  }

  @PostMapping
  public ResponseEntity<BookDTO> create(@RequestBody Map<String, Object> bookJson) {
    Integer categoryId = getCategoryId(bookJson);
    Optional<Book> createdBook = bookService.create(mapBookNoId(bookJson), categoryId);
    if (createdBook.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create book");
    }
    return new ResponseEntity<>(createdBook.get().getDto(), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookDTO> update(@RequestBody Map<String, Object> bookJson, @PathVariable("id") Integer id) {
    Integer categoryId = getCategoryId(bookJson);
    Optional<Book> updatedBook = bookService.update(mapBookWithId(bookJson, id), categoryId);
    if (updatedBook.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update book");
    }
    return new ResponseEntity<>(updatedBook.get().getDto(), HttpStatus.OK);
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id) {
    bookService.delete(id);
    Map<String, Boolean> response = new HashMap<>();
    response.put("success", true);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
