package com.example.fashionlog.repository;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmailAndStatusIsTrue(String username);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Member> findByRoleNot(Role role);
}