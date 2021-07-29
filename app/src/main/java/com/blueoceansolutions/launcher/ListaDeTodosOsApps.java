package com.blueoceansolutions.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaDeTodosOsApps extends AppCompatActivity {

    ListView listView;

    ItemAdapter itemAdapter;

    List<Item> apps;

    TextView txtBateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_todos_os_apps);

        apps = new ArrayList<>();
        getApps();

        itemAdapter = new ItemAdapter(ListaDeTodosOsApps.this, R.layout.item, apps);

        listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openApp(apps.get(position).getPack());
            }
        });

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

/*
  private void getApps(){
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for(ApplicationInfo packageInfo : packages){
            apps.add(new Item(pm.getApplicationLabel(packageInfo).toString(),
                    packageInfo.packageName,
                    pm.getApplicationIcon(packageInfo)));
        }
    }

 */

    private void getApps() {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(0);

        for (ApplicationInfo packageInfo : packages) {

            if((packageInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                apps.add(new Item(pm.getApplicationLabel(packageInfo).toString(),
                        packageInfo.packageName,
                        pm.getApplicationIcon(packageInfo)));

            } else if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            } else {
                apps.add(new Item(pm.getApplicationLabel(packageInfo).toString(),
                        packageInfo.packageName,
                        pm.getApplicationIcon(packageInfo)));
            }
        }
    }

    private void openApp(String packageName){
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if(intent != null){
            startActivity(intent);
        }
    }

    public void abrirApp(View view, String packageNameToOpen){
        try{
            Intent intent;
            new Intent(Intent.ACTION_MAIN);
            PackageManager managerClock = getPackageManager();
            intent = managerClock.getLaunchIntentForPackage(packageNameToOpen);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            startActivity(intent);
        }catch(Exception E){
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Erro")
                    .setMessage("Erro ao tentar abrir aplicativo, verifique se ele está instalado no POS!")
                    .setPositiveButton(android.R.string.yes, null )
                    .show();
        }
    }

    public void abrirConfig(View view){abrirApp(view,"com.android.settings");
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
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
