package id.ac.projekmdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.databinding.ActivityMainBinding;
import id.ac.projekmdp.databinding.ActivityRegisterBinding;
import id.ac.projekmdp.kelas.User;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    Integer ban=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //binding.getRoot() -> mengembalikan View
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference root = db.getReference();

                FirebaseAuth fAuth = null;

                String nama = binding.etNamaRegis.getText().toString();
                String email = binding.etEmailRegis.getText().toString();
                String alamat = binding.etAlamatRegis.getText().toString();
                String password = binding.etPassRegis.getText().toString();
                String confirm = binding.etConfirmRegis.getText().toString();
                String telp = binding.etTelpRegis.getText().toString();

                if(TextUtils.isEmpty(nama) ){
                    input(binding.etNamaRegis,"Nama");
                }if(TextUtils.isEmpty(email)){
                    input(binding.etEmailRegis,"Email");
                }if(TextUtils.isEmpty(alamat)){
                    input(binding.etAlamatRegis,"Alamat");
                }if(TextUtils.isEmpty(telp)){
                    input(binding.etTelpRegis,"Nomor Telepon");
                }if(TextUtils.isEmpty(password)){
                    input(binding.etPassRegis,"Password");
                }if(TextUtils.isEmpty(confirm)){
                    input(binding.etConfirmRegis,"Confirm Password");
                }if(!password.equals(confirm)){
                    binding.etConfirmRegis.setError("The Password Confirmation Doesn't Match!");
                    binding.etConfirmRegis.requestFocus();
                    binding.etPassRegis.setError("The Password Confirmation Doesn't Match!");
                    binding.etPassRegis.requestFocus();
                }
                else if(nama.length()>0 && email.length()>0 && alamat.length()>0 && telp.length()>0 && password.length()>0 && confirm.length()>0 && password.equals(confirm)){

                    //fAuth.createUserWithEmailAndPassword(email,password);

                    root.child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                ban++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    root.child("Users").push().setValue(new User(ban,email,nama,telp,alamat,password)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getBaseContext(),"Registered",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"Not Registered",Toast.LENGTH_SHORT).show();
                        }
                    });
                    //dapetkan key
//                    root.child("Users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot childSnapshot: snapshot.getChildren()) {
//                                String key = childSnapshot.getKey();
//                                System.out.println(key);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                    reset();
                }

            }
        });
        binding.btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void input(EditText txt, String s){
        txt.setError("Field "+s+" Tidak boleh kosong!");
        txt.requestFocus();
    }
    private void reset(){
        binding.etPassRegis.setText("");
        binding.etAlamatRegis.setText("");
        binding.etConfirmRegis.setText("");
        binding.etEmailRegis.setText("");
        binding.etNamaRegis.setText("");
        binding.etTelpRegis.setText("");
    }

}