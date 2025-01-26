package com.springJWT.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springJWT.model.User;

@Repository // Explicit annotation for clarity
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Fetch a user by username
    Optional<User> findByUsername(String username);
    
    // Example: If you want to search case-insensitively
    // @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    // Optional<User> findByUsernameIgnoreCase(@Param(\"username\") String username);
    
    // Check if a user exists by username
    boolean existsByUsername(String username);

    // Check if a user with the same username exists but with a different id
    boolean existsByUsernameAndIdNot(String username, int id);
}
