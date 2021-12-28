package id.ac.projekmdp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.databinding.ActivityMainBinding;
import id.ac.projekmdp.databinding.ActivityUserPageBinding;
import id.ac.projekmdp.kelas.User;

public class User_page extends AppCompatActivity {
    private ActivityUserPageBinding binding;
    BottomNavigationView navUser;
    int id=0;
    DatabaseReference root;
    ArrayList<User>datauser=new ArrayList<>();
    User sedang_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root= FirebaseDatabase.getInstance().getReference();
        load_data();
        id=getIntent().getIntExtra("id",0);
        set_sedang_login();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        binding = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navUser = findViewById(R.id.navigation_user);
        navUser.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.menuhome:
                        fragment = Fragment_home_user.newInstance(User_page.this);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        return true;
//                    case R.id.menutopup:
//                        fragment = Fragment_top_up.newInstance(User_page.this);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, fragment)
//                                .commit();
//                        return true;
//                    case R.id.menutransaksi:
//                        fragment = Fragment_transaksi.newInstance(User_page.this);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, fragment)
//                                .commit();
//                        return true;
                    case R.id.menuprofile:
                        fragment = Fragment_profile_user.newInstance(User_page.this);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        return true;
                }
                return false;
            }
        });
        if (savedInstanceState == null) {
            navUser.setSelectedItemId(R.id.menuhome);
        }
    }
    public void load_data(){
        root.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datauser.add(new User(
                            Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
                            String.valueOf(dataSnapshot.child("email").getValue()),
                            String.valueOf(dataSnapshot.child("nama").getValue()),
                            String.valueOf(dataSnapshot.child("telepon").getValue()),
                            String.valueOf(dataSnapshot.child("alamat").getValue()),
                            String.valueOf(dataSnapshot.child("password").getValue())));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void set_sedang_login(){
        for (int i = 0; i < datauser.size(); i++) {
            if(datauser.get(i).getId()==id){
                sedang_login=datauser.get(i);
            }
        }
    }

    public void gototransaksi(){
        Fragment fragment = Fragment_home_user.newInstance(User_page.this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void gotobooking(int pid){
        Fragment fragment = Fragment_booking.newInstance(User_page.this, pid);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}