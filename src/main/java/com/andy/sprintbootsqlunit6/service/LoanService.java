package com.andy.sprintbootsqlunit6.service;

import com.andy.sprintbootsqlunit6.domain.Book;
import com.andy.sprintbootsqlunit6.domain.Loan;
import com.andy.sprintbootsqlunit6.domain.Member;
import com.andy.sprintbootsqlunit6.repository.BookRepository;
import com.andy.sprintbootsqlunit6.repository.LoanRepository;
import com.andy.sprintbootsqlunit6.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoanService {

  private final BookRepository bookRepository;
  private final MemberRepository memberRepository;
  private final LoanRepository loanRepository;

  public LoanService(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository) {
    this.bookRepository = bookRepository;
    this.memberRepository = memberRepository;
    this.loanRepository = loanRepository;
  }

  public List<Loan> getLoans() {
    return loanRepository.findAll();
  }

  public Optional<Loan> getLoan(Integer id) {
    return loanRepository.findById(id);
  }

  private Loan mapLoanWithId(Map<String, Object> loanJson) {
    Integer loanId = (Integer) loanJson.getOrDefault("id", -1);
    if (loanId == -1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing loanId");
    }
    if (!loanRepository.existsById(loanId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find loan");
    }
    Loan loan = mapLoanNoId(loanJson);
    loan.setId(loanId);
    return loan;
  }

  private Loan mapLoanNoId(Map<String, Object> loanJson) {
    List<Integer> bookIds = (List<Integer>) loanJson.getOrDefault("books", Collections.EMPTY_LIST);
    if (bookIds.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Needs at least 1 bookId in a loan");
    }
    Integer memberId = (Integer) loanJson.getOrDefault("memberId", -1);
    if (memberId == -1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing memberId");
    }
    Optional<Member> optionalMember = memberRepository.findById(memberId);
    if (optionalMember.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find Library Member");
    }

    List<Book> books = bookIds.stream()
            .map(bookRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    Loan loan = new Loan();
    loan.setLoanedBooks(books);
    loan.setMember(optionalMember.get());
    return loan;
  }

  public Loan create(Map<String, Object> loanJson) {
    Loan loanToSave = mapLoanNoId(loanJson);
    return loanRepository.save(loanToSave);
  }

  public Loan update(Map<String, Object> loanJson) {
    return loanRepository.save(mapLoanWithId(loanJson));
  }

  public void delete(Integer loanId) {
    loanRepository.deleteById(loanId);
  }
}
