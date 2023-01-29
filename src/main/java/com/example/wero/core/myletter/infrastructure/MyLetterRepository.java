package com.example.wero.core.myletter.infrastructure;

import com.example.wero.core.myletter.domain.MyLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyLetterRepository extends JpaRepository<MyLetter, String> {

}
