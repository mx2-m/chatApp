package com.example.secondapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.secondapplication.HomeActivity;
import com.example.secondapplication.LoginActivity;
import com.example.secondapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

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

        final Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                GoogleSignIn.getClient(view.getContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                        .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(view.getContext(), LoginActivity.class));
                    }
                });/*.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.class, "Signout Failed.", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

        return view;
    }


}