package com.adurcup.registrationdemoapp.cotentProvider;

/**
 * Created by kshivang on 08/04/16.
 *
 * This is Constructor for User Credential response
 */
public class User {
    public String apiKey, name, email, contact;

    public User(String apiKey, String name, String email, String contact) {
        this.apiKey = apiKey;
        this.name = name;
        this.email = email;
        this.contact = contact;
    }

}
