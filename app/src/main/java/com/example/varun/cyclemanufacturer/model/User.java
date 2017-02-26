package com.example.varun.cyclemanufacturer.model;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Varun on 14-Jan-17.
 */

public class User {

    public String name;
    public String userId;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name,FirebaseUser user) {
        this.name = name;
        this.userId = user.getUid();
        this.email = user.getEmail();
    }
}
