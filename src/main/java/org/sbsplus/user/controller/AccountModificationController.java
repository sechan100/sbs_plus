package org.sbsplus.user.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.security.principal.AccountContext;
import org.sbsplus.subject.Subject;
import org.sbsplus.user.dto.AccountDto;
import org.sbsplus.user.entity.Account;
import org.sbsplus.user.service.validation.AccountService;
import org.sbsplus.util.Rq;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountModificationController {
    
    private final UserDetailsService userDetailsService;
    private final AccountService accountValidationService;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;

    @GetMapping("/{username}/modification")
    public String modification(@PathVariable("username") String username, HttpServletRequest request, Model model){

        // url로 요청한 username과 session의 Account principal의 username이 동일하지 않은 경우..
        if(!username.equals(rq.getAccount().getUsername())){
            throw new AccessDeniedException("해당 계정에 대한 수정 권한이 없습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        AccountDto account = modelMapper.map(rq.getAccount(), AccountDto.class);
        
        // Dto
        model.addAttribute("account", account);
        
        // 수업 유형 리스트
        model.addAttribute("subjects", Subject.getSubjects());

        return "/user/modification";
    }
    

    // modification process
    @PostMapping("/modification")
    @Transactional
    public String modificationPrcs(AccountDto accountDto) {

        // Account Entity for dirty checking
        Account account = accountValidationService.findById(accountDto.getId());
//        Account account = rq.getAccount();
        
        // password 칸에 빈 문자열이 오지 않았을 경우..
        if(!accountDto.getPassword().isEmpty()){
            
            // password confirm
            if(accountValidationService.confirmPassword(accountDto.getPassword(), accountDto.getConfirmPassword())) {
                return String.format("redirect:/%s/modification?error=true&type=password", rq.getAccount().getUsername());
            } else {
                // password encode and modify entity
                account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            }
            
        }
        

        /*
         *************************
         * email 변동 체크
         *************************
         */
        // email이 1개인 경우: 수정이 발생하지 않은 경우. (DB에 본인 Email값이 본인 레코드에 1개 있기에 1개로 매칭됨)
        if(1 != accountValidationService.countByEmail(accountDto.getEmail())){
            
            // DB 데이터와 Dto 데이터 매칭
            if(!account.getEmail().equals(accountDto.getEmail())) {
                return String.format("redirect:/%s/modification?error=true&type=email", rq.getAccount().getUsername());
            }
            
        // email이 0개인 경우: 수정이 발생
        } else if(0 != accountValidationService.countByEmail(accountDto.getEmail())){
            
            // Entity 수정
            account.setEmail(accountDto.getEmail());
            
        } else {
            // Error로 Redirect
            return String.format("redirect:/%s/modification?error=true&type=email", rq.getAccount().getUsername());
        }
        
        
        /*
         *************************
         * phone 변동 체크
         *************************
         */
        // phone이 1개인 경우: 수정이 발생하지 않은 경우. (DB에 본인 phone값이 본인 레코드에 1개 있기에 1개로 매칭됨)
        if(1 != accountValidationService.countByPhone(accountDto.getPhone())){
            
            // DB 데이터와 Dto 데이터 매칭
            if(!account.getPhone().equals(accountDto.getPhone())) {
                return String.format("redirect:/%s/modification?error=true&type=phone", rq.getAccount().getUsername());
            }
            
        // phone이 0개인 경우: 수정이 발생
        } else if(0 != accountValidationService.countByPhone(accountDto.getPhone())){
            
            // Entity 수정
            account.setPhone(accountDto.getPhone());
            
        } else {
            // Error로 Redirect
            return String.format("redirect:/%s/modification?error=true&type=phone", rq.getAccount().getUsername());
        }
        
        
        /*
         *************************
         * name 변동 체크
         *************************
         */
        if(!account.getName().equals(accountDto.getName())) {
            // Entity 수정
            account.setPhone(accountDto.getPhone());
        }
        
        /*
         *************************
         * nickname 변동 체크
         *************************
         */
        if(!account.getNickname().equals(accountDto.getNickname())) {
            // Entity 수정
            account.setNickname(accountDto.getNickname());
        }
        
        /*
         *************************
         * subject 변동 체크
         *************************
         */
        if(!account.getSubject().getSubjectStr().equals(accountDto.getSubject())) {
            
            // Entity 수정
            account.setSubject(Subject.convertStringToEnum(accountDto.getSubject()));
        }
        
        
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(account.getUsername());
        
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                account, null, accountContext.getAuthorities()
        );
        
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return String.format("redirect:/%s/modification", rq.getAccount().getUsername());
    }

}



















