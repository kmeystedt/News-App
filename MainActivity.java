package com.example.kylemeystedt.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObj;
    URL url;
    public static class globals{public static String title, author, description, articleURL;}
    String baseUrl = "";
    TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] articles = getResources().getStringArray(R.array.articleNames);

        final ListView lv = findViewById(R.id.articleList);


        ArrayAdapter<String> articleAdapter = new ArrayAdapter<String>(this, R.layout.list_item, articles);
        lv.setAdapter(articleAdapter);

        txtTitle = findViewById(R.id.titleTextView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if(position==0)
            {
                baseUrl = "https://newsapi.org/v2/everything?q=bitcoin&sortBy=publishedAt&apiKey=9939200b6c9a417f89f37ed6edeb83cc";
            }
            else if(position==1)
            {
                baseUrl = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=9939200b6c9a417f89f37ed6edeb83cc";
            }
            else
            {
                baseUrl = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=9939200b6c9a417f89f37ed6edeb83cc";
            }

            try {
                url = new URL(baseUrl);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if (url != null) {
                GetArticleTask getArticleTask = new GetArticleTask();
                getArticleTask.execute(url);
            }
            else {
                Toast.makeText(getApplicationContext(),R.string.invalid_url,Toast.LENGTH_SHORT).show();

            }

            }
        });
    }
    private class GetArticleTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) params[0].openConnection();
                int response = connection.getResponseCode();

                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();

                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {

                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    }
                    catch (IOException e) {
                        Toast.makeText(getApplicationContext(),R.string.read_error,Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    return new JSONObject(builder.toString());
                }
                else {
                    Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            finally {
                connection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject article) {
            convertJSONtoArrayList(article);
            newActivityFunction();
        }
    }
    private void newActivityFunction(){
        final Intent i = new Intent (this, displayAcivity.class);
        i.putExtra("article author", globals.author);
        i.putExtra("article title", globals.title);
        i.putExtra("article url", globals.articleURL);
        i.putExtra("article description", globals.description);

        startActivity(i);
    }

    private void convertJSONtoArrayList(JSONObject response) {

        try {
            jsonObj = (JSONObject) response.getJSONArray("articles").get(0);

            globals.author = jsonObj.getString("author");
            globals.description = jsonObj.getString("description");
            globals.title = jsonObj.getString("title");
            globals.articleURL = jsonObj.getString("url");

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
