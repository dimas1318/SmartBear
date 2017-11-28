package com.example.android.smartbear.materials;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartbear.R;
import com.example.android.smartbear.courses.data.CourseListItem;
import com.example.android.smartbear.lessons.data.Lesson;
import com.example.android.smartbear.lessons.data.Material;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by parsh on 27.11.2017.
 */

public class MaterialFragment extends Fragment {

    public static MaterialFragment newInstance(CourseListItem course) {
        MaterialFragment fragment = new MaterialFragment();

        Bundle args = new Bundle();
        args.putSerializable("COURSE", course);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.lesson_layout);

        CourseListItem course = (CourseListItem) getArguments().getSerializable("COURSE");
        if (course.getLessons() != null) {
            view.findViewById(R.id.no_data).setVisibility(View.GONE);

            for (Lesson lesson : course.getLessons()) {
                View lessonLayout = getLayoutInflater().inflate(R.layout.item_lesson, null);
                ((TextView) (lessonLayout.findViewById(R.id.lesson_name))).setText(lesson.getName());
                ((TextView) (lessonLayout.findViewById(R.id.lesson_order))).setText(String.valueOf(lesson.getOrder()));

                if (lesson.getMaterials() != null) {
                    lessonLayout.findViewById(R.id.material_title).setVisibility(View.VISIBLE);
                    LinearLayout linearLayout1 = lessonLayout.findViewById(R.id.material_layout);
                    linearLayout1.setVisibility(View.VISIBLE);
                    for (Material material : lesson.getMaterials()) {
                        View materialLayout = getLayoutInflater().inflate(R.layout.item_material, null);
                        ((TextView) (materialLayout.findViewById(R.id.material_name))).setText(material.getName());
                        ((TextView) (materialLayout.findViewById(R.id.material_type))).setText(material.getType());

                        if (material.getReference() != null) {
                            TextView materialReference = materialLayout.findViewById(R.id.material_reference);
                            materialReference.setVisibility(View.VISIBLE);
                            materialReference.setText(material.getReference());
                            materialReference.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference storageRef = storage.getReference("1/0/2/1.txt");

                                    final File localFile = new File(getContext().getFilesDir().getPath().toString() + "/" + "1.txt");

                                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            StringBuilder text = new StringBuilder();

                                            try {
                                                BufferedReader br = new BufferedReader(new FileReader(localFile));
                                                String line;

                                                while ((line = br.readLine()) != null) {
                                                    text.append(line);
                                                    text.append('\n');
                                                }
                                                br.close();
                                            }
                                            catch (IOException e) {
                                                Log.e("KEK", e.getMessage());
                                                Toast.makeText(getContext(), "Reading failed!", Toast.LENGTH_SHORT).show();
                                            }

                                            Toast.makeText(getContext(), text.toString(), Toast.LENGTH_SHORT).show();
                                            localFile.delete();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("KEK", e.getMessage());
                                            Toast.makeText(getContext(), "Download failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                        linearLayout1.addView(materialLayout);
                    }
                }
                linearLayout.addView(lessonLayout);
            }
        }

        return view;
    }
}
