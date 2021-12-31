package com.hayatsoftwares.hyt.music.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.hayatsoftwares.hyt.music.player.Util.Constants;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    private TextView permission;


    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Constants.PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            permission.setText("Permission for Reading Data is Available");
            Toast.makeText(this, "Now you are set to Go Ahead !!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this , MainActivity.class));
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        permission = findViewById(R.id.permission);
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            permission.setText("Permission for Reading Data is Available");
            startActivity(new Intent(SplashActivity.this , MainActivity.class));
        }else{
            Toast.makeText(this, "Permission Not Available !!!", Toast.LENGTH_SHORT).show();
            getPermission();
        }


    }
    private void getPermission(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION_CODE);
        }else{
            Toast.makeText(this, "Permission is Already Granted by Android OS", Toast.LENGTH_SHORT).show();
        }
    }
}