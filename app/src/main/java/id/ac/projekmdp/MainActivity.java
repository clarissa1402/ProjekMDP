package id.ac.projekmdp;

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

import id.ac.projekmdp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //binding.getRoot() -> mengembalikan View
    }
    void btnklik(View v){
        if(v.getId() == binding.button2.getId()){
            Intent i = new Intent(getBaseContext(), RegisterActivity.class);
            startActivity(i);
        }
    }
}

