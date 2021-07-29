package com.blueoceansolutions.launcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txtBateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //listar();

        txtBateria = findViewById(R.id.txtBateria);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
            batteryLevel(txtBateria);
        }
    }

    public void abrirApp(View view, String packageNameToOpen, String label){
        try{
            Intent intent;
            new Intent(Intent.ACTION_MAIN);
            PackageManager managerClock = getPackageManager();
            intent = managerClock.getLaunchIntentForPackage(packageNameToOpen);
            if(intent == null){
                String nomePacote = getPackageNameByLabel(label);
                if(!nomePacote.equals(null)) {
                    intent = managerClock.getLaunchIntentForPackage(nomePacote);
                }
            }
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            startActivity(intent);
        }catch(Exception E){
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Erro")
                    .setMessage("Erro ao tentar abrir aplicativo, verifique se ele está instalado no POS!")
                    .setPositiveButton(android.R.string.yes, null)
                    .show();
        }
    }

    public void abrirEmprestimo(View view){ abrirApp(view,"com.blueoceansolutions.gerenciadordeemprestimo", "solicitar empréstimos"); }
    public void abrirGestao(View view){ abrirApp(view,"br.com.ellotech", "pdv pos pay"); }
    public void abrirRealCap(View view){ abrirApp(view,"br.net.realcap","real cap"); }
    public void abrirPagamento(View view){ abrirApp(view,"br.com.celerpay.tkpppos","pagamento"); }


    public void abrirValidaApps(View view) {
        Intent intent = new Intent(this, ValidaTelaApps.class);
        startActivity(intent);
    }

   /*
        NO longer necessary
        private Drawable getIcon(String packageName){
        final PackageManager pm = getPackageManager();
        Drawable icon = null;
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if(packageName.equals(packageInfo.packageName)){
                icon = packageInfo.loadIcon(pm);
            }
        }
        return icon;
    }*/

    private String getPackageNameByLabel(String label){
        final PackageManager pm = getPackageManager();
        String packageName = null;
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if(label.toString().toLowerCase().equals(packageInfo.loadLabel(pm).toString().toLowerCase())){
                packageName = packageInfo.packageName;
            }
        }

        return packageName;
    }

   /* private void listar(){
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.d(ContentValues.TAG, packageInfo.loadLabel(pm)+":" + packageInfo.packageName);
        }
    }*/

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void batteryLevel(TextView batterLevel) {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                batterLevel.setText(level + "%");
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }

}

