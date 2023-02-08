package com.example.wero.core.receiveduser.infrastructure;

import com.example.wero.core.receiveduser.domain.ReceivedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceivedUserRepository extends JpaRepository<ReceivedUser, Integer> {
    Optional<ReceivedUser> findByMyLetterId(String letterId);

    Optional<ReceivedUser> deleteByMyLetterId(String letterId);

//    @Query(value = "SELECT r.myLetter_id FROM ReceivedUsers r")
//    Collection<String> ReceivedUserLetterId();
}
