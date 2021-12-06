package id.ac.projekmdp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import id.ac.projekmdp.R;
import id.ac.projekmdp.databinding.ActivityMasterAdminBinding;

public class MasterAdmin extends AppCompatActivity {

    ActivityMasterAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_admin);
        binding = ActivityMasterAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenuadmin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addPegawai){
            Intent i = new Intent(MasterAdmin.this, AddEditPegawai.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.addVoucher){
            Intent i = new Intent(MasterAdmin.this, AddEditVoucher.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}