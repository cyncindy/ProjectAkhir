package com.example.projectakhir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<ModelList>list;
    ListView listView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar = findViewById(R.id.toolbar);
        listView =  findViewById(R.id.listview2);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        post_data();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_create){
            startActivity(new Intent(MainActivity.this,CreateActivity.class));
        }
        if (item.getItemId() == R.id.action_post){
            startActivity(new Intent(MainActivity.this,PostActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    void post_data(){
        list = new ArrayList<>();
        String url = "http://192.168.43.133/infoapp/tampil.php";
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);
                                String id = getData.getString("id");
                                String title = getData.getString("title");
                                String detail = getData.getString("detail");
                                String image = getData.getString("image");
                                list.add(new ModelList(id, title, detail, image));
                            }
                            AdapterMain adapter = new AdapterMain(MainActivity.this, list);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

class AdapterMain extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<ModelList> model;

    public AdapterMain(Context context, ArrayList<ModelList> model) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView titleMain, detailMain;
    Button showMain;
    ImageView imageMain;

    @Override
    public View getView(final int position, View contextView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_row_info, parent, false);
        String path = "http://192.168.43.133/infoapp/image/";

        showMain = view.findViewById(R.id.btn_show_info);

        titleMain = view.findViewById(R.id.tv_item_name);
        detailMain = view.findViewById(R.id.tv_item_detail);
        imageMain = view.findViewById(R.id.img_item_photo);

        titleMain.setText(model.get(position).getTitle());
        detailMain.setText(model.get(position).getDetail());
        Glide.with(context.getApplicationContext())
                .load(path + model.get(position).getImage())
                .into(imageMain);

        showMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowData.class);
                intent.putExtra("title", model.get(position).getTitle());
                intent.putExtra("detail", model.get(position).getDetail());
                intent.putExtra("image", model.get(position).getImage());
                context.startActivity(intent);
            }
        });

        return view;
    }
}