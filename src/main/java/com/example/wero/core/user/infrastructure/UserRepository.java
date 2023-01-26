package com.example.wero.core.user.infrastructure;

import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserIdAndUserPw(String userId, String userPw);
}
