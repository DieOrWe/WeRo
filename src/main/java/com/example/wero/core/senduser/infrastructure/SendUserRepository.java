package com.example.wero.core.senduser.infrastructure;

import com.example.wero.core.senduser.domain.SendUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SendUserRepository extends JpaRepository<SendUser,String> {
    Optional<SendUser> findByMyLetterId(String letterId);
    Optional<SendUser> deleteByMyLetterId(String letterId);
}
