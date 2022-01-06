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

public class User_page extends AppCompatActivity {
    private ActivityUserPageBinding binding;
    BottomNavigationView navUser;
    int id=-1;
    DatabaseReference root;
    public ArrayList<User>datauser=new ArrayList<>();
    public ArrayList<Pegawai>datapegawai=new ArrayList<>();
    public ArrayList<Transaksi>dataTransaksi=new ArrayList<>();
    User sedang_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root= FirebaseDatabase.getInstance().getReference();
        load_data();
        id=getIntent().getIntExtra("id",-1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        binding = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadPegawai();
        loadTransaksi();

        navUser = findViewById(R.id.navigation_user);
        navUser.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.menuhome:
                        fragment = Fragment_home_user.newInstance(User_page.this, datauser, datapegawai);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        return true;
                    case R.id.menutopup:
                        fragment = Fragment_topup_user.newInstance(User_page.this, sedang_login, dataTransaksi);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        return true;
                    case R.id.menutransaksi:
                        fragment = Fragment_transaksi.newInstance(User_page.this, dataTransaksi, datapegawai);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        return true;
                    case R.id.menuprofile:
                        fragment = Fragment_profile_user.newInstance(User_page.this, datauser);
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
                            String.valueOf(dataSnapshot.child("password").getValue()),
                            String.valueOf(dataSnapshot.child("jenis_kelamin").getValue()),
                            String.valueOf(dataSnapshot.child("url").getValue()),
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
    public void set_sedang_login(){
        for (int i = 0; i < datauser.size(); i++) {
            if(datauser.get(i).getId()==id){
                sedang_login=datauser.get(i);
            }
        }
    }

    public void gotohome(){
        navUser.setSelectedItemId(R.id.menuhome);
    }

    public void gototransaksi(){
        navUser.setSelectedItemId(R.id.menutransaksi);
    }

    public void gotobooking(int pid){
        Fragment fragment = Fragment_booking.newInstance(User_page.this, pid);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
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
        getMenuInflater().inflate(R.menu.optionmenu_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.keluar){
            Intent logout = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(logout);
        }
        return super.onOptionsItemSelected(item);
    }

    public void call(String telp){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + telp));
        startActivity(callIntent);
    }
    public void chat(int tujuan){
        Intent chatIntent = new Intent(User_page.this, ChatUser.class);
        chatIntent.putExtra("nik_peg",tujuan);
        chatIntent.putExtra("id_user",id);
        chatIntent.putExtra("dari",1);
        //chatIntent.putExtra("sedang_login",sedang_login);
        chatIntent.putParcelableArrayListExtra("datauser",datauser);
        startActivity(chatIntent);
    }
}