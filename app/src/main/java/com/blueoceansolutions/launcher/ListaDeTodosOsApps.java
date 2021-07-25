package com.blueoceansolutions.launcher;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaDeTodosOsApps extends AppCompatActivity {

    ListView listView;

    ItemAdapter itemAdapter;

    List<Item> apps;

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
    }

    private void getApps(){
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for(ApplicationInfo packageInfo : packages){
            apps.add(new Item(pm.getApplicationLabel(packageInfo).toString(),
                    packageInfo.packageName,
                    pm.getApplicationIcon(packageInfo)));
        }
    }

    private void openApp(String packageName){
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if(intent != null){
            startActivity(intent);
        }
    }

}
