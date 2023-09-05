package org.sbsplus.user.repository;

import org.sbsplus.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    int countByUsername(String username);

    int countByEmail(String email);

    int countByPhone(String phone);

    User findByUsername(String username);
}
