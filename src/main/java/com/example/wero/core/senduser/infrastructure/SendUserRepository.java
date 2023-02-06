package com.example.wero.core.senduser.infrastructure;

import com.example.wero.core.senduser.domain.SendUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SendUserRepository extends JpaRepository<SendUser, Integer> {
    Optional<SendUser> findByMyLetterId(String letterId);

    @Transactional
    Optional<SendUser> deleteByMyLetterId(String letterId);
}
