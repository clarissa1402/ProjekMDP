package id.ac.projekmdp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import id.ac.projekmdp.databinding.ActivityStartPageBinding;

public class Start_page extends AppCompatActivity {

    ActivityStartPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        binding = ActivityStartPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" ));
        binding.videoView.start();
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 2 seconds
                } catch (Exception e) {

                } finally {
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}