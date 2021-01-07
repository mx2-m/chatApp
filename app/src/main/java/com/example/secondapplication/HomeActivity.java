package com.example.secondapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lib.State;
import com.example.lib.User;
import com.example.secondapplication.adapter.PagesAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//import com.example.lib.User;


//POPRAVITI SVE ZA "MOJ PROFIL", dodati login na menu, vibracija, notifikacije,pref popraviti
public class HomeActivity extends AppCompatActivity {


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenceUser = database.getReference("Users").child(user.getUid());
    //DatabaseReference referenceRequests = database.getReference("Users").child(user.getUid()).child("requests");
    DatabaseReference referenceCount = database.getReference("Count").child(user.getUid());
    DatabaseReference referenceState = database.getReference("State").child(user.getUid());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      //  ActionBar actionBar = getSupportActionBar();
      //  actionBar.setTitle("ChatApp");

        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PagesAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Users");
                        tab.setIcon(R.drawable.ic_users);
                        break;
                    case 1:
                        tab.setText("Chats");
                        tab.setIcon(R.drawable.ic_chats);
                        break;
                    case 2:
                        tab.setText("Requests");
                        tab.setIcon(R.drawable.ic_requests);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));


                        referenceCount.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Integer value = snapshot.getValue(Integer.class);
                                    badgeDrawable.setVisible(true);
                                    if (value.equals("0")) {
                                        badgeDrawable.setVisible(true);

                                    } else {
                                        badgeDrawable.setNumber(value);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        break;
                    case 3:
                        tab.setText("My Profile");
                        tab.setIcon(R.drawable.ic_requests);
                        break;


                }


            }
        });

        tabLayoutMediator.attach();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);

                if (position == 2) {

                    count();
                }

            }
        });

        // final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        userUnique();

    }

    private void userState(String state) {
        referenceState.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                State state1=new State(state," "," "," ");
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
              /*  referenceUser.child("date").setValue(date.format(calendar.getTime()));
                referenceUser.child("time").setValue(time.format(calendar.getTime()));*/
                referenceState.child("date").setValue(date.format(calendar.getTime()));
                referenceState.child("time").setValue(time.format(calendar.getTime()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void count() {
        referenceCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    referenceCount.setValue(0);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void userUnique() {

        referenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {

                    User user1 = new User(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString(),
                            "disconnected", "10/12/2020", "23:43", 0, 0);

                    referenceUser.setValue(user1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this.getBaseContext(), Settings.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
