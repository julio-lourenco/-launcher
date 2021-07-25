package com.blueoceansolutions.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ValidaTelaApps extends AppCompatActivity {

    private String senha = getHashMd5("1234"); // senha de teste haha
    private EditText editSenha;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valida_tela_apps);

        //editSenha = findViewById(R.id.editSenha);
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

}
