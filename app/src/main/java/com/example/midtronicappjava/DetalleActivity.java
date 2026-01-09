package com.example.midtronicappjava;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetalleActivity extends AppCompatActivity {

    TextView txtName, txtCapital, txtPopulation, txtArea, txtRegion, txtSubregion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle );

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        txtName = findViewById(R.id.tvCountryName);
        txtCapital = findViewById(R.id.tvCapital);
        txtPopulation = findViewById(R.id.tvPopulation);
        txtArea = findViewById(R.id.tvArea);
        txtRegion = findViewById(R.id.tvRegion);
        txtSubregion = findViewById(R.id.tvSubregion);

        String country = getIntent().getStringExtra("country");

        loadCountryDetail(country);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCountryDetail(String country) {
        new Thread(() -> {
            try {
                URL url = new URL(
                        "https://restcountries.com/v3.1/name/" + country
                );

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder json = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                JSONArray array = new JSONArray(json.toString());
                JSONObject obj = array.getJSONObject(0);

                JSONObject name = obj.getJSONObject("name");
                String commonName = name.getString("common");

                String capital = obj.getJSONArray("capital").getString(0);
                long population = obj.getLong("population");
                double area = obj.getDouble("area");
                String region = obj.getString("region");
                String subregion = obj.getString("subregion");

                runOnUiThread(() -> {
                    txtName.setText("Name: " + commonName);
                    txtCapital.setText("Capital: " + capital);
                    txtPopulation.setText("Population: " + population);
                    txtArea.setText("Area: " + area);
                    txtRegion.setText("Region: " + region);
                    txtSubregion.setText("Sub-region: " + subregion);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
