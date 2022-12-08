package com.andy.sprintbootsqlunit6.repository;

import com.andy.sprintbootsqlunit6.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
