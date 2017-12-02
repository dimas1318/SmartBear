package com.example.android.smartbear.course_material;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartbear.R;
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
import java.io.Serializable;
import java.util.List;

/**
 * Created by parsh on 02.12.2017.
 */

public class CourseMaterialFragment extends Fragment {

    public static CourseMaterialFragment newInstance(List<Material> materials) {
        CourseMaterialFragment fragment = new CourseMaterialFragment();

        Bundle args = new Bundle();
        args.putSerializable("MATERIALS", (Serializable) materials);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.material_layout);

        List<Material> materials = (List<Material>) getArguments().getSerializable("MATERIALS");

        for (Material material : materials) {
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

                        final File localFile = new File(getContext().getFilesDir().getPath() + "/" + "1.txt");

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
                                } catch (IOException e) {
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
            linearLayout.addView(materialLayout);
        }

        return view;
    }
}
