package com.andy.sprintbootsqlunit6.domain;

import com.andy.sprintbootsqlunit6.domain.DTO.Dto;
import com.andy.sprintbootsqlunit6.domain.DTO.MemberDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Member implements Dto<MemberDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String surname;

    @OneToMany(mappedBy = "member")
    private List<Loan> loans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public MemberDTO getDto() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setFirstName(this.firstName);
        memberDTO.setId(this.id);
        memberDTO.setSurname(this.surname);
        if (this.loans == null) {
            memberDTO.setLoans(Collections.emptyList());
        } else {
            memberDTO.setLoans(this.loans.stream().map(Loan::getDto).collect(Collectors.toList()));
        }
        return memberDTO;
    }
}
