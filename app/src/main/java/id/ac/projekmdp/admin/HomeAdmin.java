package id.ac.projekmdp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        binding.btnGoToMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeAdmin.this, MasterAdmin.class);
                startActivity(i);
            }
        });
    }
}