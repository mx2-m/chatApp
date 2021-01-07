package com.example.secondapplication.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib.Messages;
import com.example.secondapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.viewHolderAdapter> {


    List<Messages>list;
    Context context;
    public static final int MESSAGES_L=0;
    public static final int MESSAGES_R=1;
    Boolean right=false;
    FirebaseUser user;

    public ChatsAdapter(List<Messages> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ChatsAdapter.viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType==MESSAGES_R){
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);

        }
        return new viewHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.viewHolderAdapter holder, int position) {
        Messages chats=list.get(position);
        holder.text.setText(chats.getMessage());

        if(right){
            if(chats.getViewed().equals("yes")){
                holder.seen.setVisibility(View.VISIBLE);
                holder.notSeen.setVisibility(View.GONE);
            }
            else {
                holder.seen.setVisibility(View.GONE);
                holder.notSeen.setVisibility(View.VISIBLE);

            }

            Calendar calendar=Calendar.getInstance();
            final SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");

            if(chats.getDate().equals(format.format(calendar.getTime()))){
                holder.time.setText(chats.getTime());
            }else{
                holder.time.setText(chats.getDate()+" "+chats.getTime());
            }

        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolderAdapter extends RecyclerView.ViewHolder {


        TextView text,time;
        ImageView seen,notSeen;


        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
            time=itemView.findViewById(R.id.time);
            seen=itemView.findViewById(R.id.seen);
            notSeen=itemView.findViewById(R.id.n_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(list.get(position).getSender().equals(user.getUid())){
             right=true;
             return MESSAGES_R;
        }
        else{
            right=false;
            return MESSAGES_L;
        }


    }
}