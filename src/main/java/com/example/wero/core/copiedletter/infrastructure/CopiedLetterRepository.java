package com.example.wero.core.copiedletter.infrastructure;

import com.example.wero.core.copiedletter.domain.CopiedLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopiedLetterRepository extends JpaRepository<CopiedLetter, String> {
}
