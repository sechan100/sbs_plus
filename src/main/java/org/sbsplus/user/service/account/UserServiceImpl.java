package org.sbsplus.user.service.account;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sbsplus.subject.Subject;
import org.sbsplus.user.dto.UserDto;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Integer id) {
        Optional<User> account_ = userRepository.findById(id);
        return account_.orElseThrow(()-> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }

    @Override
    public int countByUsername(String username) {
        return userRepository.countByUsername(username);
    }

    @Override
    public int countByEmail(String email) {
        return userRepository.countByEmail(email);
    }

    @Override
    public int countByPhone(String phone) {
        return userRepository.countByPhone(phone);
    }

    @Override
    public boolean isValidUsername(String username) {
        int registeredUsername = userRepository.countByUsername(username);

        return (registeredUsername == 0);
    }

    @Override
    public boolean isValidEmail(String email) {
        int registeredEmail = userRepository.countByEmail(email);

        return (registeredEmail != 0);
    }

    @Override
    public boolean isValidPhone(String phone) {
        int registeredPhone = userRepository.countByPhone(phone);

        return (registeredPhone != 0);
    }

    @Override
    public boolean confirmPassword(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    @Override
    public boolean isVaildPhoneFormat(String phone) {
        // 구현 보류
        // cross checking with front
        return true;
    }
    
    
    @Override
    public User convertToEntityWithRole(UserDto userDto, String role) {

        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);

        // password encode
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // parse Subject type String to Enum
        user.setSubject(Subject.convertStringToEnum(userDto.getSubject()));

        // grant role
        user.setRole(role);

        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
        log.info("\n#####[ New User Registered ]##### \n"
                + "이름: " + user.getName() +"\n"
                + "아이디: " + user.getUsername() +"\n"
                + "수업 유형: " + user.getSubject().getCategoryName() +"\n"
                + "이메일: " + user.getEmail()
                + "\n################################"
        );
    }
}
