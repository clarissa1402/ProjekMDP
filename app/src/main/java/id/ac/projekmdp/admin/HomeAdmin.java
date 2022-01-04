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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.MainActivity;
import id.ac.projekmdp.R;
import id.ac.projekmdp.databinding.ActivityHomeAdminBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;

public class HomeAdmin extends AppCompatActivity {

    ActivityHomeAdminBinding binding;
    DatabaseReference root;
    ArrayList<Pegawai> datapegawai = new ArrayList<>();
    ArrayList<Transaksi> dataTransaksi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadPegawai();
        loadTransaksi();

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
                        Fragment fragment = MasterFragment.newInstance(menu,datapegawai);
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

    public void loadPegawai(){
        datapegawai = new ArrayList<>();
        root.child("Pegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datapegawai.add(new Pegawai(
                            Integer.parseInt(String.valueOf(dataSnapshot.child("nik").getValue())),
                            String.valueOf(dataSnapshot.child("email").getValue()),
                            String.valueOf(dataSnapshot.child("nama").getValue()),
                            String.valueOf(dataSnapshot.child("telepon").getValue()),
                            String.valueOf(dataSnapshot.child("alamat").getValue()),
                            String.valueOf(dataSnapshot.child("password").getValue()),
                            String.valueOf(dataSnapshot.child("jasa").getValue()),
                            String.valueOf(dataSnapshot.child("deskripsi").getValue()),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("harga").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("saldo").getValue()))
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadTransaksi(){
        dataTransaksi = new ArrayList<>();
        root.child("Transaksi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataTransaksi.add(new Transaksi(
                            Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("idUser").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("nikPegawai").getValue())),
                            String.valueOf(dataSnapshot.child("tanggal").getValue()),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("harga").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("status").getValue()))
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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