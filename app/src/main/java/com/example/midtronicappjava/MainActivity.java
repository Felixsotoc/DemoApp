package com.example.midtronicappjava;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setSelectedItemId(R.id.nav_perfil);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_paises) {
                Intent intent = new Intent(MainActivity.this, PaisesActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            if (item.getItemId() == R.id.nav_algoritmo) {
                Intent intent = new Intent(MainActivity.this, AlgoritmoActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            return true;
        });
    }



}