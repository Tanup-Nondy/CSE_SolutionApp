package com.example.cse_solution.ui.gallery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cse_solution.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoutineDeatilActivity extends AppCompatActivity {
    private TextView ses_tv,sem_tv;
    private EditText search;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter RoutineAdapter;
    private List<Model_R> model_rList;
    private String url="https://csesolution.000webhostapp.com/Api/getroutines.php?ses=";
    private String searchUrl="https://csesolution.000webhostapp.com/Api/searchroutine.php?ses=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_deatil);
        ses_tv=findViewById(R.id.session_tv);
        sem_tv=findViewById(R.id.semester_tv);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Routines");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        final String session=intent.getStringExtra("session");
        final String semester=intent.getStringExtra("semester");
        ses_tv.setText(session);
        sem_tv.setText(semester);
        url=url+session+"&sem="+semester;
        mRecyclerView=findViewById(R.id.r_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        model_rList=new ArrayList<>();
        loadData();
        search=findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=search.getText().toString();
                if (text==null){
                    loadData();
                }else{
                    searchUrl=searchUrl+session+"&sem="+semester+"&search="+text;
                    loadSearch();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void loadSearch(){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr=new StringRequest(Request.Method.GET, searchUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try{
                    model_rList.clear();
                    JSONArray jsonArray=new JSONObject(response).getJSONArray("result");
                    for(int i=0;i<jsonArray.length();i++){
                        try{
                            String day=jsonArray.getJSONObject(i).getString("day");
                            String time=jsonArray.getJSONObject(i).getString("time");
                            String course=jsonArray.getJSONObject(i).getString("course");
                            String teacher=jsonArray.getJSONObject(i).getString("teacher");
                            Model_R model_r=new Model_R(day,time,course,teacher);
                            model_rList.add(model_r);
                        }catch (Exception e){

                            Toast.makeText(RoutineDeatilActivity.this,"E1"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    RoutineAdapter=new RoutineAdapter(model_rList,RoutineDeatilActivity.this);
                    mRecyclerView.setAdapter(RoutineAdapter);
                }catch (Exception e){
                    Model_R model_r=new Model_R("","","","");
                    model_rList.add(model_r);
                    RoutineAdapter=new RoutineAdapter(model_rList,RoutineDeatilActivity.this);
                    mRecyclerView.setAdapter(RoutineAdapter);
                    Toast.makeText(RoutineDeatilActivity.this,"E2"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoutineDeatilActivity.this,"Error3"+error.toString(),Toast.LENGTH_SHORT).show();
            }});
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
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
                            String day=jsonArray.getJSONObject(i).getString("day");
                            String time=jsonArray.getJSONObject(i).getString("time");
                            String course=jsonArray.getJSONObject(i).getString("course");
                            String teacher=jsonArray.getJSONObject(i).getString("teacher");
                            Model_R model_r=new Model_R(day,time,course,teacher);
                            model_rList.add(model_r);
                        }catch (Exception e){
                            Toast.makeText(RoutineDeatilActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    RoutineAdapter=new RoutineAdapter(model_rList,RoutineDeatilActivity.this);
                    mRecyclerView.setAdapter(RoutineAdapter);
                }catch (Exception e){
                    Toast.makeText(RoutineDeatilActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoutineDeatilActivity.this,"Error"+error.toString(),Toast.LENGTH_SHORT).show();
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
