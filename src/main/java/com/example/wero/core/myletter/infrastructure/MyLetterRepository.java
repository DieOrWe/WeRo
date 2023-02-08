package com.example.wero.core.myletter.infrastructure;

import com.example.wero.core.myletter.domain.MyLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MyLetterRepository extends JpaRepository<MyLetter, String> {
    Optional<MyLetter> findByMyLetterIdAndWriterId(String myLetterId, String writerId);

    List<MyLetter> findByWriterId(String writerId);

    List<MyLetter> findByMyLetterIsPrivate(Boolean myLetterIsPrivate);

//    @Query(value = "SELECT m.myLetterId FROM MyLetters m WHERE m.created_when > str_to_date(time, %Y-%M-%d)")
//    List<String> MyLettersLetterId(@Param(value = "time") String myLetterCreatedWhen);
//    MyLetter findAllByLetterId(String myLetterId);
}
