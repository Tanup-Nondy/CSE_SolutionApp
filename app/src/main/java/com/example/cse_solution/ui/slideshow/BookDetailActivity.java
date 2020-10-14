package com.example.cse_solution.ui.slideshow;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

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
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cse_solution.BuildConfig;
import com.example.cse_solution.FileDownloader;
import com.example.cse_solution.PDFTools;
import com.example.cse_solution.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookDetailActivity extends AppCompatActivity {
    String rating="https://csesolution.000webhostapp.com/Api/getrating.php?id=";
    String post_rating="https://csesolution.000webhostapp.com/Api/postrating.php";
    String user_rating="";
    String file_loc="https://csesolution.000webhostapp.com/Uploads/pdfs/";
    String img="https://csesolution.000webhostapp.com/Uploads/images/";
    String image="";
    String avg_rate="0";
    String user_id="";
    String pdf="";
    String id="";
    ImageView iv;
    RatingBar av_rb,user_rb;
    Button save;
    private static final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ActivityCompat.requestPermissions(BookDetailActivity.this, PERMISSIONS, 112);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Book's Detail");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        String name=intent.getStringExtra("name");
        image=intent.getStringExtra("image");
        pdf=intent.getStringExtra("pdf");
        id=intent.getStringExtra("id");
        iv=findViewById(R.id.imageView2);
        av_rb=findViewById(R.id.av_rb);
        user_id= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        user_rb=findViewById(R.id.user_rb);
        save=findViewById(R.id.save_rt);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_rating=String.valueOf(user_rb.getRating());
                Log.d("rate","\n\n\n\n\n\nhi"+user_rating+id);
                //Toast.makeText(BookDetailActivity.this, "Rating is "+id+user_rating, Toast.LENGTH_LONG);
                saveRating(user_rating);
            }

        });

        file_loc=file_loc+pdf;
        img=img+image;
        rating=rating+id;
        loadImage(img);
        loadRating(rating);
    }
    private void saveRating(String user_rate){

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, post_rating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("error")) {
                    Toast.makeText(BookDetailActivity.this, "Error Occurs", Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(BookDetailActivity.this, "Succesfully rated", Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id", id); //Add the data you'd like to send to the server.
                MyData.put("rating", user_rating); //Add the data you'd like to send to the server.
                MyData.put("user_id", user_id); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        MyRequestQueue.add(MyStringRequest);
    }
    private void loadRating(String rating) {

        StringRequest sr=new StringRequest(Request.Method.GET, rating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray=new JSONObject(response).getJSONArray("ratings");
                    avg_rate=jsonArray.getJSONObject(0).getString("rating").toString();
                    av_rb.setRating(Float.parseFloat(avg_rate));


                }catch (Exception e){
                    Log.d("Error2",e.toString());
                    Toast.makeText(BookDetailActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MainError",error.toString());
                Toast.makeText(BookDetailActivity.this,"Error"+error.toString(),Toast.LENGTH_SHORT).show();
            }});

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);


    }
    private void loadImage(String url){
        Picasso.with(BookDetailActivity.this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
    public void view(View view) {
        Log.v(TAG, "view() Method invoked ");

        if (!hasPermissions(BookDetailActivity.this, PERMISSIONS)) {

            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");

            Toast t = Toast.makeText(BookDetailActivity.this, "You don't have read access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);  // -> filename = maven.pdf
            File pdfFile = new File(d, pdf);

            Log.v(TAG, "view() Method pdfFile " + pdfFile.getAbsolutePath());

            Uri path = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", pdfFile);


            Log.v(TAG, "view() Method path " + path);

            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(pdfIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(BookDetailActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }
        }
        Log.v(TAG, "view() Method completed ");

    }
    public void download(View view) {
        Log.v(TAG, "download() Method invoked ");

        if (!hasPermissions(BookDetailActivity.this, PERMISSIONS)) {

            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");

            Toast t = Toast.makeText(BookDetailActivity.this, "You don't have write access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            Log.v(TAG, "download() Method HAVE PERMISSIONS ");

            //new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
            //new DownloadFile().execute(file_loc, pdf);
            PDFTools.showPDFUrl(BookDetailActivity.this, file_loc);

        }

        Log.v(TAG, "download() Method completed ");


        //PDFTools.showPDFUrl(getApplicationContext(), file_loc);

    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            Log.v(TAG, "doInBackground() Method invoked ");

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File pdfFile = new File(folder, fileName);
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsolutePath());
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsoluteFile());

            try {
                pdfFile.createNewFile();
                Log.v(TAG, "doInBackground() file created" + pdfFile);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground() error" + e.getMessage());
                Log.e(TAG, "doInBackground() error" + e.getStackTrace());


            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            Log.v(TAG, "doInBackground() file download completed");
            Toast.makeText(BookDetailActivity.this, "File download completed !", Toast.LENGTH_LONG);
            return null;
        }
    }
}
