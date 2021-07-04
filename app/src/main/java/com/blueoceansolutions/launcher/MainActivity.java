package com.blueoceansolutions.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listar();
    }

    public void abrirEmprestimo(View view){
        Intent intent;
        new Intent(Intent.ACTION_MAIN);
        PackageManager managerClock = getPackageManager();
        intent = managerClock.getLaunchIntentForPackage("com.blueoceansolutions.gerenciadordeemprestimo");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

    public void abrirGestao(View view){
        Intent intent;
        new Intent(Intent.ACTION_MAIN);
        PackageManager managerClock = getPackageManager();
        intent = managerClock.getLaunchIntentForPackage("com.blueoceansolutions.gerenciadordeemprestimo");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

    public void abrirRealCap(View view){
        Intent intent;
        new Intent(Intent.ACTION_MAIN);
        PackageManager managerClock = getPackageManager();
        intent = managerClock.getLaunchIntentForPackage("br.com.realcapreal.realcapvendas");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }
    

    public void abrirPagamento(View view){
        Intent intent;
        new Intent(Intent.ACTION_MAIN);
        PackageManager managerClock = getPackageManager();
        intent = managerClock.getLaunchIntentForPackage("com.blueoceansolutions.gerenciadordeemprestimo");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

    private void listar(){
        final PackageManager pm = getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.d(ContentValues.TAG, packageInfo.loadLabel(pm)+":" + packageInfo.packageName);
            /*Log.d(ContentValues.TAG, "Source dir : " + packageInfo.sourceDir);
            Log.d(ContentValues.TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));*/
        }
    }

}