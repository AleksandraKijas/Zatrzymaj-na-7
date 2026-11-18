package com.example.zatrzymajna7;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvLicznik;
    private TextView tvKomunikat;
    private TextView tvTrafienia;

    private Button btnStart;
    private Button btnStop;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private int aktualnaCyfra = 0;
    private int liczbaTrafien = 0;

    private boolean licznikDziala = false;

    private long czasZmiany = 500;

    private final Runnable zmianaCyfry = new Runnable() {
        @Override
        public void run() {

            aktualnaCyfra++;

            if (aktualnaCyfra > 9) {
                aktualnaCyfra = 0;
            }

            tvLicznik.setText(String.valueOf(aktualnaCyfra));

            handler.postDelayed(this, czasZmiany);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLicznik = findViewById(R.id.tvLicznik);
        tvKomunikat = findViewById(R.id.tvKomunikat);
        tvTrafienia = findViewById(R.id.tvTrafienia);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(view -> uruchomLicznik());

        btnStop.setOnClickListener(view -> zatrzymajLicznik());
    }

    private void uruchomLicznik() {

        handler.removeCallbacks(zmianaCyfry);

        aktualnaCyfra = 0;
        tvLicznik.setText("0");

        tvKomunikat.setText("");

        licznikDziala = true;

        btnStart.setEnabled(false);
        btnStop.setEnabled(true);

        handler.postDelayed(zmianaCyfry, czasZmiany);
    }

    private void zatrzymajLicznik() {

        if (!licznikDziala) {
            return;
        }

        licznikDziala = false;

        handler.removeCallbacks(zmianaCyfry);

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        if (aktualnaCyfra == 7) {

            tvKomunikat.setText("Trafienie!");
            liczbaTrafien++;
            tvTrafienia.setText(
                    "Trafienia: " + liczbaTrafien
            );
            czasZmiany = czasZmiany - 50;

            if (czasZmiany < 200) {
                czasZmiany = 200;
            }

        } else {

            tvKomunikat.setText(
                    "Pudło! Zatrzymano na " + aktualnaCyfra
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(zmianaCyfry);
    }
}