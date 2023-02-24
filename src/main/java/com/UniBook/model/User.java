package com.UniBook.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "This Field is required")
	@Size(min = 2, max = 20, message = "Name length must be 2 to 20 characters")
    private String name;
    @Email(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}")
    private String email;
    @Size(min = 10, max = 20, message = "This Field is required")
    private String number;
    @NotBlank(message = "This Field is required")
    private String password;
    @AssertTrue
    private boolean checkbox;
    private String imageUrl = "profile.png";
    private String role = "ROLE_USER";
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Contact> contacts = new ArrayList<>();

}
