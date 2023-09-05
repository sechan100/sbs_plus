package org.sbsplus.user.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.security.principal.UserContext;
import org.sbsplus.subject.Subject;
import org.sbsplus.user.dto.UserDto;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.service.account.UserService;
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
public class UserModificationController {
    
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;

    @GetMapping("/{username}/modification")
    public String modification(@PathVariable("username") String username, HttpServletRequest request, Model model){

        // url로 요청한 username과 session의 User principal의 username이 동일하지 않은 경우..
        if(!username.equals(rq.getUser().getUsername())){
            throw new AccessDeniedException("해당 계정에 대한 수정 권한이 없습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(rq.getUser(), UserDto.class);
        
        // Dto
        model.addAttribute("user", userDto);
        
        // 수업 유형 리스트
        model.addAttribute("subjects", Subject.getSubjects());

        return "/user/modification";
    }
    

    // modification process
    @PostMapping("/modification")
    @Transactional
    public String modificationPrcs(UserDto userDto) {

        // User Entity for dirty checking
        User user = userService.findById(userDto.getId());
        
        // password 칸에 빈 문자열이 오지 않았을 경우..
        if(!userDto.getPassword().isEmpty()){
            
            // password confirm
            if(userService.confirmPassword(userDto.getPassword(), userDto.getConfirmPassword())) {
                return String.format("redirect:/%s/modification?error=true&type=password", rq.getUser().getUsername());
            } else {
                // password encode and modify entity
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            
        }
        

        /*
         *************************
         * email 변동 체크
         *************************
         */
        // email이 1개인 경우: 수정이 발생하지 않은 경우. (DB에 본인 Email값이 본인 레코드에 1개 있기에 1개로 매칭됨)
        if(1 != userService.countByEmail(userDto.getEmail())){
            
            // DB 데이터와 Dto 데이터 매칭
            if(!user.getEmail().equals(userDto.getEmail())) {
                return String.format("redirect:/%s/modification?error=true&type=email", rq.getUser().getUsername());
            }
            
        // email이 0개인 경우: 수정이 발생
        } else if(0 != userService.countByEmail(userDto.getEmail())){
            
            // Entity 수정
            user.setEmail(userDto.getEmail());
            
        } else {
            // Error로 Redirect
            return String.format("redirect:/%s/modification?error=true&type=email", rq.getUser().getUsername());
        }
        
        
        /*
         *************************
         * phone 변동 체크
         *************************
         */
        // phone이 1개인 경우: 수정이 발생하지 않은 경우. (DB에 본인 phone값이 본인 레코드에 1개 있기에 1개로 매칭됨)
        if(1 != userService.countByPhone(userDto.getPhone())){
            
            // DB 데이터와 Dto 데이터 매칭
            if(!user.getPhone().equals(userDto.getPhone())) {
                return String.format("redirect:/%s/modification?error=true&type=phone", rq.getUser().getUsername());
            }
            
        // phone이 0개인 경우: 수정이 발생
        } else if(0 != userService.countByPhone(userDto.getPhone())){
            
            // Entity 수정
            user.setPhone(userDto.getPhone());
            
        } else {
            // Error로 Redirect
            return String.format("redirect:/%s/modification?error=true&type=phone", rq.getUser().getUsername());
        }
        
        
        /*
         *************************
         * name 변동 체크
         *************************
         */
        if(!user.getName().equals(userDto.getName())) {
            // Entity 수정
            user.setPhone(userDto.getPhone());
        }
        
        /*
         *************************
         * nickname 변동 체크
         *************************
         */
        if(!user.getNickname().equals(userDto.getNickname())) {
            // Entity 수정
            user.setNickname(userDto.getNickname());
        }
        
        /*
         *************************
         * subject 변동 체크
         *************************
         */
        if(!user.getSubject().getSubjectStr().equals(userDto.getSubject())) {
            
            // Entity 수정
            user.setSubject(Subject.convertStringToEnum(userDto.getSubject()));
        }
        
        
        UserContext accountContext = (UserContext) userDetailsService.loadUserByUsername(user.getUsername());
        
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                user, null, accountContext.getAuthorities()
        );
        
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return String.format("redirect:/%s/modification", rq.getUser().getUsername());
    }

}



















