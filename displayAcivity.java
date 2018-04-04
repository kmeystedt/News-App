package com.example.kylemeystedt.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class displayAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_acivity);

        TextView txtTitle = findViewById(R.id.textViewTitle);
        TextView txtAuthor = findViewById(R.id.textViewAuthor);
        TextView txtText = findViewById(R.id.textViewText);
        TextView txtURL = findViewById(R.id.textViewURL);

        Intent i = getIntent();
        txtAuthor.setText(i.getStringExtra("article author"));
        txtTitle.setText(i.getStringExtra("article title"));
        txtText.setText(i.getStringExtra("article description"));
        txtURL.setText(i.getStringExtra("article url"));
    }


}
