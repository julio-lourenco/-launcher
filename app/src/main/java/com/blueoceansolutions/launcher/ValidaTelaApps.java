package com.blueoceansolutions.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ValidaTelaApps extends AppCompatActivity {

    private static final String senha = "1234"; // senha de teste haha
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

        System.out.println("Senha do Edit: " + editSenha.getText().toString());
        System.out.println("Senha do app: "  + senha);

        if(editSenha.getText().toString().equals(senha)) {
            Intent intent = new Intent(this, ListaDeTodosOsApps.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_LONG).show();
        }
    }

}
