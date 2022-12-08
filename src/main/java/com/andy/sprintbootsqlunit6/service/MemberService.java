package com.andy.sprintbootsqlunit6.service;

import com.andy.sprintbootsqlunit6.domain.Member;
import com.andy.sprintbootsqlunit6.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public List<Member> getMembers() {
    return memberRepository.findAll();
  }

  private Member mapMemberNoId(Map<String, Object> memberJson) {
    String firstName = (String) memberJson.getOrDefault("firstName", "");
    String surname = (String) memberJson.getOrDefault("surname", "");
    if (firstName.isEmpty() || surname.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    Member member = new Member();
    member.setFirstName(firstName);
    member.setSurname(surname);
    return member;
  }

  public Member create(Map<String, Object> memberJson) {
    return memberRepository.save(mapMemberNoId(memberJson));
  }
}
