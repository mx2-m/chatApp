package com.example.secondapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lib.Requests;
import com.example.lib.User;
import com.example.secondapplication.ApplicationChat;
import com.example.secondapplication.MessagesActivity;
import com.example.secondapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolderAdapterMessages> {

    List<User> list;
    Context context;
    ApplicationChat app;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SharedPreferences pref;


    public UsersAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolderAdapterMessages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, parent, false);
        viewHolderAdapterMessages holder = new viewHolderAdapterMessages(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapterMessages holder, int position) {
        User users = list.get(position);

        // boolean vibration = app.isVibration();

        final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Glide.with(context).load(users.getPhoto()).into(holder.imageView);
        holder.textView.setText(users.getName());

        if (users.getId().equals(user.getUid())) {
            holder.cardView.setVisibility(View.GONE);
        } else {
            holder.cardView.setVisibility(View.VISIBLE);
        }

        DatabaseReference buttons = database.getReference("Requests").child(user.getUid());
        buttons.child(users.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state = snapshot.child("state").getValue(String.class);
                if (snapshot.exists()) {
                    if (state.equals("sent")) {
                        holder.send.setVisibility(View.VISIBLE);
                        holder.add.setVisibility(View.GONE);
                        holder.request.setVisibility(View.GONE);
                        holder.friends.setVisibility(View.GONE);
                    }
                    if (state.equals("friends")) {
                        holder.friends.setVisibility(View.VISIBLE);
                        holder.add.setVisibility(View.GONE);
                        holder.request.setVisibility(View.GONE);
                        holder.send.setVisibility(View.GONE);

                    }

                    if (state.equals("request")) {
                        holder.request.setVisibility(View.VISIBLE);
                        holder.add.setVisibility(View.GONE);
                        holder.friends.setVisibility(View.GONE);
                        holder.send.setVisibility(View.GONE);

                    }
                } else {
                    holder.add.setVisibility(View.VISIBLE);
                    holder.request.setVisibility(View.GONE);
                    holder.friends.setVisibility(View.GONE);
                    holder.send.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.friends.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("Kliknuto","!!!!!");

                // if (vibration) {
                vibrator.vibrate(400);
                // }
            }
        });

        holder.friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Kliknuto","!!!!!");

                pref=v.getContext().getSharedPreferences("usersPreferences",Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor=pref.edit();

                final DatabaseReference reference= database.getReference("Requests").child(user.getUid()).child(users.getId()).child("idChat");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String id= snapshot.getValue(String.class);
                        if(snapshot.exists()){

                            Intent intent= new Intent(v.getContext(), MessagesActivity.class);
                            intent.putExtra("name",users.getName());
                            intent.putExtra("img",users.getPhoto());
                            intent.putExtra("idUser",users.getId());
                            intent.putExtra("id",id);
                            editor.putString("userPref",users.getId());
                            editor.apply();

                            v.getContext().startActivity(intent);

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                vibrator.vibrate(400);

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("Kliknuto","!!!!!");

                DatabaseReference reference = database.getReference("Requests").child(user.getUid());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Requests request= new Requests("sent","");
                        reference.child(users.getId()).setValue(request);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                DatabaseReference reference1 = database.getReference("Requests").child(users.getId());
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Requests request= new Requests("request","");
                        reference1.child(user.getUid()).setValue(request);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                DatabaseReference count = database.getReference("Count").child(users.getId());
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Integer value = snapshot.getValue(Integer.class);
                            if (value == 0) {
                                count.setValue(1);
                            } else {
                                count.setValue(value + 1);
                            }

                        } else {
                            count.setValue(1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // if (vibration) {
                vibrator.vibrate(400);
                // }
            }
        });

        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String idChat= buttons.push().getKey();

                DatabaseReference reference = database.getReference("Requests").child(users.getId()).child(user.getUid());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Requests request= new Requests("friends",idChat);   //jedinstveni id
                        reference.setValue(request);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference reference1 = database.getReference("Requests").child(user.getUid()).child(users.getId());
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Requests request= new Requests("friends",idChat);
                        reference1.setValue(request);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                // if (vibration) {
                vibrator.vibrate(400);
                //  }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolderAdapterMessages extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        CardView cardView;
        Button add, send, request, friends;
        ProgressBar progressBar;


        public viewHolderAdapterMessages(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewUsers);
            imageView = itemView.findViewById(R.id.imgUser);
            cardView = itemView.findViewById(R.id.cardView);
            add = itemView.findViewById(R.id.add);
            send = itemView.findViewById(R.id.send);
            request = itemView.findViewById(R.id.request);
            friends = itemView.findViewById(R.id.friend);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}



