package com.example.NepHench.repository;

import com.example.NepHench.beans.ContactRequest;
import com.example.NepHench.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact , Integer> {

}
