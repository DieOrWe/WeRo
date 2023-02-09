package com.example.wero.core.myletter.infrastructure;

import com.example.wero.core.myletter.domain.MyLetter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface MyLetterRepository extends JpaRepository<MyLetter, String> {

    @Query(value = "SELECT * FROM MY_LETTERS m WHERE m.MY_LETTER_IS_PRIVATE = false", nativeQuery = true)
    List<MyLetter> myLetterFindAllByMyLetterIsPrivate();

    @Query(value = "SELECT m.USER_ID FROM MY_LETTERS m ORDER BY RAND() LIMIT :NUM", nativeQuery = true)
    List<String> randomSelectUserId(@Param("NUM") int numberOfNeededUser);

    @Query(value = "SELECT m.MY_LETTER_ID FROM MY_LETTERS m WHERE m.CREATED_WHEN > :time", nativeQuery = true)
    List<MyLetter> newMyLetters(@Param(value = "time") Date letterReceivedWhen);
}
