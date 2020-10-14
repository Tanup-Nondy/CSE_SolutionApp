package com.example.cse_solution.ui.gallery;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter Ses_Adapter;
    private List<Model_S> model_sList;
    private String url="https://csesolution.000webhostapp.com/Api/getsession.php";
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView=root.findViewById(R.id.g_rv);
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
                            String session=jsonArray.getJSONObject(i).getString("session");
                            String semester=jsonArray.getJSONObject(i).getString("semester");
                            Log.d("MSg",session);
                            Model_S model_s=new Model_S(session,semester);
                            model_sList.add(model_s);
                        }catch (Exception e){
                            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    Ses_Adapter=new Ses_Adapter(model_sList,getContext());
                    mRecyclerView.setAdapter(Ses_Adapter);
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