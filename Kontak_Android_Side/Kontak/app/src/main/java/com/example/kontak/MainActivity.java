package com.example.kontak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kontak.databinding.ActivityMainBinding;
import com.example.kontak.databinding.ListDataBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;
    ArrayList<Objek> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        binding.btnTambahKontak.setOnClickListener(this);
        binding.btnCariKontak.setOnClickListener(this);
        load();
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        load();
        super.onResume();
    }

    @Override
    public void onClick(View v){
        if(v == binding.btnTambahKontak){
            startActivity(new Intent(MainActivity.this, Tambah_kontak.class));
        }
        if(v == binding.btnCariKontak){
            startActivity(new Intent(MainActivity.this, CariKontak.class));
        }
    }


    void load(){
        String url = "http://192.168.100.7/android/kontak/dataKontak.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("hasil");
                            model = new ArrayList();

                            for(int i = 0; i< jsonArray.length(); i++){
                                JSONObject getData = jsonArray.getJSONObject(i);
                                String id = getData.getString("id");
                                String namaKontak = getData.getString("nama_kontak");
                                String nomorTelpon = getData.getString("nomor_telpon");
                                String jabatan = getData.getString("jabatan");
                                String alamat = getData.getString("alamat");

//                                Toast.makeText(MainActivity.this, alamat, Toast.LENGTH_LONG).show();
                                model.add(new Objek(id, namaKontak, nomorTelpon, jabatan, alamat));
                            }

                            Adapter adapter = new Adapter(getApplicationContext(), model);
                            binding.listView.setAdapter(adapter);
                            binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(getApplicationContext(), ViewKontak.class);
                                    intent.putExtra("id", model.get(i).getId());
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
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}

class Adapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Objek> model;
    Adapter(Context context, ArrayList<Objek> model)
    {
        this.context = context;
        this.model = model;

        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    TextView tvNamaKontak, tvNomorTelpon;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = layoutInflater.inflate(R.layout.list_data, viewGroup, false);
        tvNamaKontak = view1.findViewById(R.id.tvNamaKontak);
        tvNomorTelpon = view1.findViewById(R.id.tvNomorTelpon);

        tvNamaKontak.setText(model.get(i).getNamaKontak());
        tvNomorTelpon.setText(model.get(i).getNomorTelpon());

        return view1;
    }
}