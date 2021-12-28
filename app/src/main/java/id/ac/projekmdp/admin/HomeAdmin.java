package id.ac.projekmdp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import id.ac.projekmdp.MainActivity;
import id.ac.projekmdp.R;
import id.ac.projekmdp.databinding.ActivityHomeAdminBinding;

public class HomeAdmin extends AppCompatActivity {

    ActivityHomeAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String menu;
                switch (item.getItemId()){
                    case R.id.listpegawai:
                        menu = "Pegawai";
                        break;
                    case R.id.listTransaksi:
                        menu = "Transaksi";
                        break;
                    default:
                        menu = "Pegawai";
                        break;
                }
                try {
                    if(menu.equals("Pegawai")){
                        Fragment fragment = MasterFragment.newInstance(menu);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framelayoutAdmin,fragment)
                                .commit();
                    }else {

                    }
                }catch (Exception e){
                    Log.e("HomeAdmin",e.getMessage());
                }
                return true;
            }
        });
        if (savedInstanceState == null) {
            binding.bottomnav.setSelectedItemId(R.id.listpegawai);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenuadmin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.addPegawai){
            Intent toAdd = new Intent(getApplicationContext(),AddEditPegawai.class);
            startActivity(toAdd);
        }
        if(item.getItemId()==R.id.opmLogout){
            Intent logout = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(logout);
        }
        return super.onOptionsItemSelected(item);
    }
}