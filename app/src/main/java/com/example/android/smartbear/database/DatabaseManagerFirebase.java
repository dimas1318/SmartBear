package com.example.android.smartbear.database;

import com.example.android.smartbear.AvailableCourse;
import com.example.android.smartbear.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;

/**
 * Created by parsh on 23.11.2017.
 */

public class DatabaseManagerFirebase {
    public static void saveUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userID = user.getUid();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference referenceCourses = firebaseDatabase.getReference("Students");
            referenceCourses.push().setValue(new Student(userID, Collections.<AvailableCourse>emptyList()));
        }
    }
}
