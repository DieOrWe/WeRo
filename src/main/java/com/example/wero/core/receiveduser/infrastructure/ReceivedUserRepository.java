package com.example.wero.core.receiveduser.infrastructure;

import com.example.wero.core.receiveduser.domain.ReceivedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceivedUserRepository extends JpaRepository<ReceivedUser, String> {
    Optional<ReceivedUser> findByMyLetterId(String letterId);

    Optional<ReceivedUser> deleteByMyLetterId(String letterId);
}
