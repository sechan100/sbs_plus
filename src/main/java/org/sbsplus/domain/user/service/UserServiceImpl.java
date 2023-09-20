package org.sbsplus.domain.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sbsplus.domain.cummunity.repository.ArticleRepository;
import org.sbsplus.domain.cummunity.repository.CommentRepository;
import org.sbsplus.domain.notice.NoticeRepository;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.repository.AnswerRepository;
import org.sbsplus.domain.qna.repository.QuestionRepository;
import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.repository.UserRepository;
import org.sbsplus.util.ModelMapperConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final NoticeRepository noticeRepository;

    private final PasswordEncoder passwordEncoder;

    
    @Qualifier("dtoToUser")
    private final ModelMapper dtoToUser;
    @Qualifier("userToDto")
    private final ModelMapper userToDto;
    

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
        
        User user = dtoToUser.map(userDto, User.class);

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
    public void delete(UserDto userDto) {
        User user = dtoToUser.map(userDto, User.class);
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserDtoById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        return userToDto.map(user, UserDto.class);
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
    public Page<User> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.userRepository.findAll(pageable);
    }
}











