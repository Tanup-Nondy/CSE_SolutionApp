package com.example.cse_solution.ui.slideshow;

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

public class SubjectAdaptar extends RecyclerView.Adapter<SubjectAdaptar.ViewHolder> {
    private List<Model_Subject> model_sList;
    private Context context;

    public SubjectAdaptar(List<Model_Subject> model_sList, Context context) {
        this.model_sList = model_sList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subject, parent, false);
        return new SubjectAdaptar.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdaptar.ViewHolder holder, int i) {
        final Model_Subject model_s = model_sList.get(i);
        holder.sub.setText(model_s.getSubject());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject=model_s.getSubject();
                Intent intent=new Intent(context,BookActivity.class);
                intent.putExtra("subject",subject);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model_sList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sub;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            sub = itemView.findViewById(R.id.subject_tv);
            cv = itemView.findViewById(R.id.sub_cv);
        }
    }
}
