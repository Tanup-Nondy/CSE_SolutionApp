package com.example.cse_solution.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse_solution.R;
import com.example.cse_solution.ui.gallery.Model_R;
import com.example.cse_solution.ui.gallery.RoutineAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdaptar extends RecyclerView.Adapter<BookAdaptar.ViewHolder>{
    private List<Model_Book> model_bList;
    private Context context;

    public BookAdaptar(List<Model_Book> model_bList, Context context) {
        this.model_bList = model_bList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book,parent,false);
        return new BookAdaptar.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdaptar.ViewHolder holder, int i) {
        final Model_Book model_b=model_bList.get(i);
        String img=model_b.getImage();
        String url="https://csesolution.000webhostapp.com/Uploads/images/"+img;
        holder.name.setText(model_b.getName());
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.image,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image=model_b.getImage();
                String pdf=model_b.getPdf();
                String name=model_b.getName();
                String id=model_b.getId();
                Intent intent=new Intent(context,BookDetailActivity.class);
                intent.putExtra("image",image);
                intent.putExtra("id",id);
                intent.putExtra("pdf",pdf);
                intent.putExtra("name",name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model_bList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView sub,name;
        ImageView image;
        CardView cv;
        public ViewHolder(View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.book_tv);
            image=itemView.findViewById(R.id.imageView1);
            cv= itemView.findViewById(R.id.book_cv);
        }
    }

}
