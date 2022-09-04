package com.vedictree.preschool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class video_call extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        Button msg_btn=findViewById(R.id.wa_btn);

        msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+"9970578516";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        Button download=findViewById(R.id.video_download);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4","Hindi.mp4");
            }
        });
    }

    private void downloadFile(String fileURL, String fileName) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String rootDir = Environment.getExternalStorageDirectory()
                            + File.separator + "Video";
                    File rootFile = new File(rootDir);
                    rootFile.mkdir();
                    URL url = new URL(fileURL);
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    c.setRequestMethod("GET");
                    c.setDoOutput(true);
                    c.connect();
                    FileOutputStream f = new FileOutputStream(new File(rootFile,
                            fileName));
                    InputStream in = c.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = in.read(buffer)) > 0) {
                        f.write(buffer, 0, len1);
                    }
                    f.close();
                 } catch (IOException e) {
                    Log.d("Error....", e.toString());
                }
            }
        });

        thread.start();

    }
}