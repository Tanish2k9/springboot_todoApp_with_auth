package com.tanish.Task_Springboot.repositories;

import com.tanish.Task_Springboot.entities.User;
import com.tanish.Task_Springboot.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole admin);
}
