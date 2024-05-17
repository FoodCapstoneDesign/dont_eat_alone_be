package io.junseok.mealmate.domain.member.repository;

import io.junseok.mealmate.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);

    Optional<Member> findOneWithAuthoritiesByEmail(String email);

    Optional<Member> findByEmail(String email);
}
