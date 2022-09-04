package com.vedictree.preschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vedictree.preschool.R;
import com.vedictree.preschool.VideoPlayerDemoVideoPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class SessionAdapter extends RecyclerView.Adapter <SessionAdapter.ViewHolder>{
    private ArrayList<String> sessionImage;
    private ArrayList<String> sessionName;
    private Context context;
    private ArrayList<String> video_id;
    private ArrayList<String> session_id;
    String courseName;
    SharedPreferences.Editor editor;

    public SessionAdapter(Context context, ArrayList<String> session_name, ArrayList<String> session_image,ArrayList<String> video_id,ArrayList<String> session_id) {

//    public SessionAdapter(Context context, ArrayList<String> session_name, ArrayList<Integer> session_image,ArrayList<String> video_id,ArrayList<String> session_id) {
        super();
        this.context = context;
        this.sessionImage=session_image;
        this.sessionName=session_name;
        this.video_id=video_id;
        this.session_id=session_id;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.session_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        int num=i + 1 ;
        viewHolder.mSessionNameText.setText(sessionName.get(i));
        viewHolder.mSessionNameText.setText(sessionName.get(i));
        Log.i("Image ssss",String.valueOf(sessionImage.get(i)));
//        viewHolder.session_image_new.setImageResource(sessionImage.get(i));


        String img_path=sessionImage.get(i);
        String[] separated = img_path.split("\\.");
        Log.i("img",separated[0]);
        Log.i("img",separated[1]);
        String first_img=separated[0]+"_new.";
        Log.i("img",first_img);
        first_img=first_img+separated[1];

        Glide.with(viewHolder.session_image_new.getContext())
                .asBitmap()
//                .load(" https://www.vedictreeschool.com/uploads/sessionImages/rhymes1_new.png")
                .load("https://www.vedictreeschool.com/uploads/sessionImages/"+first_img)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        viewHolder.session_image_new.setImageBitmap(bitmap);
                    }
                });



//        String img_path=sessionImage.get(i);
//        String[] separated = img_path.split(".");
//        Log.i("img",separated[0]);
//        Log.i("img",separated[1]);
//        String first_img=separated[0]+"_new";
//        Log.i("img",separated[0]);

//        Glide.with(viewHolder.session_image_new.getContext())
//                .load("https://www.vedictreeschool.com/uploads/sessionImages/"+sessionImage.get(i))
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        viewHolder.progressBar.setVisibility(View.GONE);
//                        Log.i("onLoadFailed","onLoadFailed");
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        viewHolder.progressBar.setVisibility(View.GONE);
//                        Log.i("onResourceReady","onResourceReady");
//
//                        return false;
//                    }
//                })
//        .into(viewHolder.session_image_new);

        viewHolder.session_image_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://player.vimeo.com/video/"+video_id.get(i)+"/config";
                Log.i("Video id",video_id.get(i));
                StringRequest str=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject resuest=jsonObject.getJSONObject("request");
                            JSONObject files=resuest.getJSONObject("files");
                            JSONArray progress_arr=files.getJSONArray("progressive");
                            JSONObject video_object=jsonObject.getJSONObject("video");
                            JSONObject thumb_object=video_object.getJSONObject("thumbs");
                            String video_title=video_object.getString("title");


                            Iterator x = thumb_object.keys();
                            JSONArray jsonArray = new JSONArray();

                            while (x.hasNext()){
                                String key = (String) x.next();
                                jsonArray.put(thumb_object.get(key));
                            }
                            Log.i("Json from volley",String.valueOf(jsonArray));
                            editor.putString("VIDEO_THUMB", String.valueOf(jsonArray.get(0)));
                            editor.putString("VIDEO_TITLE", video_title);

                            editor.commit();
                            JSONObject url_array=progress_arr.getJSONObject(0);
                            String str_url=url_array.getString("url");
                            String str_quality=url_array.getString("quality");
                            Log.i("Dashboard quality",url_array.getString("quality"));
                            Log.i("Dashboard Url",str_url);
                            if (str_quality.equals("240p")||str_quality.equals("1080p")){
                                url_array=progress_arr.getJSONObject(1);
                                str_url=url_array.getString("url");
                                str_quality=url_array.getString("quality");
                                Log.i("Dashboard quality 240",url_array.getString("quality"));

                                if (str_quality.equals("1080p")||str_quality.equals("240p")){
                                    url_array=progress_arr.getJSONObject(2);
                                    str_url=url_array.getString("url");
                                    str_quality=url_array.getString("quality");
                                    Log.i("Dashboard quality 1080",url_array.getString("quality"));

                                    if (str_quality.equals("1080p")||str_quality.equals("240p")) {
                                        url_array = progress_arr.getJSONObject(3);
                                        str_url = url_array.getString("url");
                                        str_quality = url_array.getString("quality");
                                        Log.i("Dashboard quality 1080", url_array.getString("quality"));
                                        if (str_quality.equals("1080p")||str_quality.equals("240p")){
                                            url_array=progress_arr.getJSONObject(4);
                                            str_url=url_array.getString("url");
                                            str_quality=url_array.getString("quality");
                                            Log.i("Dashboard quality 1080",url_array.getString("quality"));
                                            Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                            editor.putString("VIDEO_VIMEO", str_url);
                                            editor.putString("VIMEO_ID", video_id.get(i));
                                            editor.putString("VIMEO_SESSION_ID", session_id.get(i));
                                            editor.putString("FROM_DASHBOARD", "dashboard");
                                            editor.commit();
                                            view.getContext().startActivity(intent);
                                        }
                                        else {
                                            Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                            editor.putString("VIDEO_VIMEO", str_url);
                                            editor.putString("VIMEO_ID", video_id.get(i));
                                            editor.putString("VIMEO_SESSION_ID", session_id.get(i));
                                            editor.putString("FROM_DASHBOARD", "dashboard");
                                            editor.commit();
                                            view.getContext().startActivity(intent);
                                        }
                                    }
                                    else {
                                        Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                        editor.putString("VIDEO_VIMEO", str_url);
                                        editor.putString("VIMEO_ID", video_id.get(i));
                                        editor.putString("VIMEO_SESSION_ID", session_id.get(i));
                                        editor.putString("FROM_DASHBOARD", "dashboard");
                                        editor.commit();
                                        view.getContext().startActivity(intent);
                                    }
                                }
                                else {
                                    Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                    editor.putString("VIDEO_VIMEO", str_url);
                                    editor.putString("VIMEO_ID", video_id.get(i));
                                    editor.putString("VIMEO_SESSION_ID", session_id.get(i));
                                    editor.putString("FROM_DASHBOARD", "dashboard");
                                    editor.commit();
                                    view.getContext().startActivity(intent);

                                }
                            }
                            else {
                                Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                editor.putString("VIDEO_VIMEO", str_url);
                                editor.putString("VIMEO_ID", video_id.get(i));
                                editor.putString("VIMEO_SESSION_ID", session_id.get(i));
                                editor.putString("FROM_DASHBOARD", "dashboard");
                                editor.commit();
                                view.getContext().startActivity(intent);

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error:",error.toString());
                        Toast.makeText(view.getContext(),"Network error please try again", Toast.LENGTH_LONG).show();
                    }
                });

                RequestQueue requestQueue= Volley.newRequestQueue(view.getContext());
                requestQueue.add(str);
            }
        });


    }

    @Override
    public int getItemCount() {
        return sessionName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mSessionImage;
        TextView mSessionNameText;
        LinearLayout back_ll;
        LinearLayout back_ll_two;
        ImageView session_image_new;
        ProgressBar progressBar;
        ViewHolder(View itemView) {
            super(itemView);
            mSessionNameText=itemView.findViewById(R.id.session_name_text);
            mSessionImage=itemView.findViewById(R.id.session_image_view);
            back_ll=itemView.findViewById(R.id.session_name_ll);
            back_ll_two=itemView.findViewById(R.id.session_name_ll_two);
            session_image_new=itemView.findViewById(R.id.new_sesion_iv);
              progressBar = itemView.findViewById(R.id.progress);
        }
    }
}