package com.example.android.smartbear.database;

import com.example.android.smartbear.AvailableCourse;
import com.example.android.smartbear.Course;
import com.example.android.smartbear.MainActivity;
import com.example.android.smartbear.R;
import com.example.android.smartbear.Student;
import com.example.android.smartbear.courses.data.CourseListItem;
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
import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class CourseManagerFirebase implements CourseManager {
//    private String userID;
    private final Bus bus;

    public CourseManagerFirebase() {
        this.bus = MainActivity.bus;
        bus.register(this);
    }

    @Override
    public List<CourseListItem> getUserCourses() {
        final List<CourseListItem> myCourseListItems = new ArrayList<>();
        //Getting of current user id
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referenceCourses = firebaseDatabase.getReference("Courses");
        referenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    courses.add(ds.getValue(Course.class));
                }

                final List<Course> coursesList = new ArrayList<>();
                coursesList.addAll(courses);

                List<Course> list = new ArrayList<>();

                for (Student student : getStudentList()) {
                    if (userID.equals(student.getStudentId())) {
                        for (AvailableCourse course : student.getAvailableCourses()) {
                            list.add(coursesList.get(course.getCourseId() - 1));
                        }
                    }
                }

                for (Course course : list) {
                    myCourseListItems.add(new CourseListItem(R.drawable.logo, course.getName(), course.getLessons()));
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
//                for (Course course : courses) {
//                    courseListItems.add(new CourseListItem(R.drawable.logo, course.getName(), course.getLessons()));
//                }
                bus.post(new ListOfCoursesDownloadedEvent(myCourseListItems));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        return myCourseListItems;
    }

    @Override
    public List<CourseListItem> getAllCourses() {
        final List<CourseListItem> courseListItems = new ArrayList<>();

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
                    courseListItems.add(new CourseListItem(R.drawable.logo, course.getName(), course.getLessons()));
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
    public void deleteCourse(final CourseListItem course, final int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        final String name = course.getCourseName();

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


}
