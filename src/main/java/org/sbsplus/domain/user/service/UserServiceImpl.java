package org.sbsplus.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Qualifier("dtoToUser")
    private final ModelMapper mapper;
    

    @Override
    public int countByEmail(String email) {
        return userRepository.countByEmail(email);
    }
    
    
    @Override
    public boolean isValidUsername(String username) {
        User userWithIdenticalUsername = userRepository.findByUsername(username);

        return (userWithIdenticalUsername == null);
    }

    @Override
    public boolean isValidEmail(String email) {
        int registeredEmail = userRepository.countByEmail(email);

        return (registeredEmail == 0);
    }

    @Override
    public boolean confirmPassword(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }
    
    
    @Override
    public User convertToEntityWithRole(UserDto userDto, String role) {
        
        User user = mapper.map(userDto, User.class);

        // grant role
        user.setRole(role);

        return user;
    }

    @Override
    public void save(UserDto userDto) {
        
        User user = convertToEntityWithRole(userDto, "USER");
        userRepository.save(user);
        
        log.info("\n#####[ New User Registered ]##### \n"
                + "이름: " + user.getName() +"\n"
                + "아이디: " + user.getUsername() +"\n"
                + "수업 유형: " + user.getCategory().getName() +"\n"
                + "이메일: " + user.getEmail()
                + "\n################################"
        );
    }
    
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long userId) {
        return null;
    }
}











