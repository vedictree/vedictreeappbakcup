package com.vedictree.preschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.vedictree.preschool.R;

import java.util.ArrayList;


public class StudentProgressCategoriesAdapter extends RecyclerView.Adapter <StudentProgressCategoriesAdapter.ViewHolder>{
    private ArrayList<Integer> imageArray;
    private ArrayList<String> progressPercentageArray;
    private ArrayList<String> progressReportSessionName;
    private Context context;
    String courseName;
    SharedPreferences.Editor editor;

    public StudentProgressCategoriesAdapter(Context context, ArrayList<String> progressPercentageArray,ArrayList<String> progressReportSessionName) {
        super();
        this.context = context;
        this.progressReportSessionName=progressReportSessionName;
        this.progressPercentageArray=progressPercentageArray;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_progress_report_categories, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.progress_text.setText(progressReportSessionName.get(i));
        viewHolder.session_progressbar.setProgress(Integer.parseInt(progressPercentageArray.get(i)));
        viewHolder.session_percentage.setText(progressPercentageArray.get(i)+"%");
    }

    @Override
    public int getItemCount() {
        return progressReportSessionName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ProgressBar session_progressbar;
        TextView session_percentage;
        TextView progress_text;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            session_progressbar = itemView.findViewById(R.id.progress_bar_categories);
            session_percentage = itemView.findViewById(R.id.categories_percentage);
            progress_text = itemView.findViewById(R.id.category_text);

        }
    }
}