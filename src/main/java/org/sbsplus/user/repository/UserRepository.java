package org.sbsplus.user.repository;

import org.sbsplus.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByUsername(String username);

    int countByEmail(String email);
}
