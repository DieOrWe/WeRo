package com.example.wero.core.myletter.infrastructure;

import com.example.wero.core.myletter.domain.MyLetter;

import com.example.wero.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface MyLetterRepository extends JpaRepository<MyLetter, String> {

    @Query(value = "SELECT * FROM MY_LETTERS m WHERE m.MY_LETTER_IS_PRIVATE = false", nativeQuery = true)
    List<MyLetter> myLetterFindAllByMyLetterIsPrivate();

//    @Query(value = "SELECT USER_ID FROM MY_LETTERS WHERE NOT USERNICKNAME = :SENDER ORDER BY RAND() LIMIT :NUM", nativeQuery = true)
//    List<String> randomSelectUserId(@Param("NUM") int numberOfNeededUser, @Param("SENDER") List<String> userNickname);
//    @Query(value = "SELECT USER_ID FROM MY_LETTERS ORDER BY RAND() LIMIT :NUM", nativeQuery = true)
//    List<String> randomSelectUserId(@Param("NUM") int numberOfNeededUser);
    @Query(value = "SELECT USER_ID FROM (SELECT * FROM USERS WHERE NOT USER_NICK_NAME = :NICK) LIMIT 1", nativeQuery = true)
    Optional<String> getUserIdByNickName(@Param("NICK") String userNickname);

    @Query(value = "SELECT m.MY_LETTER_ID FROM MY_LETTERS m WHERE m.CREATED_WHEN > :time", nativeQuery = true)
    List<MyLetter> newMyLetters(@Param(value = "time") Date letterReceivedWhen);
}
