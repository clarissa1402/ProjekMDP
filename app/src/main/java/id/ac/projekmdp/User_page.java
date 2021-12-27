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

import id.ac.projekmdp.databinding.ActivityMainBinding;
import id.ac.projekmdp.databinding.ActivityUserPageBinding;

public class User_page extends AppCompatActivity {
    private ActivityUserPageBinding binding;
    BottomNavigationView navUser;
    String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        binding = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email=getIntent().getStringExtra("email");
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
//                    case R.id.menuprofile:
//                        fragment = Fragment_profile.newInstance(User_page.this);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.framelayout, fragment)
//                                .commit();
//                        return true;
                }
                return false;
            }
        });
        if (savedInstanceState == null) {
            navUser.setSelectedItemId(R.id.menuhome);
        }
    }
}