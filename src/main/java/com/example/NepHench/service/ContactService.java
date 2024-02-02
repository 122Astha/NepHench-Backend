package com.example.NepHench.service;

import com.example.NepHench.beans.ContactRequest;
import com.example.NepHench.model.Contact;

public interface ContactService {

    Contact postContact(ContactRequest request);
}
