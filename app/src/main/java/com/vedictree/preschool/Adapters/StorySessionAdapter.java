package com.vedictree.preschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.vedictree.preschool.YouTubeVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StorySessionAdapter extends RecyclerView.Adapter <StorySessionAdapter.ViewHolder>{
    private ArrayList<String> sessionImage;
    private ArrayList<Integer> back_image;
    private ArrayList<Integer> back_image_two;
    private ArrayList<String> sessionName;
    private ArrayList<String> video_id;
    private ArrayList<String> session_id;

    private  ArrayList<String> video_type;
    private Context context;
    String courseName;
    SharedPreferences.Editor editor;

    public StorySessionAdapter(Context context, ArrayList<String> session_name, ArrayList<String> session_image, ArrayList<String> video_id, ArrayList<String> session_id, ArrayList<String> video_type) {
        super();
        this.context = context;
        this.sessionImage=session_image;
        this.sessionName=session_name;
        this.video_type=video_type;
        this.video_id=video_id;
        this.session_id=session_id;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_session_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        int num=i + 1 ;

        viewHolder.session_image_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("video_id",video_id.get(i));

                if (video_type.get(i).equals("Youtube")) {
                    Uri uri=Uri.parse(video_id.get(i));
                    String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
                    Pattern compiledPattern = Pattern.compile(pattern);
                    Matcher matcher = compiledPattern.matcher(uri.toString());
                    if (matcher.find()) {
                        Log.i("Match is", matcher.group());
                        editor.putString("YOU_TUBE_ID", matcher.group());
                        editor.commit();
                        Intent webIntent1 = new Intent(view.getContext(), YouTubeVideo.class);
                        view.getContext().startActivity(webIntent1);
                    }
                }
                else {
                    Log.i("Vimeo id", video_id.get(i));
                    String url = "https://player.vimeo.com/video/" + video_id.get(i) + "/config";
                    Log.i("Vimeo id", url);

                    StringRequest str = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("Vimeo id", url);

                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject resuest = jsonObject.getJSONObject("request");
                                JSONObject files = resuest.getJSONObject("files");
                                JSONArray progress_arr = files.getJSONArray("progressive");
                                Log.i("Json from volley", String.valueOf(progress_arr));
                                JSONObject url_array = progress_arr.getJSONObject(0);
                                String str_url = url_array.getString("url");
                                String str_quality = url_array.getString("quality");
                                Log.i("Dashboard quality", url_array.getString("quality"));
                                Log.i("Dashboard Url", str_url);
                                if (str_quality.equals("1080p") || str_quality.equals("240p")) {
                                    url_array = progress_arr.getJSONObject(1);
                                    str_url = url_array.getString("url");
                                    str_quality = url_array.getString("quality");
                                    Log.i("Dashboard quality 240", url_array.getString("quality"));

                                    if (str_quality.equals("1080p") || str_quality.equals("240p")) {
                                        url_array = progress_arr.getJSONObject(2);
                                        str_url = url_array.getString("url");
                                        str_quality = url_array.getString("quality");
                                        Log.i("Dashboard quality 1080", url_array.getString("quality"));

                                        if (str_quality.equals("1080p") || str_quality.equals("240p")) {
                                            url_array = progress_arr.getJSONObject(3);
                                            str_url = url_array.getString("url");
                                            str_quality = url_array.getString("quality");
                                            Log.i("Dashboard quality 1080", url_array.getString("quality"));
                                            if (str_quality.equals("1080p") || str_quality.equals("240p")) {
                                                url_array = progress_arr.getJSONObject(4);
                                                str_url = url_array.getString("url");
                                                str_quality = url_array.getString("quality");
                                                Log.i("Dashboard quality 1080", url_array.getString("quality"));
                                                Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                                editor.putString("VIDEO_VIMEO", str_url);
                                                editor.putString("VIMEO_ID", video_id.get(i));
                                                editor.putString("FROM_DASHBOARD", "Value_base");
                                                editor.commit();
                                                view.getContext().startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                                editor.putString("VIDEO_VIMEO", str_url);
                                                editor.putString("VIMEO_ID", video_id.get(i));
                                                editor.putString("FROM_DASHBOARD", "Value_base");
                                                editor.commit();
                                                view.getContext().startActivity(intent);
                                            }
                                        } else {
                                            Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                            editor.putString("VIDEO_VIMEO", str_url);
                                            editor.putString("VIMEO_ID", video_id.get(i));
                                            editor.putString("FROM_DASHBOARD", "Value_base");
                                            editor.commit();
                                            view.getContext().startActivity(intent);
                                        }
                                    } else {
                                        Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                        editor.putString("VIDEO_VIMEO", str_url);
                                        editor.putString("VIMEO_ID", video_id.get(i));
                                        editor.putString("FROM_DASHBOARD", "Value_base");
                                        editor.commit();
                                        view.getContext().startActivity(intent);
                                    }
                                } else {
                                    Intent intent = new Intent(view.getContext(), VideoPlayerDemoVideoPlayer.class);
                                    editor.putString("VIDEO_VIMEO", str_url);
                                    editor.putString("VIMEO_ID", video_id.get(i));
                                    editor.putString("FROM_DASHBOARD", "Value_base");
                                    editor.commit();
                                    view.getContext().startActivity(intent);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Volley error:", error.toString());
                            Toast.makeText(view.getContext(), "Network error please try again", Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                    requestQueue.add(str);
                }
//                editor.putString("FESTIVAL_NAME",textArray.get(i));
//                editor.putString("FESTIVAL_DATE",video_date.get(i));
//                editor.putString("FESTIVAL_IMAGE",imageArray.get(i));
//                editor.commit();
            }
        });

        Glide.with(viewHolder.session_image_new.getContext())
                .asBitmap()
                .load("https://www.vedictreeschool.com/uploads/craftbanner/"+sessionImage.get(i))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        viewHolder.session_image_new.setImageBitmap(bitmap);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return sessionName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView mSessionImage;
//        TextView mSessionNameText;
//        LinearLayout back_ll;
//        LinearLayout back_ll_two;
        ImageView session_image_new;
        ViewHolder(View itemView) {
            super(itemView);
//            mSessionNameText=itemView.findViewById(R.id.session_name_text);
//            mSessionImage=itemView.findViewById(R.id.session_image_view);
//            back_ll=itemView.findViewById(R.id.session_name_ll);
//            back_ll_two=itemView.findViewById(R.id.session_name_ll_two);
            session_image_new=itemView.findViewById(R.id.activity_new_sesion_iv);
        }
    }
}