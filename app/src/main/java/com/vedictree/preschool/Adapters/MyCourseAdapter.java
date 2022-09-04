package com.vedictree.preschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.CourseGold;
import com.vedictree.preschool.CourseRock;
import com.vedictree.preschool.CourseShining;
import com.vedictree.preschool.CourseSuper;
import com.vedictree.preschool.CourseTwinking;
import com.vedictree.preschool.R;

import java.util.ArrayList;


public class MyCourseAdapter extends RecyclerView.Adapter <MyCourseAdapter.ViewHolder>{
    private ArrayList<String> imageArray;
    private ArrayList<String> textArray;
    private ArrayList<String> planArray;
    private Context context;
    String courseName;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    public MyCourseAdapter(Context context,String course_name, ArrayList<String> imageArray,ArrayList<String> planArray) {
        super();
        this.context = context;
        this.imageArray=imageArray;
        this.textArray=textArray;
        this.planArray=planArray;
        courseName=course_name;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_course_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        int num=i + 1 ;
//        String plan_id=preferences.getString("PLAN_ID","");
            viewHolder.month_button.setClickable(true);
//            viewHolder.month_button.setImageResource(imageArray.get(i));

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(viewHolder.month_button.getContext());
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
        Glide.with(viewHolder.month_button.getContext())
                .load(imageArray.get(i))
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(viewHolder.month_button);


            viewHolder.month_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString("MY_COURSE_FROM", "course");
                    editor.commit();
                  String  plan_id=planArray.get(i);
                    if (plan_id.equals("11")||plan_id.equals("1")||plan_id.equals("6")) {
                                Intent intent = new Intent(view.getContext(), CourseGold.class);
                                view.getContext().startActivity(intent);
                            } else if (plan_id.equals("12")||plan_id.equals("7")||plan_id.equals("2")) {
                                Intent intent = new Intent(view.getContext(), CourseTwinking.class);
                                view.getContext().startActivity(intent);
                            } else if (plan_id.equals("13")||plan_id.equals("8")||plan_id.equals("3")) {
                                Intent intent = new Intent(view.getContext(), CourseShining.class);
                                view.getContext().startActivity(intent);
                            } else if (plan_id.equals("14")||plan_id.equals("9")||plan_id.equals("4")) {
                                Intent intent = new Intent(view.getContext(), CourseRock.class);
                                view.getContext().startActivity(intent);
                            } else if (plan_id.equals("15")||plan_id.equals("10")||plan_id.equals("16")) {
                                Intent intent = new Intent(view.getContext(), CourseSuper.class);
                                view.getContext().startActivity(intent);
                            }

//                    switch (courseName) {
//                        case "Nursery":
//                        case "Senior":
//                        case "Junior":
//                            Log.i("Courese is:", courseName);
//
//                            if (i == 0) {
//                                Intent intent = new Intent(view.getContext(), CourseGold.class);
//                                view.getContext().startActivity(intent);
//                            } else if (i == 1) {
//                                Intent intent = new Intent(view.getContext(), CourseTwinking.class);
//                                view.getContext().startActivity(intent);
//                            } else if (i == 2) {
//                                Intent intent = new Intent(view.getContext(), CourseShining.class);
//                                view.getContext().startActivity(intent);
//                            } else if (i == 3) {
//                                Intent intent = new Intent(view.getContext(), CourseRock.class);
//                                view.getContext().startActivity(intent);
//                            } else if (i == 4) {
//                                Intent intent = new Intent(view.getContext(), CourseSuper.class);
//                                view.getContext().startActivity(intent);
//                            }
//                            break;
//                        default:
//                            break;
//                    }
                }
            });


    }

    @Override
    public int getItemCount() {
        return imageArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView month_button;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            month_button = itemView.findViewById(R.id.course_image);
            month_button.setClickable(true);
        }
    }
}