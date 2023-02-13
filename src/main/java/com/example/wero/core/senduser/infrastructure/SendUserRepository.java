package com.example.wero.core.senduser.infrastructure;

import com.example.wero.core.senduser.domain.SendUser;

import com.example.wero.core.senduser.domain.SendUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
public interface SendUserRepository extends JpaRepository<SendUser, Integer> {
    Optional<SendUser> findByMyLetterId(String letterId);

    @Transactional
    Optional<SendUser> deleteByMyLetterId(String letterId);
    
    @Query(value = "SELECT MY_LETTER_ID FROM SEND_USERS", nativeQuery = true)
    List<String> findLetterIds();
}
