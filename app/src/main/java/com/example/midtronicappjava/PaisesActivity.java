package com.example.midtronicappjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PaisesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountriesAdapter adapter;
    private List<String> countries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paises);

        recyclerView = findViewById(R.id.recyclerCountries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CountriesAdapter(countries, country -> {
            Intent intent = new Intent(this, DetalleActivity.class);
            intent.putExtra("country", country);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_paises);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_perfil) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (item.getItemId() == R.id.nav_algoritmo) {
                Intent intent = new Intent(this, AlgoritmoActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            return true;
        });

        loadCountries();
    }

    private void loadCountries() {
        new Thread(() -> {
            try {
                URL url = new URL(
                        "https://raw.githubusercontent.com/vinaygaba/Ultimate-String-Array-List/master/Countries.xml"
                );

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream inputStream = conn.getInputStream();
                List<String> result = parseCountries(inputStream);

                runOnUiThread(() -> {
                    countries.clear();
                    countries.addAll(result);
                    adapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private List<String> parseCountries(InputStream inputStream) throws Exception {
        List<String> list = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, null);

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equals("item")) {
                list.add(parser.nextText());
            }
            eventType = parser.next();
        }
        return list;
    }


    interface OnCountryClick {
        void onClick(String country);
    }

    class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {

        private List<String> countries;
        private OnCountryClick listener;

        CountriesAdapter(List<String> countries, OnCountryClick listener) {
            this.countries = countries;
            this.listener = listener;
        }

        @NonNull
        @Override
        public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // CAMBIO: Inflar tu nuevo layout personalizado
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lista_paises, parent, false);
            return new CountryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
            String country = countries.get(position);
            holder.textView.setText(country);
            holder.itemView.setOnClickListener(v -> listener.onClick(country));
        }

        class CountryViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            CountryViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tvCountryName);
            }
        }
        @Override
        public int getItemCount() {
            return countries.size();
        }

    }
}
