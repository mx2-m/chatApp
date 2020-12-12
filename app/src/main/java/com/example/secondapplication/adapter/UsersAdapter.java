package com.example.secondapplication.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lib.User;
import com.example.secondapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolderAdapter> {

    List<User>list;
    Context context;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();



    public UsersAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users,parent,false);
        viewHolderAdapter holder=new viewHolderAdapter(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapter holder, int position) {
        User users= list.get(position);
        final Vibrator vibrator= (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        Glide.with(context).load(users.getPhoto()).into(holder.imageView);
        holder.textView.setText(users.getName());

        if(users.getId().equals(user.getUid())){
           holder.cardView.setVisibility(View.GONE);
        }
        else {
            holder.cardView.setVisibility(View.VISIBLE);
        }

        DatabaseReference buttons=database.getReference("Users").child(user.getUid()).child("Requests").child(users.getId());
        buttons.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state=snapshot.child("state").getValue(String.class);
if(snapshot.exists()){
                if(state.equals("sent")){
                    holder.send.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.GONE);
                    holder.request.setVisibility(View.GONE);
                    holder.friends.setVisibility(View.GONE);
                }
                if(state.equals("friends")){
                    holder.friends.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.GONE);
                    holder.request.setVisibility(View.GONE);
                    holder.send.setVisibility(View.GONE);

                }

                if(state.equals("request")){
                    holder.request.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.GONE);
                    holder.friends.setVisibility(View.GONE);
                    holder.send.setVisibility(View.GONE);

                }}

                else{
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

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference=database.getReference("Users").child(user.getUid()).child("Requests");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        reference.child(users.getId()).child("state").setValue("sent");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                DatabaseReference reference1=database.getReference("Users").child(users.getId()).child("Requests");
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            reference1.child(user.getUid()).child("state").setValue("request");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                DatabaseReference count= database.getReference("Users").child(users.getId()).child("requests");
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.exists()){
                           Integer value=snapshot.getValue(Integer.class);
                           if(value==0){
                               count.setValue(1);
                           }
                           else {
                               count.setValue(value+1);
                           }



                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
vibrator.vibrate(400);
            }
        });

        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference=database.getReference("Users").child(user.getUid()).child("Requests");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        reference.child(users.getId()).child("state").setValue("friends");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference reference1=database.getReference("Users").child(users.getId()).child("Requests");
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        reference1.child(user.getUid()).child("state").setValue("friends");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                vibrator.vibrate(400);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolderAdapter extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        CardView cardView;
        Button add, send, request, friends;
        ProgressBar progressBar;



        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);

            textView= itemView.findViewById(R.id.textViewUsers);
            imageView=itemView.findViewById(R.id.imgUser);
            cardView=itemView.findViewById(R.id.cardView);
            add=itemView.findViewById(R.id.add);
            send=itemView.findViewById(R.id.send);
            request=itemView.findViewById(R.id.request);
            friends=itemView.findViewById(R.id.friend);
            progressBar=itemView.findViewById(R.id.progressBar);
        }
    }
}



