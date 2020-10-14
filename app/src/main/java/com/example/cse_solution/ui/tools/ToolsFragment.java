package com.example.cse_solution.ui.tools;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cse_solution.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private String pdf_url="https://csesolution.000webhostapp.com/Api/getsyllabus.php";
    String file_loc="https://csesolution.000webhostapp.com/Uploads/syllabus/";
    String name="";
    private WebView web_view;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        web_view = root.findViewById(R.id.web_view);
        loadData();

        return root;
    }
    private void loadData() {
        final ProgressDialog pd=new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr=new StringRequest(Request.Method.GET, pdf_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try{
                    JSONArray jsonArray=new JSONObject(response).getJSONArray("result");
                    for(int i=0;i<jsonArray.length();i++){
                        try{
                            name=jsonArray.getJSONObject(i).getString("pdf");
                            Log.d("pdfurl",name);
                        }catch (Exception e){
                            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    file_loc=file_loc+name;
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading Data...");
                    progressDialog.setCancelable(false);
                    web_view.requestFocus();
                    web_view.getSettings().setJavaScriptEnabled(true);
                    //Log.d("URLVALUE",name);
                    String url = "https://docs.google.com/viewer?embedded=true&url="+file_loc;
                    web_view.loadUrl(url);
                    web_view.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                        }
                    });
                    web_view.setWebChromeClient(new WebChromeClient() {
                        public void onProgressChanged(WebView view, int progress) {
                            if (progress < 100) {
                                 progressDialog.show();
                            }
                            if (progress == 100) {
                                progressDialog.dismiss();
                            }
                        }
                    });
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
        //file_loc=file_loc+name;
        //final ProgressDialog progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Loading Data...");
        //progressDialog.setCancelable(false);

        //web_view.requestFocus();
        //web_view.getSettings().setJavaScriptEnabled(true);
        //Log.d("URLVALUE",name);
        //String url = "https://docs.google.com/viewer?embedded = true&url = "+file_loc;
        //web_view.loadUrl(url);
        /*web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });*/

    }
}