package com.UniBook.Dao;

import java.util.*;
import com.UniBook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("From Contact as c where c.user.id =: userId")
    public List<Contact> findContactByUserId(int userId);

}
