package com.UniBook.Controller;

import com.UniBook.Dao.UserRepository;
import com.UniBook.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class FormSubmitController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("user", user);
                return "signup";
            }
            model.addAttribute("user", new User());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            return "signup";
        }
    }
    @PostMapping("/forgetPassword")
    public String forgetPassoword(@ModelAttribute("user") User user, @RequestParam("email") String email,
                                  @RequestParam("oldPassword") String OldPassword,
                                  @RequestParam("newPassword") String NewPassword) {

        User byEmail = this.userRepository.findByEmail(email);
        System.out.println(byEmail);

        if (email.equals(byEmail.getEmail())) {
            if (this.bCryptPasswordEncoder.matches(OldPassword, byEmail.getPassword())) {
                byEmail.setPassword(this.bCryptPasswordEncoder.encode(NewPassword));
                this.userRepository.save(byEmail);
                return "redirect:/login";
            } else {
                System.out.println("wrong password");
                return "redirect:/forget-password";
            }
        }
        return "redirect:/login";
    }
}
