package com.example.projectakhir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    //Toolbar toolbar;
    ArrayList<ModelList>list;
    ListView listView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //toolbar = findViewById(R.id.toolbar);
        listView =  findViewById(R.id.listview);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        post_data();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    void post_data(){
        list = new ArrayList<>();
        String url = "http://192.168.43.133/infoapp/tampilpost.php";
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
                            Adapter adapter = new Adapter(PostActivity.this, list);
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

class Adapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    ArrayList<ModelList> model;
    public Adapter(Context context, ArrayList<ModelList>model){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.model=model;
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

    TextView titlePost, detailPost;
    Button show,edit,delete;
    ImageView image;

    @Override
    public View getView(final int position, View contextView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_row_post, parent, false);
        String path = "http://192.168.43.133/infoapp/image/";

        show = view.findViewById(R.id.show);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);

        titlePost = view.findViewById(R.id.posttitle);
        detailPost = view.findViewById(R.id.postdetail);
        image = view.findViewById(R.id.img_info);

        titlePost.setText(model.get(position).getTitle());
        detailPost.setText(model.get(position).getDetail());
        Glide.with(context.getApplicationContext())
                .load(path+model.get(position).getImage())
                .into(image);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShowData.class);
                intent.putExtra("title",model.get(position).getTitle());
                intent.putExtra("detail",model.get(position).getDetail());
                intent.putExtra("image",model.get(position).getImage());
                context.startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditData.class);
                intent.putExtra("id",model.get(position).getId());
                intent.putExtra("title",model.get(position).getTitle());
                intent.putExtra("detail",model.get(position).getDetail());
                context.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_data(model.get(position).getId());
            }
        });

        return view;
    }

    void delete_data(String id){
        String url = "http://192.168.43.133/infoapp/hapus.php?id="+id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("oke")) {
                                Toast.makeText(context, "Content Successfully Deleted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}