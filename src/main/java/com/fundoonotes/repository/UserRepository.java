package com.fundoonotes.repository;

import com.fundoonotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    //   UC3:Abstract method constructed by Spring Data JPA to fetch user by email
    Optional<User> findByEmail(String email);
}
