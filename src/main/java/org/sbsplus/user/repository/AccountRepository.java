package org.sbsplus.user.repository;

import org.sbsplus.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    int countByUsername(String username);

    int countByEmail(String email);

    int countByPhone(String phone);

    Account findByUsername(String username);
}
