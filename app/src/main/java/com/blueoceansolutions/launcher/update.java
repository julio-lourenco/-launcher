package com.blueoceansolutions.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.pax.dal.IDAL;
import com.pax.neptunelite.api.NeptuneLiteUser;

public class update extends AppCompatActivity {
    private DownloadManager dm = null;
    private long lastDownload = -1L;
    private static IDAL dal;
    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        appContext = getApplicationContext();
        registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        fazerDownload();

    }

    private void fazerDownload(){
        Uri uri=Uri.parse("https://flashpdv.com.br/atualizacaoapk/app-release-realpay.apk");

        Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();

        lastDownload = dm.enqueue(new DownloadManager.Request(uri)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                "app-release-realpay.apk"));
    }


    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            //Instalar o Aplicativo;
            try{
                dal = NeptuneLiteUser.getInstance().getDal(appContext);
                dal.getSys().installApp(Environment.DIRECTORY_DOWNLOADS+"app-release-realpay.apk");

            }catch(Exception E){
                Toast.makeText(appContext,"Erro ao atualizar o aplicativo.", Toast.LENGTH_LONG).show();
            }


        }
    };
}