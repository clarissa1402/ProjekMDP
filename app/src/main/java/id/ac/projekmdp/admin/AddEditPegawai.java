package id.ac.projekmdp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import id.ac.projekmdp.R;
import id.ac.projekmdp.databinding.ActivityAddEditPegawaiBinding;

public class AddEditPegawai extends AppCompatActivity {

    ActivityAddEditPegawaiBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pegawai);
        binding = ActivityAddEditPegawaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSavePegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik = binding.etnik.getText().toString();
                String nama = binding.etNamaPegawai.getText().toString();
                String email = binding.etEmailPegawai.getText().toString();
                String alamat = binding.etAlamat.getText().toString();
                String telp = binding.etTelp.getText().toString();
                String password = binding.etPasswordPegawai.getText().toString();
                String confirm = binding.etConfirmPassPegawai.getText().toString();
                String jenis = binding.spinner.toString();

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
                }if(TextUtils.isEmpty(password)){
                    input(binding.etPasswordPegawai,"Password");
                }if(TextUtils.isEmpty(confirm)){
                    input(binding.etConfirmPassPegawai,"Confirm Password");
                }if(!password.equals(confirm)){
                    binding.etConfirmPassPegawai.setError("The Password Confirmation Doesn't Match!");
                    binding.etConfirmPassPegawai.requestFocus();
                    binding.etPasswordPegawai.setError("The Password Confirmation Doesn't Match!");
                    binding.etPasswordPegawai.requestFocus();
                }
            }
        });

        binding.btnBackPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddEditPegawai.this,HomeAdmin.class);
                startActivity(i);
            }
        });
    }
    private void input(EditText txt, String s){
        txt.setError("Field "+s+" Tidak boleh kosong!");
        txt.requestFocus();
    }
}