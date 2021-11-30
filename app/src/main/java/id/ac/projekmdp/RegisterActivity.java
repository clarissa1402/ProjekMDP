package id.ac.projekmdp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import id.ac.projekmdp.databinding.ActivityMainBinding;
import id.ac.projekmdp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //binding.getRoot() -> mengembalikan View
    }

    void btnklik(View v){
        if(v.getId() == binding.button4.getId()){
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);
        }
    }
}