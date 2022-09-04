package com.vedictree.preschool.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vedictree.preschool.R;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter <NotificationAdapter.ViewHolder>{
    private ArrayList<Integer> imageArray;
    private ArrayList<String> textArray;
    private Context context;
    SharedPreferences.Editor editor;
    String orientation_str;
    View v;
    SharedPreferences preferences;

    public NotificationAdapter(Context context, ArrayList<String> textArray) {
        super();
        this.context = context;
        this.imageArray=imageArray;
        this.textArray=textArray;
         preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            v= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.notification_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        int num=i + 1 ;
        viewHolder.text_view.setText(textArray.get(i));
        viewHolder.index_view.setText(String.valueOf(num));
    }

    @Override
    public int getItemCount() {
        return textArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_view;
        TextView index_view;
        ViewHolder(View itemView) {
            super(itemView);
            text_view=itemView.findViewById(R.id.notification_text);
            index_view=itemView.findViewById(R.id.notification_index);
        }
    }
}