package com.UniBook.Dao;

import com.UniBook.model.Complain;
import com.UniBook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complain, Integer> {
}
