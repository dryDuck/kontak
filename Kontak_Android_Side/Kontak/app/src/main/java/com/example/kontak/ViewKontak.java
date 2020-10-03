package com.example.kontak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kontak.databinding.ActivityViewKontakBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewKontak extends AppCompatActivity {

    private ActivityViewKontakBinding binding;
    private TextView namaKontak, nomorTelpon, jabatan, alamat;
    private Button actTelpon, actSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_kontak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        namaKontak = findViewById(R.id.ctvNamaKontak);
        nomorTelpon = findViewById(R.id.ctvNomorTelpon);
        jabatan = findViewById(R.id.ctvJabatan);
        alamat = findViewById(R.id.ctvAlamat);
        actTelpon = findViewById(R.id.actTelpon);
        actSms = findViewById(R.id.actSms);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusKontak();
            }
        });
        kontak();
    }

    void kontak(){
        String url = "http://192.168.100.7/android/kontak/tampilKontak.php?id=" + getIntent().getStringExtra("id");
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            namaKontak.setText(jsonObject.getString("namaKontak"));
                            nomorTelpon.setText(jsonObject.getString("nomor_telpon"));
                            jabatan.setText(jsonObject.getString("jabatan"));
                            alamat.setText(jsonObject.getString("alamat"));

                            actSms.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                                    smsIntent.setType("vnd.android-dir/mms-sms");
                                    smsIntent.putExtra("address",nomorTelpon.getText().toString());
                                    smsIntent.putExtra("sms_body","Hay!");
                                    smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(smsIntent);
                                }
                            });

                            actTelpon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + nomorTelpon.getText().toString()));
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void hapusKontak(){
        String url = "http://192.168.100.7/android/kontak/hapusKontak.php?id=" + getIntent().getStringExtra("id");
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if(status.equals("Data Berhasil Dihapus")){
                                Toast.makeText(ViewKontak.this, status, Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(ViewKontak.this, status, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}