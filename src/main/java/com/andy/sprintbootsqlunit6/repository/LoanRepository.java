package com.andy.sprintbootsqlunit6.repository;

import com.andy.sprintbootsqlunit6.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
