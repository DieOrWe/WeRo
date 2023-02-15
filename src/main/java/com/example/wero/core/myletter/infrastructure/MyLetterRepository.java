package com.example.wero.core.myletter.infrastructure;

import com.example.wero.core.myletter.domain.MyLetter;

import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface MyLetterRepository extends JpaRepository<MyLetter, String> {
    
    @Query(value = "select my_letter_id from my_letters", nativeQuery = true)
    List<String> findLetterIds();
    
    @Query(value = "select * from my_letters m where m.my_letter_is_private = false", nativeQuery = true)
    List<MyLetter> myLetterFindAllByMyLetterIsPrivate();

    @Query(value = "select * from my_letters where created_when > :time AND my_letter_is_private = false", nativeQuery = true)
    List<MyLetter> newMyLetters(@Param(value = "time") Date letterReceivedWhen);
    
    @Query(value = "SELECT du.user_id FROM (SELECT * FROM users WHERE NOT user_id = :ID ORDER BY RAND()) du LIMIT 1", nativeQuery = true)
    Optional<String> getUserIdById(@Param("ID") String userId);
    
    
}
