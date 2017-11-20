package com.example.android.smartbear.database;

import com.example.android.smartbear.Course;
import com.example.android.smartbear.R;
import com.example.android.smartbear.Student;
import com.example.android.smartbear.courses.data.CourseListItem;
import com.example.android.smartbear.lessons.data.Lesson;
import com.example.android.smartbear.lessons.data.Material;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.internal.Utils;

/**
 * Created by parsh on 19.11.2017.
 */

public class CourseManagerFirebase implements CourseManager {
    private String userID;

    @Override
    public List<CourseListItem> getUserCourses() {
        final List<CourseListItem> courseListItems = new ArrayList<>();

        //Getting of current user id
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referenceCourses = firebaseDatabase.getReference("Courses");
        referenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    courses.add(ds.getValue(Course.class));
                }

//                for (Course course : courses) {
//                    System.out.println(course.getCourseId());
//                    System.out.println(course.getName());
//                    if (course.getLessons() != null) {
//                        for (Lesson lesson : course.getLessons()) {
//                            System.out.println(lesson.getName());
//                            System.out.println(lesson.getOrder());
//                            for (Material material : lesson.getMaterials()) {
//                                System.out.println(material.getName());
//                                if (material.getReference() != null) {
//                                    System.out.println(material.getReference());
//                                }
//                                System.out.println(material.getType());
//                            }
//                        }
//                    }
//                    System.out.println("***********************");
//                }

                for (Course course : courses) {
                    courseListItems.add(new CourseListItem(R.drawable.logo, course.getName()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return courseListItems;
    }

    @Override
    public List<CourseListItem> getAllCourses() {
        return null;
    }

    private List<Student> getStudentList() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Students");
        final List<Student> students = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    students.add(ds.getValue(Student.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return students;
    }
}
