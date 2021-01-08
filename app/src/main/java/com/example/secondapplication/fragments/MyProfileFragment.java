package com.example.secondapplication.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.secondapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfileFragment extends Fragment {

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        TextView textView = view.findViewById(R.id.name);
        TextView textView1 = view.findViewById(R.id.mail);
        ImageView imageView = view.findViewById(R.id.imgUser);

        if (user != null) {

            textView.setText(user.getDisplayName());
            Glide.with(this).load(user.getPhotoUrl()).into(imageView);
            textView1.setText(user.getEmail());

        }

        return view;
    }
}