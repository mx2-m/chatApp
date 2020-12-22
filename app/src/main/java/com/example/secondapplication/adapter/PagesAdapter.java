package com.example.secondapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.secondapplication.fragments.ChatsFragment;
import com.example.secondapplication.fragments.MyRequestsFragment;
import com.example.secondapplication.fragments.RequestsFragment;
import com.example.secondapplication.fragments.UsersFragment;

public class PagesAdapter extends FragmentStateAdapter {


    public PagesAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UsersFragment();
            case 1:
                return new ChatsFragment();
            case 2:
                return new RequestsFragment();
            case 3:
                return new MyRequestsFragment();
            default:
                return new UsersFragment();

        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
