package com.vedictree.preschool.Adapters;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vedictree.preschool.ChildeDashboardNew;
import com.vedictree.preschool.R;

import java.io.File;
import java.util.ArrayList;


public class testDownloadAdapterNew extends RecyclerView.Adapter <testDownloadAdapterNew.ViewHolder>{
    private ArrayList<String> testDownload;
    private String title_str;

    private Context context;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    View v ;

    public testDownloadAdapterNew(Context context, ArrayList<String> testDownload, String title_str) {
        super();
        this.context = context;
        this.testDownload=testDownload;
        this.title_str=title_str;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            v= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.test_download_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        int num=i + 1 ;

        viewHolder.mDownload.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.CustomAlertDialog);
                ViewGroup viewGroup =view.findViewById(android.R.id.content);
                View dialog = LayoutInflater.from(view.getContext()).inflate(R.layout.progress_layout, viewGroup, false);
                builder.setView(dialog);
                AlertDialog alertDialog_onCreate= builder.create();
                alertDialog_onCreate.show();
                    Log.i("Url 1", testDownload.get(i));
                    String url_string = "http://www.vedictreeschool.com/teacher/uploads/multiple_pics_loading/" + testDownload.get(i);
                    Uri url = Uri.parse(url_string);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator +testDownload.get(i));
                Log.i("File ",file.toString());
                if (file.isFile()){

                    alertDialog_onCreate.dismiss();

                    Dialog alertDialog = new Dialog(view.getContext());
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(R.layout.homework_available);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button button=alertDialog.findViewById(R.id.no_home_session_button);
                    Button view_button=alertDialog.findViewById(R.id.view_home_session_button);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final ValueAnimator yes_button = ValueAnimator.ofFloat(1f, 1.05f);
                            yes_button.setDuration(300);
                            yes_button.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    button.setScaleX((Float) animation.getAnimatedValue());
                                    button.setScaleY((Float) animation.getAnimatedValue());
                                }
                            });
                            yes_button.setRepeatCount(1);
                            yes_button.setRepeatMode(ValueAnimator.REVERSE);
                            yes_button.start();
                            alertDialog.dismiss();

                        }
                    });
                    view_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MimeTypeMap map = MimeTypeMap.getSingleton();
                            String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                            String type = map.getMimeTypeFromExtension(ext);
                            if (type == null)
                                type = "*/*";
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri data = Uri.fromFile(file);
                            intent.setDataAndType(data, type);
                           view.getContext().startActivity(intent);
                        }
                    });
                    alertDialog.show();

                }else {

                    if (url.toString().contains(".pdf")) {

                        DownloadManager downloadmanager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(url);
                        request.setTitle(title_str);
                        request.setDescription("Downloading");
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setVisibleInDownloadsUi(false);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator + testDownload.get(i));
                        downloadmanager.enqueue(request);
                        alertDialog_onCreate.dismiss();
                        Toast.makeText(view.getContext(), "Download complete.Check Download folder", Toast.LENGTH_SHORT).show();
                    }
                    else if (url.toString().contains(".png") || url.toString().contains(".jpg") || url.toString().contains(".jpeg")) {
//                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + testDownload.get(i));
//                        if (file.isFile()) {
//                            Toast.makeText(view.getContext(), "File exist", Toast.LENGTH_SHORT).show();
//                            alertDialog_onCreate.dismiss();
//
//                        } else {
                            try {

                                String[] mimeTypes = {"image/png", "image/jpg", "image/jpeg"};

                                DownloadManager dm = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                DownloadManager.Request request = new DownloadManager.Request(url);
                                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                                        .setAllowedOverRoaming(false)
                                        .setVisibleInDownloadsUi(true)
                                        .setTitle(title_str)
                                        .setMimeType("image/jpg")
                                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator + testDownload.get(i));
                                dm.enqueue(request);
                                alertDialog_onCreate.dismiss();

                                Toast.makeText(view.getContext(), "Download complete.Check Download folder", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.i("Failure", String.valueOf(e));
                            }
//                        }
                    } else if (url.toString().contains(".zip")) {
                        DownloadManager downloadmanager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://androhub.com/demo/demo.zip"));
                        request.setTitle(title_str);
                        request.setDescription("Downloading");
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setVisibleInDownloadsUi(false);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator + testDownload.get(i));
                        downloadmanager.enqueue(request);
                        alertDialog_onCreate.dismiss();
                        Toast.makeText(view.getContext(), "Download complete.Check Download folder", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        String url_string_image = "http://www.vedictreeschool.com/teacher/uploads/multiple_pics_loading/" + testDownload.get(i);
        Log.i("Image ",url_string_image);
        Uri url2 = Uri.parse(url_string_image);
        if (url2.toString().contains(".pdf")) {
            viewHolder.mViewImage.setBackgroundResource(R.drawable.pdf_image);
        }
        else if (url2.toString().contains(".png") || url2.toString().contains(".jpg") || url2.toString().contains(".jpeg")) {
            Glide.with(viewHolder.mViewImage.getContext()).load(url_string_image).into(viewHolder.mViewImage);
        }
        else if (url2.toString().contains(".zip")) {
            viewHolder.mViewImage.setBackgroundResource(R.drawable.zip_image);

        }
        else {
            viewHolder.mViewImage.setBackgroundResource(R.drawable.zip_image);
        }
        viewHolder.mTitle.setText(title_str);
    }


    @Override
    public int getItemCount() {
        return testDownload.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mDownload;
        ImageView mViewImage;
        TextView mTitle;

        ViewHolder(View itemView) {
            super(itemView);
            mDownload=itemView.findViewById(R.id.download_icon);
            mViewImage=itemView.findViewById(R.id.download_icon_image);
            mTitle=itemView.findViewById(R.id.download_image_name);
        }
    }
}