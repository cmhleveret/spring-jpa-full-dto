package com.andy.sprintbootsqlunit6.service;

import com.andy.sprintbootsqlunit6.domain.Book;
import com.andy.sprintbootsqlunit6.domain.Category;
import com.andy.sprintbootsqlunit6.repository.BookRepository;
import com.andy.sprintbootsqlunit6.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

  private final BookRepository bookRepository;
  private final CategoryRepository categoryRepository;

  public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
    this.bookRepository = bookRepository;
    this.categoryRepository = categoryRepository;
  }

  public List<Book> getBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> getBook(Integer id) {
    return bookRepository.findById(id);
  }

  private void validateBook(Book book, Integer categoryId) {
    if (book.getTitle().isEmpty() || book.getSynopsis().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing title and/or synopsis");
    }
    Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
    if (optionalCategory.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing category");
    }
    book.setCategory(optionalCategory.get());
  }

  public Optional<Book> create(Book book, Integer categoryId) {
    validateBook(book, categoryId);
    return Optional.of(bookRepository.save(book));
  }

  public Optional<Book> update(Book book, Integer categoryId) {
    validateBook(book, categoryId);
    return Optional.of(bookRepository.save(book));
  }

  public void delete(Integer id) {
    Optional<Book> optionalBook = bookRepository.findById(id);
    if (optionalBook.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book");
    }
    bookRepository.delete(optionalBook.get());
  }

  public List<Book> getBooksForCategory(Category category) {
    return bookRepository.findByCategory(category);
  }

}
