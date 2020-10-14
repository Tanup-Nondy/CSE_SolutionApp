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

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder>{
    private List<Model_R> model_rList;
    private Context context;

    public RoutineAdapter(List<Model_R> model_rList, Context context) {
        this.model_rList = model_rList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_routine,parent,false);
        return new RoutineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineAdapter.ViewHolder holder, int i) {
        final Model_R model_r=model_rList.get(i);
        holder.day.setText(model_r.getDay());
        holder.time.setText(model_r.getTime());
        holder.course.setText(model_r.getCourse());
        holder.teacher.setText(model_r.getTeacher());

    }

    @Override
    public int getItemCount() {
        return model_rList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView day,time,course,teacher;
        CardView cv;
        public ViewHolder(View itemView){
            super(itemView);
            day=itemView.findViewById(R.id.day_tv);
            time=itemView.findViewById(R.id.time_tv);
            course=itemView.findViewById(R.id.course_tv);
            teacher=itemView.findViewById(R.id.teacher_tv);
            cv= itemView.findViewById(R.id.routine_cv);
        }
    }
}
