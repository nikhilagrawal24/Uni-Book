package com.UniBook.Controller;

import com.UniBook.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/index")
    public String home(Model model) {
        model.addAttribute("title", "Welcome to Uni-Book");
        System.out.println("This is home Page");
        return "index";
    }
    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Uni-Book - About us");
        System.out.println("This is about Page");
        return "about";
    }
    @RequestMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Uni-Book - Contact us");
        System.out.println("This is contact Page");
        return "contact";
    }
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Uni-Book - log in or sign up");
        System.out.println("This is login Page");
        return "login";
    }
    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Uni-Book - log in or sign up");
        System.out.println("This is signup Page");
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping("/forget-password")
    public String forgetPassword(Model model) {
        model.addAttribute("title", "Uni-Book - Forget Password | Can't login");
        model.addAttribute("user", new User());
        return "ForgetPassword";
    }



}
