package org.sbsplus.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.subject.Subject;
import org.sbsplus.user.entity.Account;
import org.sbsplus.user.dto.AccountDto;
import org.sbsplus.user.service.validation.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

    private final AccountService accountValidationService;


    // get request register form page
    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("account", new AccountDto());
        model.addAttribute("subjects", Subject.getSubjects());
        return "/user/register";
    }


    // register process
    @PostMapping("/register")
    public String register_prcs(AccountDto accountDto) {


        // username invalid
        if(!accountValidationService.isValidUsername(accountDto.getUsername())){
            return "redirect:/register?error=true&type=username";

        // email invalid
        } else if(accountValidationService.isValidEmail(accountDto.getEmail())) {
            return "redirect:/register?error=true&type=email";

        // phone invalid
        } else if(accountValidationService.isValidPhone(accountDto.getPhone())) {
            return "redirect:/register?error=true&type=phone";

        // match password with confirmPassword
        } else if(accountValidationService.confirmPassword(accountDto.getPassword(), accountDto.getConfirmPassword())) {
            return "redirect:/register?error=true&type=password";
        }


        // Convert AccountDto -> Account(Entity)
        Account account = accountValidationService.convertToEntityWithRole(accountDto, "USER");

        accountValidationService.save(account);

        return "redirect:/login";
    }
}