package com.example.wero.core.receiveduser.infrastructure;

import com.example.wero.core.receiveduser.domain.ReceivedUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceivedUserRepository extends JpaRepository<ReceivedUser, Integer> {

    @Query(value = "SELECT LETTER_RECEIVED_WHEN FROM (SELECT * FROM RECEIVED_USERS ORDER BY LETTER_RECEIVED_WHEN DESC) WHERE ROWNUM = 1", nativeQuery = true)
    String RecentReceivedLetter();

    @Query(value = "SELECT * FROM RECEIVED_USERS WHERE USER_ID IS NULL", nativeQuery = true)
    List<ReceivedUser> findByUserIdIsNull();
    
    @Transactional
    Optional<String> deleteByMyLetterId(String myLetterId);
    
    @Query(value = "SELECT USER_ID FROM RECEIVED_USERS", nativeQuery = true)
    Collection<String> findUserIds();


}
