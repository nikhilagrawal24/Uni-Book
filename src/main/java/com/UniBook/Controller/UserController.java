package com.UniBook.Controller;

import com.UniBook.Dao.ComplaintRepository;
import com.UniBook.Dao.ContactRepository;
import com.UniBook.Dao.UserRepository;
import com.UniBook.model.Complain;
import com.UniBook.model.Contact;
import com.UniBook.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @RequestMapping("/")
    public String signin(Model model, Principal principal) {
        String userName = principal.getName();
        User user = this.userRepository.findByEmail(userName);
        model.addAttribute("title", "Uni-Book - Dashboard");
        model.addAttribute("user", user);

        //getting user contact list
        List<Contact> contacts = user.getContacts();
        model.addAttribute("contacts", contacts);
        return "user/dashboard";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        String userName = principal.getName();
        User user = this.userRepository.findByEmail(userName);
        model.addAttribute("user", user);
        model.addAttribute("title", "Uni-Book - Profile");
        return "user/profile";
    }

    @RequestMapping("/updateContact/{cId}")
    public String UpdateContact(@PathVariable("cId") int cId, Model model, Principal principal) {
        Contact contact = this.contactRepository.findById(cId).get();
        model.addAttribute("contact", contact);
        String userName = principal.getName();
        User user = this.userRepository.findByEmail(userName);
        model.addAttribute("user", user);
        model.addAttribute("title", "Uni-Book - Update Contact");
        return "user/UpdateContact";
    }

    @RequestMapping("/updateProfile")
    public String UpdateProfile(Model model, Principal principal) {
        String userName = principal.getName();
        User user = this.userRepository.findByEmail(userName);
        model.addAttribute("user", user);
        model.addAttribute("title", "Uni-Book - Update Profile");
        return "user/UpdateProfile";
    }

    @RequestMapping("/about")
    public String about(Model model, Principal principal) {
        String userName = principal.getName();
        User user = this.userRepository.findByEmail(userName);
        model.addAttribute("user", user);
        model.addAttribute("title", "Uni-Book - About us");
        return "user/about";
    }

    @RequestMapping("/contact")
    public String contact(Model model, Principal principal) {
        String userName = principal.getName();
        User user = this.userRepository.findByEmail(userName);
        model.addAttribute("user", user);
        model.addAttribute("title", "Uni-Book - Contact us");
        return "user/contact";
    }

    @PostMapping("/addContact")
    public String addContact(@ModelAttribute("contact") Contact contact, BindingResult result, @RequestParam("cProfilePic") MultipartFile file, Principal principal, Model model) {

        try {
            if (file.isEmpty()) {
            } else {
                contact.setCProfilePic(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            String name = principal.getName();
            User user = this.userRepository.findByEmail(name);
            user.getContacts().add(contact);
            contact.setUser(user);
            this.userRepository.save(user);
            model.addAttribute("user", user);

            //getting user contact list
            List<Contact> contacts = user.getContacts();
            model.addAttribute("contacts", contacts);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return "redirect:/user/";
    }

    @GetMapping("/delete/{cId}")
    public String deleteContact(@PathVariable("cId") int cId, Model model) throws IOException {

        Contact contact = this.contactRepository.findById(cId).get();
        File file = new ClassPathResource("static/img").getFile();
        File cImage = new File(file, contact.getCProfilePic());
        cImage.delete();

        contact.setUser(null);
        this.contactRepository.delete(contact);

        return "redirect:/user/";
    }

    @PostMapping("/contactUpdate")
    public String contactUpdate(@ModelAttribute("contact") Contact contact, BindingResult result, @RequestParam("cId") int cId,
                                Principal principal, Model model, @RequestParam("cProfilePic") MultipartFile file) {

        model.addAttribute("contact", contact);
        Contact contact1 = this.contactRepository.findById(cId).get();
        User user = this.userRepository.findByEmail(principal.getName());

        model.addAttribute("user", user);

        try {
            if (file.isEmpty()) {
                contact.setCProfilePic(contact1.getCProfilePic());
            } else {
                File oldProfile = new ClassPathResource("static/img").getFile();
                File oldfile = new File(oldProfile, user.getId() + "_" + contact1.getCProfilePic());
                oldfile.delete();

                contact.setCProfilePic(file.getOriginalFilename());
                File newProfilePic = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(newProfilePic.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            contact.setUser(user);
            this.contactRepository.save(contact);
            model.addAttribute("contact", new Contact());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/";
    }


    @PostMapping("/updateprofile")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result, @RequestParam("imageUrl") MultipartFile file,
                             Principal principal) throws IOException {

        String username = principal.getName();
        User oldUser = this.userRepository.findByEmail(username);

        if (file.isEmpty()) {
            user.setImageUrl(oldUser.getImageUrl());
        } else {
            File oldProfile = new ClassPathResource("static/img").getFile();
            File oldfile = new File(oldProfile, oldUser.getId() + "_" + oldUser.getImageUrl());
            oldfile.delete();

            user.setImageUrl(file.getOriginalFilename());
            File newProfilePic = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(newProfilePic.getAbsolutePath() + File.separator + oldUser.getId() + "_" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


            user.setId(oldUser.getId());
            user.setCheckbox(true);
        }
        this.userRepository.save(user);

        return "redirect:/user/";
    }

    //Contact us form
    @PostMapping("/complain")
    private String requestHelp(@ModelAttribute("complain") Complain complain,
                               Model model) {
        this.complaintRepository.save(complain);

        model.addAttribute("title", "Contact us");
        model.addAttribute("complaint", new Complain());

        return "redirect:/user/contact";
    }


}
