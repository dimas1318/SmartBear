package com.example.android.smartbear.database;

import com.example.android.smartbear.AvailableCourse;
import com.example.android.smartbear.MainActivity;
import com.example.android.smartbear.R;
import com.example.android.smartbear.Student;
import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.events.ListOfAllCoursesDownloadedEvent;
import com.example.android.smartbear.events.ListOfCoursesDownloadedEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by parsh on 19.11.2017.
 */

public class CourseManagerFirebase implements CourseManager {
    private final Bus bus;

    public CourseManagerFirebase() {
        this.bus = MainActivity.bus;
        bus.register(this);
    }

    @Override
    public List<Course> getUserCourses() {
        final List<Course> studentCourses = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Students");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final Student student = ds.getValue(Student.class);
                    if (student.getStudentId().equals(userID)) {
                        if (student.getAvailableCourses() != null) {
                            final DatabaseReference coursesReference = firebaseDatabase.getReference("Courses");
                            coursesReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        Course course = ds.getValue(Course.class);
                                        for (AvailableCourse availableCourse : student.getAvailableCourses()) {
                                            if (availableCourse != null && availableCourse.getCourseId() == course.getCourseId()) {
                                                studentCourses.add(new Course(R.drawable.logo, course.getName(), course.getLessons()));
                                                break;
                                            }
                                        }
                                        bus.post(new ListOfCoursesDownloadedEvent(studentCourses));
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return studentCourses;
    }

    @Override
    public List<Course> getAllCourses() {
        final List<Course> courseListItems = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referenceCourses = firebaseDatabase.getReference("Courses");
        referenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    courses.add(ds.getValue(Course.class));
                }

                for (Course course : courses) {
                    courseListItems.add(new Course(R.drawable.logo, course.getName(), course.getLessons()));
                }

                bus.post(new ListOfAllCoursesDownloadedEvent(courseListItems));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return courseListItems;
    }

    @Override
    public void deleteCourse(final Course course, final int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        final String name = course.getName();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Courses");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Course course = ds.getValue(Course.class);
                    if (course.getName().equals(name)) {
                        final int id = course.getCourseId();

                    final DatabaseReference studentReference = firebaseDatabase.getReference("Students");
                        studentReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    Student student = ds.getValue(Student.class);
                                    if (student.getStudentId().equals(userID)) {
                                        for(DataSnapshot availableCoursesDs : ds.getChildren()) {
                                            for(DataSnapshot availableCourseDs : availableCoursesDs.getChildren()) {
                                                if (availableCourseDs.getValue(AvailableCourse.class).getCourseId() == id) {
                                                    studentReference.child(ds.getKey()).child(availableCoursesDs.getKey()).child(availableCourseDs.getKey()).removeValue();
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private List<Student> getStudentList() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Students");
        final List<Student> students = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    students.add(ds.getValue(Student.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return students;
    }


    @Override
    public void addCourse(Course course) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        final String name = course.getName();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Courses");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Course course = ds.getValue(Course.class);
                    if (course.getName().equals(name)) {
                        final int id = course.getCourseId();

                        final DatabaseReference studentReference = firebaseDatabase.getReference("Students");
                        studentReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    Student student = ds.getValue(Student.class);
                                    if (student.getStudentId().equals(userID)) {
                                        if (student.getAvailableCourses() != null) {
                                            for (AvailableCourse availableCourse : student.getAvailableCourses()) {
                                                if (availableCourse != null && availableCourse.getCourseId() == id) {
                                                    return;
                                                }
                                            }
                                        }
                                        for(DataSnapshot availableCoursesDs : ds.getChildren()) {
                                            Map<String, Integer> value = new HashMap<>();
                                            value.put("CourseID", id);
                                            studentReference.child(ds.getKey()).child(availableCoursesDs.getKey())
                                                    .child(String.valueOf(id - 1))
                                                    .setValue(value);
                                            return;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}