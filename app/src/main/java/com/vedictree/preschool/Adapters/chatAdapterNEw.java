package com.vedictree.preschool.Adapters;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.gson.JsonObject;
import com.vedictree.preschool.R;
import com.vedictree.preschool.Utils.APIInterface;
//import com.vedictree.preschool.Utils.RetrofitSignletonNew;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chatAdapterNEw extends RecyclerView.Adapter <chatAdapterNEw.ViewHolder>{
    private ArrayList<String> chat_side;
    private ArrayList<String>  chat_msg;
    private ArrayList<String>  chat_time;
    private ArrayList<Integer>  read_unread_status;
    private ArrayList<String>  chat_text_attachment;
//    private ArrayList<String>  chat_time;
    private Context context;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    View v;
    DownloadManager manager;
    AsyncTask mMyTask;

    public chatAdapterNEw(Context context, ArrayList<String> chat_side, ArrayList<String> chat_msg,ArrayList<String> chat_time,ArrayList<String> chat_text_attachment,ArrayList<Integer> read_unread_status) {
        super();
        this.context = context;
        this.chat_side=chat_side;
        this.chat_msg=chat_msg;
        this.chat_time=chat_time;
        this.read_unread_status=read_unread_status;
        this.chat_text_attachment=chat_text_attachment;
         preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            v= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.right_left_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        Log.i("Side is ",chat_side.get(i));
        String stud_id=preferences.getString("STUDENT_ID","");
        if (chat_side.get(i).equals("Student")){
            viewHolder.left_ll.setVisibility(View.GONE);
            viewHolder.right_ll.setVisibility(View.VISIBLE);
            if (chat_text_attachment.get(i).equals("Text")){
                viewHolder.right_download.setVisibility(View.GONE);
                viewHolder.right_attachment.setVisibility(View.GONE);
                viewHolder.msg_text_right.setVisibility(View.VISIBLE);
                viewHolder.msg_text_right.setText(chat_msg.get(i));

                String str=chat_msg.get(i);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("rlid",12);
                jsonObject.addProperty("studid",420);
                jsonObject.addProperty("chtimg",str);

//                APIInterface service2 = RetrofitSignletonNew.getAPIInterface();
//                Call<ResponseBody> call2 = service2.getChatImage(jsonObject);
//                Log.i("PROFILE", String.valueOf(call2.request().url()));
//
//                call2.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(@NotNull Call<ResponseBody> call2, @NotNull Response<ResponseBody> response) {
//                        if (response.body() != null) {
//                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
//                            viewHolder.right_attachment.setImageBitmap(bmp);
//
//                            int count;
//                            byte[] data = new byte[1024 * 4];
//                            long fileSize = response.body().contentLength();
//                            InputStream inputStream = new BufferedInputStream(response.body().byteStream(), 1024 * 8);
//                            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), str);
//                            OutputStream outputStream = null;
//                            try {
//                                outputStream = new FileOutputStream(outputFile);
//                                long total = 0;
//                                boolean downloadComplete = false;
//                                while ((count = inputStream.read(data)) != -1) {
//
//                                    total += count;
//                                    int progress = (int) ((double) (total * 100) / (double) fileSize);
//
//
//                                    outputStream.write(data, 0, count);
//                                    downloadComplete = true;
//                                    Log.i("downloadComplete", String.valueOf(downloadComplete));
//                                }
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<ResponseBody> call2, @NotNull Throwable t) {
//                        Log.i("Error", String.valueOf(t.getMessage()));
//                    }
//                });


            }
            else {
                viewHolder.right_download.setVisibility(View.VISIBLE);
                viewHolder.right_attachment.setVisibility(View.VISIBLE);
                viewHolder.msg_text_right.setVisibility(View.GONE);
                String str=chat_msg.get(i);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("rlid",12);
                jsonObject.addProperty("studid",420);
                jsonObject.addProperty("chtimg",str);
//
//                APIInterface service2 = RetrofitSignletonNew.getAPIInterface();
//                Call<ResponseBody> call2 = service2.getChatImage(jsonObject);
//                Log.i("PROFILE", String.valueOf(call2.request().url()));
//
//                call2.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(@NotNull Call<ResponseBody> call2, @NotNull Response<ResponseBody> response) {
//                        if (response.body() != null) {
//                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
//                            viewHolder.right_attachment.setImageBitmap(bmp);
//
//                            int count;
//                            byte[] data = new byte[1024 * 4];
//                            long fileSize = response.body().contentLength();
//                            InputStream inputStream = new BufferedInputStream(response.body().byteStream(), 1024 * 8);
//                            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), str);
//                            OutputStream outputStream = null;
//                            try {
//                                outputStream = new FileOutputStream(outputFile);
//                                long total = 0;
//                                boolean downloadComplete = false;
//                                while ((count = inputStream.read(data)) != -1) {
//
//                                    total += count;
//                                    int progress = (int) ((double) (total * 100) / (double) fileSize);
//
//
//                                    outputStream.write(data, 0, count);
//                                    downloadComplete = true;
//                                    Log.i("downloadComplete", String.valueOf(downloadComplete));
//                                }
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<ResponseBody> call2, @NotNull Throwable t) {
//                        Log.i("Error", String.valueOf(t.getMessage()));
//                    }
//                });

//                Glide.with(viewHolder.left_attachment.getContext()).load("https://www.vedictreeschool.com/teacher/uploads/chatimgup/junfu.png").into(viewHolder.left_attachment);
//                Glide.with(viewHolder.right_attachment.getContext()).load("https://www.vedictreeschool.com/uploads/chatimgup/"+str).into(viewHolder.right_attachment);


            }
            viewHolder.time_text_right.setText(chat_time.get(i));

        }
        else {
            viewHolder.left_ll.setVisibility(View.VISIBLE);
            viewHolder.right_ll.setVisibility(View.GONE);
            viewHolder.time_text_left.setText(chat_time.get(i));
            if (read_unread_status.get(i).equals("0")) {
                viewHolder.time_text_left.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_uncheck_circle_outline_24,0);
            }else {
                viewHolder.time_text_left.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_check_circle_outline_24,0);

            }
            if (chat_text_attachment.get(i).equals("Text")){
                viewHolder.left_download.setVisibility(View.GONE);
                viewHolder.left_attachment.setVisibility(View.GONE);
                viewHolder.msg_text_left.setVisibility(View.VISIBLE);
                viewHolder.msg_text_left.setText(chat_msg.get(i));
            }
            else {
                viewHolder.left_download.setVisibility(View.VISIBLE);
                viewHolder.left_attachment.setVisibility(View.VISIBLE);
                viewHolder.msg_text_left.setVisibility(View.GONE);
                String str=chat_msg.get(i);
                Glide.with(viewHolder.left_attachment.getContext()).load("https://www.vedictreeschool.com/teacher/uploads/chatimgup/"+str).into(viewHolder.left_attachment);
            }
        }
        viewHolder.left_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=chat_msg.get(i);
//                String url ="https://www.vedictreeschool.com/teacher/uploads/chatimgup/"+str;
//                Log.i("Url is",url);
//                new DownloadsImage().execute(url);

//                try{
//                    DownloadManager dm = (DownloadManager)view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                    Uri downloadUri = Uri.parse(url);
//                    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
//                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
//                            .setAllowedOverRoaming(false)
//                            .setVisibleInDownloadsUi(true)
//                            .setTitle(str)
//                            .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
//                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,File.separator + str );
//                    dm.enqueue(request);
//                    Toast.makeText(view.getContext(), "Image download started.", Toast.LENGTH_SHORT).show();
//                    BroadcastReceiver onComplete = new BroadcastReceiver() {
//
//                        public void onReceive(Context ctxt, Intent intent) {
//                            Toast.makeText(view.getContext(), "Download complete.", Toast.LENGTH_SHORT).show();
//                        }
//                    };
//                    context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//                }catch (Exception e){
//                    Toast.makeText(view.getContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
//                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("rlid",12);
                jsonObject.addProperty("studid",420);
                jsonObject.addProperty("chtimg",str);

//                APIInterface service2 = RetrofitSignletonNew.getAPIInterface();
//                Call<ResponseBody> call2 = service2.getChatImage(jsonObject);
//                Log.i("PROFILE", String.valueOf(call2.request().url()));
//
//                call2.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(@NotNull Call<ResponseBody> call2, @NotNull Response<ResponseBody> response) {
//                        if (response.body() != null) {
////                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
////                            viewHolder.right_attachment.setImageBitmap(bmp);
//
//                            int count;
//                            byte[] data = new byte[1024 * 4];
//                            long fileSize = response.body().contentLength();
//                            InputStream inputStream = new BufferedInputStream(response.body().byteStream(), 1024 * 8);
//                            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), str);
//                            OutputStream outputStream = null;
//                            try {
//                                outputStream = new FileOutputStream(outputFile);
//                                long total = 0;
//                                boolean downloadComplete = false;
//                                while ((count = inputStream.read(data)) != -1) {
//
//                                    total += count;
//                                    int progress = (int) ((double) (total * 100) / (double) fileSize);
//                                    outputStream.write(data, 0, count);
//                                    downloadComplete = true;
//                                    Log.i("downloadComplete", String.valueOf(downloadComplete));
//                                    Toast.makeText(view.getContext(), "Download complete.", Toast.LENGTH_SHORT).show();
//
//                                }
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                                Toast.makeText(view.getContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                Toast.makeText(view.getContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<ResponseBody> call2, @NotNull Throwable t) {
//                        Log.i("Error", String.valueOf(t.getMessage()));
//                    }
//                });

            }

        });
        viewHolder.right_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=chat_msg.get(i);
//                String url ="https://www.vedictreeschool.com/uploads/chatimgup/"+str;
//
//                try{
//                    DownloadManager dm = (DownloadManager)view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                    Uri downloadUri = Uri.parse(url);
//                    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
//                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
//                            .setAllowedOverRoaming(false)
//                            .setVisibleInDownloadsUi(true)
//                            .setTitle(str)
//                            .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
//                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,File.separator + str );
//                    dm.enqueue(request);
//                    Toast.makeText(view.getContext(), "Image download started.", Toast.LENGTH_SHORT).show();
//                    BroadcastReceiver onComplete = new BroadcastReceiver() {
//
//                        public void onReceive(Context ctxt, Intent intent) {
//                            Toast.makeText(view.getContext(), "Download complete.", Toast.LENGTH_SHORT).show();
//                        }
//                    };
//                    context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//                }catch (Exception e){
//                    Toast.makeText(view.getContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
//                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("rlid",12);
                jsonObject.addProperty("studid",420);
                jsonObject.addProperty("chtimg",str);

//                APIInterface service2 = RetrofitSignletonNew.getAPIInterface();
//                Call<ResponseBody> call2 = service2.getChatImage(jsonObject);
//                Log.i("PROFILE", String.valueOf(call2.request().url()));
//
//                call2.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(@NotNull Call<ResponseBody> call2, @NotNull Response<ResponseBody> response) {
//                        if (response.body() != null) {
////                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
////                            viewHolder.right_attachment.setImageBitmap(bmp);
//
//                            int count;
//                            byte[] data = new byte[1024 * 4];
//                            long fileSize = response.body().contentLength();
//                            InputStream inputStream = new BufferedInputStream(response.body().byteStream(), 1024 * 8);
//                            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), str);
//                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator +str);
//                            if (file.isFile()){
////                                Toast.makeText(view.getContext(), "File exists.", Toast.LENGTH_SHORT).show();
//
//                                Dialog alertDialog = new Dialog(view.getContext());
//                                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                alertDialog.setContentView(R.layout.chat_image_available);
//                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                Button button=alertDialog.findViewById(R.id.no_chat_session_button);
//
//                                button.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//
//                                        final ValueAnimator yes_button = ValueAnimator.ofFloat(1f, 1.05f);
//                                        yes_button.setDuration(300);
//                                        yes_button.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                            @Override
//                                            public void onAnimationUpdate(ValueAnimator animation) {
//                                                button.setScaleX((Float) animation.getAnimatedValue());
//                                                button.setScaleY((Float) animation.getAnimatedValue());
//                                            }
//                                        });
//                                        yes_button.setRepeatCount(1);
//                                        yes_button.setRepeatMode(ValueAnimator.REVERSE);
//                                        yes_button.start();
//                                        alertDialog.dismiss();
//
//                                    }
//                                });
//                                alertDialog.show();
//                            }else {
//                                OutputStream outputStream = null;
//                                try {
//                                    outputStream = new FileOutputStream(outputFile);
//                                    long total = 0;
//                                    boolean downloadComplete = false;
//                                    while ((count = inputStream.read(data)) != -1) {
//
//                                        total += count;
//                                        int progress = (int) ((double) (total * 100) / (double) fileSize);
//                                        outputStream.write(data, 0, count);
//                                        downloadComplete = true;
//                                        Log.i("downloadComplete", String.valueOf(downloadComplete));
//                                        Toast.makeText(view.getContext(), "Download complete.", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                    Toast.makeText(view.getContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                    Toast.makeText(view.getContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<ResponseBody> call2, @NotNull Throwable t) {
//                        Log.i("Error", String.valueOf(t.getMessage()));
//                    }
//                });


            }

        });



    }

    @Override
    public int getItemCount() {
        return chat_msg.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView msg_text_left;
        TextView time_text_left;
        LinearLayout left_ll;
        LinearLayout right_ll;
        TextView msg_text_right;
        TextView time_text_right;
        ImageView left_download;
        ImageView right_download;
        ImageView left_attachment;
        ImageView right_attachment;

        ViewHolder(View itemView) {
            super(itemView);
            msg_text_left = itemView.findViewById(R.id.msgr_left);
            time_text_left = itemView.findViewById(R.id.chat_day_text_left);
            msg_text_right = itemView.findViewById(R.id.msgr_right);
            time_text_right = itemView.findViewById(R.id.chat_day_text_right);
            left_ll=itemView.findViewById(R.id.left_layout);
            right_ll=itemView.findViewById(R.id.righ_layout);
            left_download=itemView.findViewById(R.id.left_download);
            right_download=itemView.findViewById(R.id.right_download);
            left_attachment=itemView.findViewById(R.id.left_upload_image);
            right_attachment=itemView.findViewById(R.id.right_upload_image);

        }
    }

}