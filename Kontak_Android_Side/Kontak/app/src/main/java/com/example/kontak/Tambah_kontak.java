package com.example.kontak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kontak.databinding.TambahKontakBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Tambah_kontak extends AppCompatActivity implements View.OnClickListener{

    private TambahKontakBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TambahKontakBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancel.setOnClickListener(this);
        binding.btnInsert.setOnClickListener(this);
    }


    void addKontak(){
        String url = "http://192.168.100.7/android/kontak/simpan_kontak.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String statusKirim = jsonObject.getString("status");

                            if (statusKirim.equals("oke")){
                                Toast.makeText(Tambah_kontak.this, "Kontak Tersimpan", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Tambah_kontak.this,"Kontak Gagal Disimpan", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Tambah_kontak.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<String, String>();

                stringStringHashMap.put("nama_kontak", binding.etxNamaKontak.getText().toString());
                stringStringHashMap.put("nomor_telpon", binding.etxNomorTelpon.getText().toString());
                stringStringHashMap.put("jabatan", binding.etxJabatan.getText().toString());
                stringStringHashMap.put("alamat", binding.etxAlamat.getText().toString());

                    return stringStringHashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    void testButton(){
        Toast.makeText(this, "ini tes", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v){
        if(v == binding.btnCancel){
            startActivity(new Intent(Tambah_kontak.this, MainActivity.class));
        }
        if(v == binding.btnInsert){
            addKontak();
        }
    }
}
