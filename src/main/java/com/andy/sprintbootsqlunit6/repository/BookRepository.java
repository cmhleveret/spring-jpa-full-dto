package com.andy.sprintbootsqlunit6.repository;

import com.andy.sprintbootsqlunit6.domain.Book;
import com.andy.sprintbootsqlunit6.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
  boolean existsBookByTitle(String title);
  List<Book> findByCategory(Category category);
}
