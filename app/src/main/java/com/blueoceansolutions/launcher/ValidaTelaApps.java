package com.blueoceansolutions.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ValidaTelaApps extends AppCompatActivity {

    private String senha = getHashMd5("real9876"); // senha de teste haha
    private EditText editSenha;
    private Button btnEntrar;

    TextView txtBateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valida_tela_apps);

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

    public void abrirApps(View view) {
        editSenha = findViewById(R.id.editSenha);
        String editSenhaMD5 = getHashMd5(editSenha.getText().toString());

        System.out.println("Senha do Edit: " + editSenha.getText().toString());
        System.out.println("Senha MD5 do Edit: " +  getHashMd5(editSenha.getText().toString()));

        System.out.println("Senha do app: "  + senha);

        if(editSenhaMD5.equals(senha)) {
            Intent intent = new Intent(this, ListaDeTodosOsApps.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_LONG).show();
        }
    }

    public static String getHashMd5(String value) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
        return hash.toString(16);
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
