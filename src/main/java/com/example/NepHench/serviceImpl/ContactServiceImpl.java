package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.ContactRequest;
import com.example.NepHench.model.Contact;
import com.example.NepHench.repository.ContactRepository;
import com.example.NepHench.service.ContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact postContact(ContactRequest request) {
        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setSubject(request.getSubject());
        contact.setMessage(request.getMessage());
        return contactRepository.save(contact);
    }
}
