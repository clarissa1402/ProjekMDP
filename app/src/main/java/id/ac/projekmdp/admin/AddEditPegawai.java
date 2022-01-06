package id.ac.projekmdp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.R;
import id.ac.projekmdp.databinding.ActivityAddEditPegawaiBinding;
import id.ac.projekmdp.kelas.Pegawai;

public class AddEditPegawai extends AppCompatActivity {

    ActivityAddEditPegawaiBinding binding;
    Boolean isEdit;
    DatabaseReference root;
    Pegawai pegawai;
    ArrayList<Pegawai> listPegawai = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root= FirebaseDatabase.getInstance().getReference();
        binding = ActivityAddEditPegawaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isEdit = false;
        if(getIntent().hasExtra("detail")){
            isEdit = true;
            try {
                pegawai = getIntent().getParcelableExtra("detail");
                binding.etnik.setText(pegawai.getNik()+"");
                binding.etNamaPegawai.setText(pegawai.getNama());
                binding.etPasswordPegawai.setEnabled(false);
                binding.etConfirmPassPegawai.setEnabled(false);
                binding.etEmailPegawai.setText(pegawai.getEmail());
                binding.etAlamat.setText(pegawai.getAlamat());
                binding.etTelp.setText(pegawai.getTelepon());
                int selectedIndex = getSpinnerValuePosition(binding.spinner,pegawai.getJasa());
                binding.spinner.setSelection(selectedIndex);
                binding.etDeskripsi.setText(pegawai.getDeskripsi());
                binding.etHargaPegawai.setText(pegawai.getHarga()+"");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
        binding.btnSavePegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUpdate();
            }
        });

        binding.btnBackPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddEditPegawai.this,HomeAdmin.class);
                startActivity(i);
            }
        });
        if (isEdit){
            makeToast("edit mode");;
        }
        else{
            makeToast("add mode");
        }
    }
    void makeToast(String pesan){
        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
    }
    void addUpdate(){
        String nik = binding.etnik.getText().toString();
        String nama = binding.etNamaPegawai.getText().toString();
        String email = binding.etEmailPegawai.getText().toString();
        String alamat = binding.etAlamat.getText().toString();
        String telp = binding.etTelp.getText().toString();
        String password = binding.etPasswordPegawai.getText().toString();
        String confirm = binding.etConfirmPassPegawai.getText().toString();
        String harga = binding.etHargaPegawai.getText().toString();
        String jenis = binding.spinner.getSelectedItem().toString();
        String deskripsi = binding.etDeskripsi.getText().toString();
        Integer hargaInt =0;
        if (harga.equals("")){
            hargaInt=50000;
        }
        else{
            hargaInt=Integer.parseInt(harga);
        }
        Integer nikInt = 0;
        if(nik.equals("")){
            nikInt = 0;
        }
        else {
            nikInt=Integer.parseInt(nik);
        }

        if(TextUtils.isEmpty(nik) ){
            input(binding.etnik,"NIK");
        }if(TextUtils.isEmpty(nama) ){
            input(binding.etNamaPegawai,"Nama");
        }if(TextUtils.isEmpty(email)){
            input(binding.etEmailPegawai,"Email");
        }if(TextUtils.isEmpty(alamat)){
            input(binding.etAlamat,"Alamat");
        }if(TextUtils.isEmpty(telp)){
            input(binding.etTelp,"Nomor Telepon");
        }if(TextUtils.isEmpty(deskripsi)){
            input(binding.etDeskripsi,"Deskripsi");
        }
        if(!isEdit){
            if(TextUtils.isEmpty(password)){
                input(binding.etPasswordPegawai,"Password");
            }if(TextUtils.isEmpty(confirm)){
                input(binding.etConfirmPassPegawai,"Confirm Password");
            }if(!password.equals(confirm)){
                binding.etConfirmPassPegawai.setError("The Password Confirmation Doesn't Match!");
                binding.etConfirmPassPegawai.requestFocus();
                binding.etPasswordPegawai.setError("The Password Confirmation Doesn't Match!");
                binding.etPasswordPegawai.requestFocus();
            }
            if(deskripsi.length()>0 && nama.length()>0 && email.length()>0 && alamat.length()>0 && telp.length()>0 && password.length()>0 && confirm.length()>0 && password.equals(confirm) && nik.length()>0){
                root.child("Pegawai").push().setValue(new Pegawai(nikInt,email,nama,telp,alamat,password,jenis,deskripsi,hargaInt,0)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            }
        }else {
            if (deskripsi.length()>0 && nama.length()>0 && email.length()>0 && alamat.length()>0 && telp.length()>0 && nik.length()>0){
                //Toast.makeText(getBaseContext(),"Masuk edit",Toast.LENGTH_SHORT).show();
                root.child("Pegawai").orderByChild("nik").equalTo(pegawai.getNik()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()){
                            String key = childSnapshot.getKey();
                            root.child("Pegawai").child(key).child("nik").setValue(nik);
                            root.child("Pegawai").child(key).child("email").setValue(email);
                            root.child("Pegawai").child(key).child("nama").setValue(nama);
                            root.child("Pegawai").child(key).child("telepon").setValue(telp);
                            root.child("Pegawai").child(key).child("alamat").setValue(alamat);
                            root.child("Pegawai").child(key).child("jasa").setValue(jenis);
                            root.child("Pegawai").child(key).child("deskripsi").setValue(deskripsi);
                            root.child("Pegawai").child(key).child("harga").setValue(harga);
                        }
                        Toast.makeText(getBaseContext(),"Edited",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        }

    }
    int getSpinnerValuePosition(Spinner spinner, String value){
        int position = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            String item = spinner.getItemAtPosition(i).toString();
            if (item.equalsIgnoreCase(value)){
                position = i;
                break;
            }
        }
        return position;
    }
    private void input(EditText txt, String s){
        txt.setError("Field "+s+" Tidak boleh kosong!");
        txt.requestFocus();
    }

}