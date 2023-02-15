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
    
    Optional<ReceivedUser> findByMyLetterId(String myLetterId);
//    (select * from received_users order by letter_received_when desc) where ROWNUM = 1 rd
    @Query(value = "select letter_received_when from received_users order by letter_received_when desc limit 1", nativeQuery = true)
    String RecentReceivedLetter();
    
    
    @Query(value = "select * from received_users r where r.user_id is null", nativeQuery = true)
    List<ReceivedUser> findByUserIdIsNull();
    
    @Transactional
    Optional<String> deleteByMyLetterId(String myLetterId);
    
    @Query(value = "select my_letter_id from received_users", nativeQuery = true)
    List<String> findLetterIds();


}
