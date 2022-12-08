package com.andy.sprintbootsqlunit6.resources;

import com.andy.sprintbootsqlunit6.domain.DTO.MemberDTO;
import com.andy.sprintbootsqlunit6.domain.Member;
import com.andy.sprintbootsqlunit6.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
public class MemberResource {

  private final MemberService memberService;

  public MemberResource(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping
  public ResponseEntity<List<MemberDTO>> getMembers() {
    List<MemberDTO> members = memberService.getMembers().stream().map(Member::getDto).collect(Collectors.toList());
    return new ResponseEntity<>(members, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<MemberDTO> create(@RequestBody Map<String, Object> requestBody) {
    MemberDTO member = memberService.create(requestBody).getDto();
    return new ResponseEntity<>(member, HttpStatus.CREATED);
  }
}
