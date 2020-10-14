package com.example.cse_solution.ui.slideshow;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cse_solution.BuildConfig;
import com.example.cse_solution.FileDownloader;
import com.example.cse_solution.MainActivity;
import com.example.cse_solution.R;
import com.example.cse_solution.ui.gallery.Model_R;
import com.example.cse_solution.ui.gallery.RoutineAdapter;
import com.example.cse_solution.ui.gallery.RoutineDeatilActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private TextView sub_tv;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter BookAdapter;
    private List<Model_Book> model_bList;
    private String url="https://csesolution.000webhostapp.com/Api/getbooks.php?subject=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        sub_tv=findViewById(R.id.sub_tv);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Books");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        String subject=intent.getStringExtra("subject");
        sub_tv.setText(subject);
        url=url+subject;
        Log.d("Msg", url );
        mRecyclerView=findViewById(R.id.b_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        model_bList=new ArrayList<>();
        loadData();
    }

    private void loadData() {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try{
                    JSONArray jsonArray=new JSONObject(response).getJSONArray("result");
                    for(int i=0;i<jsonArray.length();i++){
                        try{
                            String id=jsonArray.getJSONObject(i).getString("id");
                            String sub=jsonArray.getJSONObject(i).getString("subject");
                            String name=jsonArray.getJSONObject(i).getString("name");
                            String image=jsonArray.getJSONObject(i).getString("image");
                            String pdf=jsonArray.getJSONObject(i).getString("pdf");
                            Model_Book model_b=new Model_Book(id,sub,name,image,pdf);
                            model_bList.add(model_b);
                        }catch (Exception e){
                            Toast.makeText(BookActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    BookAdapter=new BookAdaptar(model_bList,getApplicationContext());
                    mRecyclerView.setAdapter(BookAdapter);
                }catch (Exception e){
                    Toast.makeText(BookActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookActivity.this,"Error"+error.toString(),Toast.LENGTH_SHORT).show();
            }});
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//goto previous page on back press
        return super.onSupportNavigateUp();
    }


}
