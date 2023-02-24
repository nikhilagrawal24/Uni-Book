package com.UniBook.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    private String cName;
    private String cNumber;
    private String cEmail;
    private String work;
    private String cProfilePic;
    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Contact{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cNumber='" + cNumber + '\'' +
                ", cEmail='" + cEmail + '\'' +
                ", work='" + work + '\'' +
                ", cProfilePic='" + cProfilePic + '\'' +
                '}';
    }
}
