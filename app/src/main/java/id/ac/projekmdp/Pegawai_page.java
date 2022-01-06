package id.ac.projekmdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.databinding.ActivityUserPageBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;
import id.ac.projekmdp.kelas.User;

public class Pegawai_page extends AppCompatActivity {
    private ActivityUserPageBinding binding;
    BottomNavigationView navPegawai;
    int nik = 0;
    DatabaseReference root;
    public ArrayList<User> datauser = new ArrayList<>();
    public ArrayList<Pegawai> datapegawai = new ArrayList<>();
    public ArrayList<Transaksi> dataTransaksi = new ArrayList<>();
    Pegawai sedang_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_page);

        root= FirebaseDatabase.getInstance().getReference();
        nik = getIntent().getIntExtra("nik",0);

        loadUser();
        loadPegawai();
        loadTransaksi();


        navPegawai = findViewById(R.id.navigation_peg);
        navPegawai.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.menutransaksipeg:
                        fragment = Fragment_transaksi_pegawai.newInstance(Pegawai_page.this, dataTransaksi, datauser);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_peg, fragment)
                                .commit();
                        return true;
//                    case R.id.menuwithdrawpeg:
//                        fragment = Fragment_withdraw_pegawai.newInstance(Pegawai_page.this, dataTransaksi, datapegawai);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container_peg, fragment)
//                                .commit();
//                        return true;
//                    case R.id.menuprofilepeg:
//                        fragment = Fragment_profile_pegawai.newInstance(Pegawai_page.this, datauser);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container_peg, fragment)
//                                .commit();
//                        return true;
                }
                return false;
            }
        });
        if (savedInstanceState == null) {
            navPegawai.setSelectedItemId(R.id.menutransaksipeg);
        }
    }

    public void gototransaksi(){
        navPegawai.setSelectedItemId(R.id.menutransaksipeg);
    }

    public void set_sedang_login(){
        for (int i = 0; i < datapegawai.size(); i++) {
            if(datapegawai.get(i).getNik() == nik){
                sedang_login = datapegawai.get(i);
            }
        }
    }

    public void loadUser(){
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
                            String.valueOf(dataSnapshot.child("password").getValue()),
                            String.valueOf(dataSnapshot.child("jenis_kelamin").getValue()),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("saldo").getValue()))
                    ));
                }
                set_sedang_login();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
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
        dataTransaksi = new ArrayList<Transaksi>();
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

    public void call(String telp){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + telp));
        startActivity(callIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.keluar){
            Pegawai_page.this.finish();
//            Intent logout = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(logout);
        }
        return super.onOptionsItemSelected(item);
    }
    public void chat(int tujuan){
        Intent chatIntent = new Intent(Pegawai_page.this,ChatUser.class);
        chatIntent.putExtra("nik_peg",nik);
        chatIntent.putExtra("id_user",tujuan);
        chatIntent.putExtra("dari",2);
        startActivity(chatIntent);
    }
}