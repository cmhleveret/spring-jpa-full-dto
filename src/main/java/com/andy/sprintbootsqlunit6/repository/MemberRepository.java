package com.andy.sprintbootsqlunit6.repository;

import com.andy.sprintbootsqlunit6.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
