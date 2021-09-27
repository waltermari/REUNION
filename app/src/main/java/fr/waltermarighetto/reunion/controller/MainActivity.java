package fr.waltermarighetto.reunion.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.waltermarighetto.reunion.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new InitRessources();
    }


}