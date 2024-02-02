package com.example.NepHench.controller;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.ContactRequest;
import com.example.NepHench.model.Contact;
import com.example.NepHench.repository.ContactRepository;
import com.example.NepHench.repository.ServiceRepository;
import com.example.NepHench.service.ContactService;
import com.example.NepHench.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

        @Autowired
        private ContactRepository contactRepository;
        private ContactService contactService;

    public ContactController(ContactRepository contactRepository, ContactService contactService) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
    }

        @GetMapping("")
        @ResponseBody
        public List<Contact> getAllContact(){
            return contactRepository.findAll();
        }

        @PostMapping
        public ResponseEntity<ApiResponse> postContact(@RequestBody ContactRequest request){
            Contact contact = contactService.postContact(request);
            ApiResponse response = new ApiResponse("Contact saved");
            return ResponseEntity.ok(response);
        }
}
