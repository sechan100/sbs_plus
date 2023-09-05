package org.sbsplus.user.service.account;

import org.sbsplus.user.dto.UserDto;
import org.sbsplus.user.entity.User;

public interface UserService {

    User findById(Integer id);

    int countByUsername(String username);

    int countByEmail(String email);
    
    // 이미 존재하는 username 확인
    boolean isValidUsername(String username);

    // 이미 존재하는 이메일 확인
    boolean isValidEmail(String email);

    // 비밀번호 확인이 일치하는가
    boolean confirmPassword(String password, String confirmPassword);
    

    /**
     * convert Dto object to Entity with configured PasswordEncoder Bean wired RegisterService
     * @param accountDto Dto Object
     * @param role USER, ADMIN etc..
     * @return Account Entity
     */
    User convertToEntityWithRole(UserDto accountDto, String role);

    void save(User account);

}
