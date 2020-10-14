package com.example.cse_solution.ui.slideshow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cse_solution.R;
import com.example.cse_solution.ui.gallery.Model_S;
import com.example.cse_solution.ui.gallery.Ses_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter SubjectAdaptar;
    private List<Model_Subject> model_sList;
    private SlideshowViewModel slideshowViewModel;
    private String url="https://csesolution.000webhostapp.com/Api/getsubject.php";
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        mRecyclerView=root.findViewById(R.id.s_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        model_sList=new ArrayList<>();
        loadData();
        return root;
    }
    private void loadData() {
        final ProgressDialog pd=new ProgressDialog(getContext());
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
                            String subject=jsonArray.getJSONObject(i).getString("subject");
                            Model_Subject model_s=new Model_Subject(subject);
                            model_sList.add(model_s);
                        }catch (Exception e){
                            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    SubjectAdaptar=new SubjectAdaptar(model_sList,getContext());
                    mRecyclerView.setAdapter(SubjectAdaptar);
                }catch (Exception e){
                    Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error"+error.toString(),Toast.LENGTH_SHORT).show();
            }});
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(sr);
    }
}