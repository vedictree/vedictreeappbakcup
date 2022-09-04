package com.vedictree.preschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vedictree.preschool.R;

import java.util.ArrayList;


public class StudentProgressAdapter extends RecyclerView.Adapter <StudentProgressAdapter.ViewHolder>{
    private ArrayList<String> progressPercentageArray;
    private ArrayList<String> progressReportSessionName;
    private Context context;
    ArrayList<String> session_image;
    String courseName;
    SharedPreferences.Editor editor;

    public StudentProgressAdapter(Context context,ArrayList<String> progressReportSessionName,ArrayList<String> progressPercentageArray,ArrayList<String> session_image) {
        super();
        this.context = context;
        this.progressPercentageArray=progressPercentageArray;
        this.progressReportSessionName=progressReportSessionName;
        this.session_image=session_image;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_progress_report_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        int num=i + 1 ;
//        viewHolder.session_image.setImageResource(imageArray.get(i));
        String img_path=session_image.get(i);
//        String[] separated = img_path.split("\\.");
        Log.i("img",img_path);
//        Log.i("img",separated[1]);
//        String first_img=separated[0]+"_new.";
//        Log.i("img",first_img);
//        first_img=first_img+separated[1];


        Glide.with(viewHolder.session_image.getContext())
                .asBitmap()
                .load("https://www.vedictreeschool.com/uploads/sessionImages/"+img_path)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        viewHolder.session_image.setImageBitmap(bitmap);

                    }
                });
        Log.i("Image url", String.valueOf(session_image.get(i)));
        viewHolder.session_name.setText(progressReportSessionName.get(i));
        viewHolder.session_progressbar.setProgress(Integer.parseInt(progressPercentageArray.get(i)));
        viewHolder.progress_text.setText(progressPercentageArray.get(i)+"%");
        viewHolder.session_percentage.setText(progressPercentageArray.get(i)+"%");
    }

    @Override
    public int getItemCount() {
        return session_image.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView session_image;
        ProgressBar session_progressbar;
        TextView session_percentage;
        TextView progress_text;
        TextView session_name;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            session_image = itemView.findViewById(R.id.progress_report_item_session_image);
            session_progressbar = itemView.findViewById(R.id.progress_report_item_progressbar);
            session_percentage = itemView.findViewById(R.id.progress_report_item_percentage);
            progress_text = itemView.findViewById(R.id.progress_report_item_percentage_bar);
            session_name=itemView.findViewById(R.id.progress_report_session_name);

        }
    }
}