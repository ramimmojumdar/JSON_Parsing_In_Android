package com.example.jsonparsinginandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    AppCompatButton loadBtn;
    ProgressBar progressBar;
    TextView tvName, tvEmail, tvPhone, tvAge, tvCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadBtn = findViewById(R.id.loadBtn);
        progressBar = findViewById(R.id.progressBar);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAge = findViewById(R.id.tvAge);
        tvCountry = findViewById(R.id.tvCountry);


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);


                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://hit-and-run-budgets.000webhostapp.com/apps/server.json";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                progressBar.setVisibility(View.GONE);
                                Log.d("serverRes", response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String name = jsonObject.getString("name");
                                    String email = jsonObject.getString("email");
                                    String phone = jsonObject.getString("phone");
                                    Integer age = jsonObject.getInt("age");
                                    String ageString = age.toString();
                                    String country = jsonObject.getString("country");

                                    tvName.setText(name);
                                    tvEmail.setText(email);
                                    tvPhone.setText(phone);
                                    tvAge.setText(ageString);
                                    tvCountry.setText(country);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadBtn.setText("That didn't work!");
                        progressBar.setVisibility(View.GONE);
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });

    }
}