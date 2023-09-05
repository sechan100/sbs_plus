package org.sbsplus.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.type.Subject;
import org.sbsplus.user.dto.UserDto;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.service.account.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;


    // get request register form page
    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new UserDto());
        model.addAttribute("subjects", Subject.getSubjects());
        return "/user/register";
    }


    // register process
    @PostMapping("/register")
    public String register_prcs(UserDto userDto) {


        // username invalid
        if(!userService.isValidUsername(userDto.getUsername())){
            return "redirect:/register?error=true&type=username";

        // email invalid
        } else if(userService.isValidEmail(userDto.getEmail())) {
            return "redirect:/register?error=true&type=email";

        // match password with confirmPassword
        } else if(userService.confirmPassword(userDto.getPassword(), userDto.getConfirmPassword())) {
            return "redirect:/register?error=true&type=password";
        }


        // Convert UserDto -> User(Entity)
        User user = userService.convertToEntityWithRole(userDto, "USER");

        userService.save(user);

        return "redirect:/login";
    }
}