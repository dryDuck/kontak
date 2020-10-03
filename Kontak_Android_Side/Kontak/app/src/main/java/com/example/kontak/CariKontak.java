package com.example.kontak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kontak.databinding.CariKontakBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CariKontak extends AppCompatActivity implements View.OnClickListener{

    private CariKontakBinding binding;
    private ArrayList<Objek> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CariKontakBinding.inflate(this.getLayoutInflater());
        binding.btnResetCari.setOnClickListener(this);
        binding.btnCancelCari.setOnClickListener(this);
        setContentView(binding.getRoot());

        binding.etxCariKontak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cariData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    void cariData(String d){
        String url = "http://192.168.100.7/android/kontak/cariKontak.php?q=" + d;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("hasil");
                            model = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject getData = jsonArray.getJSONObject(i);

                                String id = getData.getString("id");
                                String namaKontak = getData.getString("nama_kontak");
                                String nomorTelpon = getData.getString("nomor_telpon");

                                model.add(new Objek(id, namaKontak, nomorTelpon, "", ""));
                            }
                            Adapter adapter = new Adapter(getApplicationContext(), model);
                            binding.lvCari.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CariKontak.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v){
        if(v == binding.btnResetCari){
            binding.etxCariKontak.setText("");
        }
        if(v == binding.btnCancelCari){
            finish();
        }
    }

}

//
//class Adapter extends BaseAdapter {
//    LayoutInflater layoutInflater;
//    Context context;
//    ArrayList<Objek> model;
//    Adapter(Context context, ArrayList<Objek> model)
//    {
//        this.context = context;
//        this.model = model;
//
//        this.layoutInflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        return model.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return model.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    TextView tvNamaKontak, tvNomorTelpon;
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        View view1 = layoutInflater.inflate(R.layout.list_data, viewGroup, false);
//        tvNamaKontak = view1.findViewById(R.id.tvNamaKontak);
//        tvNomorTelpon = view1.findViewById(R.id.tvNomorTelpon);
//
//        tvNamaKontak.setText(model.get(i).getNamaKontak());
//        tvNomorTelpon.setText(model.get(i).getNomorTelpon());
//
//        return view1;
//    }
//}