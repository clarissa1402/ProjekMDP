package id.ac.projekmdp;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.admin.HomeAdmin;
import id.ac.projekmdp.databinding.ActivityMainBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.User;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
//    FirebaseDatabase db = FirebaseDatabase.getInstance();
//    DatabaseReference root = db.getReference();
    ArrayList<User> datauser=new ArrayList<>();
    ArrayList<Pegawai> datapegawai=new ArrayList<>();
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        root=FirebaseDatabase.getInstance().getReference();
        load_data();
        //root.child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//        root.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()){
////                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()){
////                        User user=dataSnapshot.getValue(User.class);
////                        datauser.add(user);
////                    }
//                    DataSnapshot ds=task.getResult();
//                    datauser=new ArrayList<>();
//                    datauser.add(new User(String.valueOf(ds.child("email").getValue()),String.valueOf(ds.child("nama").getValue()),String.valueOf(ds.child("telepon").getValue()),String.valueOf(ds.child("alamat").getValue()),String.valueOf(ds.child("password").getValue())));
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etEmail.getText().toString();
                String pass = binding.etPassword.getText().toString();

                if(email.equals("admin@gmail.com") && pass.equals("admin")){
                    Intent i = new Intent(getBaseContext(), HomeAdmin.class);
                    startActivity(i);
                }
                else{
                    String tipe="";
                    String temp_email="";
                    for (int i = 0; i < datauser.size(); i++) {
                        if (datauser.get(i).getEmail().equals(email)&&datauser.get(i).getPassword().equals(pass)){
                            tipe="user";
                            temp_email=datauser.get(i).getEmail();
                        }
                    }
                    if(tipe==""){
                        for (int i = 0; i < datapegawai.size(); i++) {
                            if (datapegawai.get(i).getEmail().equals(email)&&datapegawai.get(i).getPassword().equals(pass)){
                                tipe="pegawai";
                                temp_email=datapegawai.get(i).getEmail();
                            }
                        }
                    }
                    if(tipe.equals("user")){
                        //Toast.makeText(MainActivity.this, "Berhasil login", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(MainActivity.this,User_page.class);
                        i.putExtra("email",temp_email);
                        startActivity(i);
                    }
                    else if(tipe.equals("pegawai")){

                    }

                    //root.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//        root.child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()){
//                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()){
//                        Toast.makeText(MainActivity.this, String.valueOf(dataSnapshot.child("email").getValue()), Toast.LENGTH_SHORT).show();
////                        User user=dataSnapshot.getValue(User.class);
////                        datauser.add(user);
//                    }
////                      DataSnapshot ds=task.getResult();
////                      Toast.makeText(MainActivity.this, String.valueOf(ds.child("email").getValue()), Toast.LENGTH_SHORT).show();
////                    DataSnapshot ds=task.getResult();
////                    datauser=new ArrayList<>();
//                    //datauser.add(new User(String.valueOf(ds.child("email").getValue()),String.valueOf(ds.child("nama").getValue()),String.valueOf(ds.child("telepon").getValue()),String.valueOf(ds.child("alamat").getValue()),String.valueOf(ds.child("password").getValue())));
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });











                }
                //root.setValue(email);
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
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
        root.child("Pegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datapegawai.add(new Pegawai(
                            String.valueOf(dataSnapshot.child("nik").getValue()),
                            String.valueOf(dataSnapshot.child("email").getValue()),
                            String.valueOf(dataSnapshot.child("nama").getValue()),
                            String.valueOf(dataSnapshot.child("telepon").getValue()),
                            String.valueOf(dataSnapshot.child("alamat").getValue()),
                            String.valueOf(dataSnapshot.child("password").getValue()),
                            String.valueOf(dataSnapshot.child("jasa").getValue()),
                            String.valueOf(dataSnapshot.child("deskripsi").getValue())
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        root.child("Pegawai").push().setValue(new Pegawai("46718236487628734", "pegawai@a.com", "pegawai", "412364612374682", "ngagel_jaya", "123", "Cleaning", "deskripsi")).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(getBaseContext(),"Registered",Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getBaseContext(),"Not Registered",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

