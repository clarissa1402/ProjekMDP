package id.ac.projekmdp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import id.ac.projekmdp.R;
import id.ac.projekmdp.databinding.ActivityAddEditVoucherBinding;

public class AddEditVoucher extends AppCompatActivity {

    ActivityAddEditVoucherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_voucher);
        binding = ActivityAddEditVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSaveVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = binding.etNamaVoucher.getText().toString();
                String potongan = binding.etPotonganVoucher.getText().toString();
                String harga = binding.etHargaVoucher.getText().toString();

                if(TextUtils.isEmpty(nama)){
                    input(binding.etNamaVoucher,"Nama");
                }
                if(TextUtils.isEmpty(potongan)){
                    input(binding.etPotonganVoucher,"Potongan");
                }
                if(TextUtils.isEmpty(harga)){
                    input(binding.etHargaVoucher,"Harga");
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddEditVoucher.this, MasterAdmin.class);
                startActivity(i);
            }
        });
    }
    private void input(EditText txt, String s){
        txt.setError("Field "+s+" Tidak boleh kosong!");
        txt.requestFocus();
    }
}