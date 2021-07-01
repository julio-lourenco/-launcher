package com.blueoceansolutions.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openApp(View view){
        Intent intent;
        new Intent(Intent.ACTION_MAIN);
        PackageManager managerClock = getPackageManager();
        intent = managerClock.getLaunchIntentForPackage("com.google.android.gm");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

    public void openWhatsApp(View view){
        Intent intent;
        new Intent(Intent.ACTION_MAIN);
        PackageManager managerClock = getPackageManager();
        intent = managerClock.getLaunchIntentForPackage("com.whatsapp");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

}