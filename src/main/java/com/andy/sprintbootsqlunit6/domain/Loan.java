package com.andy.sprintbootsqlunit6.domain;

import com.andy.sprintbootsqlunit6.domain.DTO.Dto;
import com.andy.sprintbootsqlunit6.domain.DTO.LoanDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Loan implements Dto<LoanDto> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToMany
  @JoinTable(
          name = "loaned_book",
          joinColumns = @JoinColumn(name = "loan_id"),
          inverseJoinColumns = @JoinColumn(name = "book_id"))
  private List<Book> loanedBooks;

  @ManyToOne
  private Member member;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<Book> getLoanedBooks() {
    return loanedBooks;
  }

  public void setLoanedBooks(List<Book> loanedBooks) {
    this.loanedBooks = loanedBooks;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  @Override
  public LoanDto getDto() {
    LoanDto loanDto = new LoanDto();
    loanDto.setId(this.id);
    loanDto.setMemberId(this.member.getId());
    loanDto.setMemberFirstName(this.member.getFirstName());
    loanDto.setMemberSurname(this.member.getSurname());
    if (this.loanedBooks == null) {
      loanDto.setBooks(Collections.emptyList());
    } else {
      loanDto.setBooks(this.loanedBooks.stream().map(Book::getDto).collect(Collectors.toList()));
    }
    return loanDto;
  }
}
