package org.sbsplus.domain.user.repository;

import org.sbsplus.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByUsername(String username);

    int countByEmail(String email);
    
    User findByEmail(String email);
}
