package com.example.secondapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.lib.State;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesActivity extends AppCompatActivity {

    TextView username;
    ImageView online,offline;
    SharedPreferences pref;
    CircleImageView imageView;

    FirebaseUser user1= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference referenceState=database.getReference("State").child(user1.getUid());




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref=getApplicationContext().getSharedPreferences("userPref",MODE_PRIVATE);
        imageView=findViewById(R.id.imgUser);
        username= findViewById(R.id.textViewUsers);
        online= findViewById(R.id.image_online);
        offline= findViewById(R.id.image_offline);

        final String idUserPref=pref.getString("userPref"," ");

        String user=getIntent().getExtras().getString("name");
        String photo=getIntent().getExtras().getString("img");
        String idUser=getIntent().getExtras().getString("idUser");
        String id=getIntent().getExtras().getString("id");

        username.setText(user);
        Glide.with(this).load(photo).into(imageView);

        final DatabaseReference ref=database.getReference("State").child(idUserPref).child("chat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chat= snapshot.getValue(String.class);
                if(snapshot.exists()){
                    if(chat.equals(user1.getUid())){
                        online.setVisibility(View.VISIBLE);
                        offline.setVisibility(View.GONE);
                    }else{
                        online.setVisibility(View.GONE);
                        offline.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void userState(String state) {
        referenceState.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String id=pref.getString("userPref"," ");

                State state1=new State(state," "," ",id);
                referenceState.setValue(state1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        userState("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        userState("offline");
        getTime();

    }

    private void getTime() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat time=new SimpleDateFormat("HH:mm");
        SimpleDateFormat date= new SimpleDateFormat("dd/MM/yyyy");

        referenceState.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                referenceState.child("date").setValue(date.format(calendar.getTime()));
                referenceState.child("time").setValue(time.format(calendar.getTime()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}