package com.andy.sprintbootsqlunit6.resources;

import com.andy.sprintbootsqlunit6.domain.DTO.LoanDto;
import com.andy.sprintbootsqlunit6.domain.Loan;
import com.andy.sprintbootsqlunit6.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
public class LoanResource {

  private final LoanService loanService;

  public LoanResource(LoanService loanService) {
    this.loanService = loanService;
  }

  @GetMapping
  public ResponseEntity<List<LoanDto>> getLoans() {
    List<LoanDto> loans = loanService.getLoans().stream().map(Loan::getDto).collect(Collectors.toList());
    return new ResponseEntity<>(loans, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<LoanDto> post(@RequestBody Map<String, Object> requestBody) {
    LoanDto loan = loanService.create(requestBody).getDto();
    return new ResponseEntity<>(loan, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Boolean>> post(@PathVariable Integer id) {
    loanService.delete(id);
    Map<String, Boolean> response = new HashMap<>();
    response.put("success", true);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
