package com.blueoceansolutions.launcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listar();
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

    public void abrirEmprestimo(View view){ abrirApp(view,"com.blueoceansolutions.gerenciadordeemprestimo"); }
    public void abrirGestao(View view){ abrirApp(view,"br.com.ellotech"); }
    public void abrirRealCap(View view){
        abrirApp(view,"br.net.realcap");
    }
    public void abrirPagamento(View view){
        abrirApp(view,"br.com.celerpay.tkpppos");
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

    private void listar(){
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.d(ContentValues.TAG, packageInfo.loadLabel(pm)+":" + packageInfo.packageName);
        }
    }

}