package com.example.secondapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lib.User;
import com.example.secondapplication.R;
import com.example.secondapplication.adapter.MessagesAdapter;
import com.example.secondapplication.adapter.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {


    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProgressBar progressBar;

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View view= inflater.inflate(R.layout.fragment_chats, container, false);


        progressBar = view.findViewById(R.id.progressBar);

        assert user != null;

        RecyclerView rv;


        ArrayList<User> list;
        MessagesAdapter adapter;
        LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        adapter = new MessagesAdapter(list, getContext());
        rv.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    list.removeAll(list);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        rv.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        User user1 = snapshot1.getValue(User.class);
                        list.add(user1);

                    }
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getContext(), "No users", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}