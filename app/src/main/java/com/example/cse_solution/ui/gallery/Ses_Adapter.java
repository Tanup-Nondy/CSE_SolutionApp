package com.example.cse_solution.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse_solution.R;

import java.util.List;

public class Ses_Adapter extends RecyclerView.Adapter<Ses_Adapter.ViewHolder>{
    private List<Model_S> model_sList;
    private Context context;

    public Ses_Adapter(List<Model_S> model_sList, Context context) {
        this.model_sList = model_sList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Model_S model_s=model_sList.get(i);
        holder.session.setText(model_s.getSession());
        holder.semester.setText(model_s.getSemester());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String session=model_s.getSession();
                String semester=model_s.getSemester();
                Intent intent=new Intent(context,RoutineDeatilActivity.class);
                intent.putExtra("session",session);
                intent.putExtra("semester",semester);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model_sList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView session,semester;
        CardView cv;
        public ViewHolder(View itemView){
            super(itemView);
            session=itemView.findViewById(R.id.ses_tv);
            semester=itemView.findViewById(R.id.sem_tv);
            cv= itemView.findViewById(R.id.g_cv);
        }
    }
}
