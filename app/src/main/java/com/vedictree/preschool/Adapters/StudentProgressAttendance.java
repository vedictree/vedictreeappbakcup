package com.vedictree.preschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vedictree.preschool.R;

import java.util.ArrayList;

public class StudentProgressAttendance extends RecyclerView.Adapter <StudentProgressAttendance.ViewHolder>{
    private ArrayList<String> dayArray;
    private ArrayList<String> timeArray;
    private ArrayList<String> present_absent_array;
    private Context context;
    View v ;

    public StudentProgressAttendance(Context context, ArrayList<String> dayArray,ArrayList<String> timeArray,ArrayList<String> present_absent_array ) {
        super();
        this.context = context;
        this.dayArray=dayArray;
        this.timeArray=timeArray;
        this.present_absent_array=present_absent_array;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            v= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.student_progress_attendance_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.day_str.setText(dayArray.get(i));
        viewHolder.time_str.setText(timeArray.get(i));
        if (present_absent_array.get(i).equals("Absent")){
            viewHolder.p_a_str.setTextColor(Color.RED);
        }else {
            viewHolder.p_a_str.setTextColor(Color.BLACK);
        }
        viewHolder.p_a_str.setText(present_absent_array.get(i));
    }

    @Override
    public int getItemCount() {
        return dayArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView day_str;
        TextView time_str;
        TextView p_a_str;
        ViewHolder(View itemView) {
            super(itemView);
            day_str = itemView.findViewById(R.id.day_string);
            time_str = itemView.findViewById(R.id.time_string);
            p_a_str = itemView.findViewById(R.id.p_a_string);

        }
    }
}