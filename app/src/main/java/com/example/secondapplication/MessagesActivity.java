package com.example.secondapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.lib.Messages;
import com.example.lib.State;
import com.example.secondapplication.adapters.ChatsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesActivity extends AppCompatActivity {

    private TextView username;
    private ImageView online, offline;
    //private SharedPreferences pref;
    private CircleImageView imageView;

    private FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceState = database.getReference("State").child(user1.getUid());
    private DatabaseReference referenceMessage = database.getReference("Messages");
    private EditText editText;
    private ImageButton imageButton;

    private String idChat;
    boolean onlineF = false;
    private RecyclerView rvChats;
    private ChatsAdapter adapter;
    private ArrayList<Messages> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // pref = getApplicationContext().getSharedPreferences("userPref", MODE_PRIVATE);
        imageView = findViewById(R.id.imgUser);
        username = findViewById(R.id.textViewUsers);
        online = findViewById(R.id.image_online);
        offline = findViewById(R.id.image_offline);
        editText = findViewById(R.id.edit_text);
        imageButton = findViewById(R.id.btn_send);


      //  final String idUserPref = pref.getString("userPref", " ");

        String user = getIntent().getExtras().getString("name");
        String photo = getIntent().getExtras().getString("img");
        String idUser = getIntent().getExtras().getString("idUser");
        idChat = getIntent().getExtras().getString("id");

        seen();

        username.setText(user);
        Glide.with(this).load(photo).into(imageView);


      /*  final DatabaseReference ref = database.getReference("State").child(idUserPref).child("chat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chat = snapshot.getValue(String.class);
                if (snapshot.exists()) {
                    if (chat.equals(user1.getUid())) {
                        onlineF = true;
                        online.setVisibility(View.VISIBLE);
                        offline.setVisibility(View.GONE);
                    } else {
                        onlineF = false;
                        online.setVisibility(View.GONE);
                        offline.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = editText.getText().toString();

                if (!message.isEmpty()) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                    String idPush = referenceMessage.push().getKey();

                    if (onlineF) {      //ne radi
                        Messages messages = new Messages(idPush, message, user1.getUid(), idUser, "yes", date.format(calendar.getTime()), time.format(calendar.getTime()));
                        referenceMessage.child(idChat).push().setValue(messages);
                        editText.setText(" ");
                    } else {
                        Messages messages = new Messages(idPush, message, user1.getUid(), idUser, "no", date.format(calendar.getTime()), time.format(calendar.getTime()));
                        referenceMessage.child(idChat).child(idPush).setValue(messages);
                        editText.setText(" ");
                    }
                }
            }
        });

        rvChats = findViewById(R.id.rv);
        rvChats.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rvChats.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        adapter = new ChatsAdapter(list, this);
        rvChats.setAdapter(adapter);

        readMessages();

    }

    private void seen() {

        referenceMessage.child(idChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()) {

                    Messages messages = s.getValue(Messages.class);
                    if (messages.getReciver().equals(user1.getUid())) {
                        referenceMessage.child(idChat).child(messages.getId()).child("viewed").setValue("yes");


                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void readMessages() {
        referenceMessage.child(idChat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.removeAll(list);
                    for (DataSnapshot s : snapshot.getChildren()) {
                        Messages messages = s.getValue(Messages.class);
                        list.add(messages);
                        scroll();
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void scroll() {
        rvChats.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void userState(String state) {
        referenceState.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              //  final String id = pref.getString("userPref", " ");

                State state1 = new State(state, " ", " ", " ");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

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