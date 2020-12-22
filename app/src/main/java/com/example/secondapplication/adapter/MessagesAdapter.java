package com.example.secondapplication.adapter;

import android.content.Context;
import android.os.Vibrator;
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
import com.example.lib.Message;
import com.example.lib.User;
import com.example.secondapplication.ApplicationChat;
import com.example.secondapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.viewHolderAdapterMessages> {

    List<User> list;
    Context context;
    ApplicationChat app;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public MessagesAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolderAdapterMessages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages, parent, false);
        viewHolderAdapterMessages holder = new viewHolderAdapterMessages(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.viewHolderAdapterMessages holder, int position) {
        User users = list.get(position);

        // boolean vibration = app.isVibration();

        final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        holder.textViewUser.setText(users.getName());
        Glide.with(context).load(users.getId()).into(holder.imageView);

        DatabaseReference requests = database.getReference("Requests").child(user.getUid());
        requests.child(users.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state = snapshot.child("state").getValue(String.class);

                if (snapshot.exists()) {
                    if (state.equals("friends")) {
                        holder.cardView.setVisibility(View.VISIBLE);
                    } else {
                        holder.cardView.setVisibility(View.GONE);
                    }
                } else {
                    holder.cardView.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");

        DatabaseReference ref_state = database.getReference("State").child(users.getState());
        ref_state.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state = snapshot.child("state").getValue(String.class);
                String time = snapshot.child("time").getValue(String.class);
                String date = snapshot.child("date").getValue(String.class);

                if (snapshot.exists()) {

                    if (state.equals("online")) {
                        holder.tv_online.setVisibility(View.VISIBLE);
                        holder.imageView.setVisibility(View.VISIBLE);
                        holder.offline.setVisibility(View.GONE);
                        holder.tv_online.setVisibility(View.GONE);


                    } else {
                        holder.tv_online.setVisibility(View.GONE);
                        holder.imageView.setVisibility(View.GONE);
                        holder.offline.setVisibility(View.VISIBLE);
                        holder.tv_online.setVisibility(View.VISIBLE);

                    }

                    if (date.equals(date1.format(calendar.getTime()))) {
                        holder.tv_offline.setText(time);
                    } else {
                        holder.tv_offline.setText(time + date);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolderAdapterMessages extends RecyclerView.ViewHolder {

        TextView textViewUser;
        ImageView imageView;
        CardView cardView;
        TextView tv_online, tv_offline;
        ImageView online, offline;


        public viewHolderAdapterMessages(@NonNull View itemView) {
            super(itemView);

            textViewUser = itemView.findViewById(R.id.textViewUsers);
            imageView = itemView.findViewById(R.id.imgUser);
            cardView = itemView.findViewById(R.id.cardView);
            tv_online = itemView.findViewById(R.id.tv_online);
            tv_offline = itemView.findViewById(R.id.tv_offline);
            online = itemView.findViewById(R.id.online);
            offline = itemView.findViewById(R.id.offline);

        }
    }
}



