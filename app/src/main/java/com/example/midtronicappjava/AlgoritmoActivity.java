package com.example.midtronicappjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AlgoritmoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.algoritmo);

        EditText etInput = findViewById(R.id.etInput);
        Button btnCompress = findViewById(R.id.btnCompress);
        TextView tvResult = findViewById(R.id.tvResult);

        btnCompress.setOnClickListener(v -> {
            String text = etInput.getText().toString();
            String result = compressString(text);
            tvResult.setText("Result: " + result);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_algoritmo);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_perfil) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (item.getItemId() == R.id.nav_paises) {
                Intent intent = new Intent(this, PaisesActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            return true;
        });

    }

    /*La lógica que utilice es comparar la letra actual con la siguiente
    La idea es que si la letra actual es "a" y la siguiente es "a" también entonces
    el count aumenta hasta que es diferente es cuando guarda la 1era letra y
    le concatena el count que va. Así hasta terminar el string */
    private String compressString(String input) {
        if (input == null || input.isEmpty()) return "";
        input = input.toLowerCase();
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 0; i < input.length(); i++) {
            if (i + 1 < input.length() && input.charAt(i) == input.charAt(i + 1)) {
                count++;
            } else {
                sb.append(input.charAt(i)).append(count);
                count = 1;
            }
        }
        return sb.toString();
    }
}