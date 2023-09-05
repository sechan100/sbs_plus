package org.sbsplus.user.service.validation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sbsplus.subject.Subject;
import org.sbsplus.user.dto.AccountDto;
import org.sbsplus.user.entity.Account;
import org.sbsplus.user.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Account findById(Integer id) {
        Optional<Account> account_ = accountRepository.findById(id);
        return account_.orElseThrow(()-> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }

    @Override
    public int countByUsername(String username) {
        return accountRepository.countByUsername(username);
    }

    @Override
    public int countByEmail(String email) {
        return accountRepository.countByEmail(email);
    }

    @Override
    public int countByPhone(String phone) {
        return accountRepository.countByPhone(phone);
    }

    @Override
    public boolean isValidUsername(String username) {
        int registeredUsername = accountRepository.countByUsername(username);

        return (registeredUsername == 0);
    }

    @Override
    public boolean isValidEmail(String email) {
        int registeredEmail = accountRepository.countByEmail(email);

        return (registeredEmail != 0);
    }

    @Override
    public boolean isValidPhone(String phone) {
        int registeredPhone = accountRepository.countByPhone(phone);

        return (registeredPhone != 0);
    }

    @Override
    public boolean confirmPassword(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    @Override
    public boolean isVaildPhoneFormat(String phone) {
        // 구현 보류
        return true;
    }
    
    
    @Override
    public Account convertToEntityWithRole(AccountDto accountDto, String role) {

        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);

        // password encode
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        // parse Subject type String to Enum
        account.setSubject(Subject.convertStringToEnum(accountDto.getSubject()));

        // grant role
        account.setRole(role);

        return account;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
        log.info("\n *** New Account Registered *** \n"
                + "이름: " + account.getName() +"\n"
                + "아이디: " + account.getUsername() +"\n"
                + "수업 유형: " + account.getSubject().getCategoryName() +"\n"
                + "이메일: " + account.getEmail()
                + "\n" + "*****************************"
        );
    }
}
