package com.vedictree.preschool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PdfView extends AppCompatActivity {
    private Button btn, btnScroll;
    private LinearLayout llPdf;
    private Bitmap bitmap;
    SharedPreferences preferences;
    String course_name;
    String course_pdf;
    String pkg_name;
    TextView pdf_name;
    WebView webView;
    String pdfurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        course_name=preferences.getString("COURSE_NAME","");
        course_pdf=preferences.getString("PDF_NAME","");
        pkg_name=preferences.getString("PACKAGE_NAME","");
        pdf_name=findViewById(R.id.pdf_name);
        webView = (WebView) findViewById(R.id.pdf_web_view);
        webView.getSettings().setJavaScriptEnabled(true);


        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.M) {
            webView.setInitialScale(50);
            Log.i("50","50");
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            webView.setInitialScale(150);
            Log.i("100", "100");

        }else {
            webView.setInitialScale(100);
        }
        if (pkg_name.equals("Twink"))
        {
            if (course_name.equals("Nursery")){
                pdf_name.setText("Nursery Twinkling Star");
//                 pdfurl = "";
//                Log.i("Url",pdfurl);
                webView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/01_Nursery_Twinkling_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onReceivedError(final WebView view, int errorCode, String description,
                                                final String failingUrl) {
                        //control you layout, show something like a retry button, and
                        //call view.loadUrl(failingUrl) to reload.
                        Log.i("Error is",description);
                        super.onReceivedError(view, errorCode, description, failingUrl);
                    }
                });

            }else if (course_name.equals("Junior")){
                pdf_name.setText("Junior KG Twinkling Star");
//                 pdfurl = "";
//                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/02_Junior_Twinkling_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }else if (course_name.equals("Senior")){
                pdf_name.setText("Senior KG Twinkling Star");
//                 pdfurl = "";
//                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/03_Senior_Twinkling_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }
        }
        else  if (pkg_name.equals("SHIN"))
        {
            if (course_name.equals("Nursery")){
                pdf_name.setText("Nursery Shining Star");
                 pdfurl = "";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/01_Nursery_Shining_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }else if (course_name.equals("Junior")){
                pdf_name.setText("Junior KG Shining Star");
                 pdfurl = "";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/02_Junior_Shining_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }else if (course_name.equals("Senior")) {
                pdf_name.setText("Senior KG Shining Star");
                String pdfurl = "";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/03_Senior_Shining_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }
        }
        else  if (pkg_name.equals("GOLD"))
        {
            if (course_name.equals("Nursery")){
                pdf_name.setText("Nursery Golden Star");
                String pdfurl = "";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/01_Nursery_Golden_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }else if (course_name.equals("Junior")){
                pdf_name.setText("Junior KG Golden Star");
                 pdfurl = "";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://vedictreeschool.com/uploads/webpcoursePdf/02__Junior_KG_Golden_Star.pdf");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }else if (course_name.equals("Senior")){
                pdf_name.setText("Senior KG Golden Star");
                 pdfurl = "";
                Log.i("Url",pdfurl);
                webView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.vedictreeschool.com/uploads/webpcoursePdf/Senior_KG_Golden_Star.pdf");
//                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfurl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }

        }
        else  if (pkg_name.equals("ROCK"))
        {
            if (course_name.equals("Nursery")){
                pdf_name.setText("Nursery Rock Star");
                 pdfurl = "https://vedictreeschool.com/uploads/webpcoursePdf/01_Nursery_Rock_Star.pdf";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfurl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }else if (course_name.equals("Junior")){
                pdf_name.setText("Junior KG Rock Star");
                pdfurl = "https://vedictreeschool.com/uploads/webpcoursePdf/02_Junior_Rock_Star_Star.pdf";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfurl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });


            }else if (course_name.equals("Senior")){
                pdf_name.setText("Senior KG Rock Star");
                pdfurl = "https://vedictreeschool.com/uploads/webpcoursePdf/03_Senior_Rock_Star.pdf";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfurl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }

        }
        else  if (pkg_name.equals("SUPER"))
        {
            if (course_name.equals("Nursery")){
                pdf_name.setText("Nursery Super Star");
                pdfurl = "https://vedictreeschool.com/uploads/webpcoursePdf/01_Nursery_Super_Star.pdf";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfurl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }else if (course_name.equals("Junior")){
                pdf_name.setText("Junior KG Super Star");
                pdfurl = "https://vedictreeschool.com/uploads/webpcoursePdf/02_Junior_Super_Star.pdf";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfurl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

            }else if (course_name.equals("Senior")){
                pdf_name.setText("Senior KG Super Star");
                pdfurl = "https://vedictreeschool.com/uploads/webpcoursePdf/03_Senior_Super_Star.pdf";
                Log.i("Url",pdfurl);
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfurl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }
        }
        else if (pkg_name.equals("Test")){
            String test_name=preferences.getString("TEST_NAME","");
            pdfurl=preferences.getString("TEST_URL","") ;
            pdf_name.setText(test_name);
            Log.i("Url",pdfurl);
            webView.loadUrl(pdfurl);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

        }



//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

//        btn = findViewById(R.id.btn);
//        btnScroll = findViewById(R.id.btnScroll);
//        llPdf = findViewById(R.id.llPdf);
//
//        btnScroll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),ParentPinActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("size"," "+llPdf.getWidth() +"  "+llPdf.getWidth());
//                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
//                createPdf();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isMyServiceRunning(MusicBackground.class)){
        }else {
            startService(new Intent(this,MusicBackground.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isApplicationGoingToBackground(this))
        {
            stopService(new Intent(this,MusicBackground.class));
        }else {

        }
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn){

        }else {
            stopService(new Intent(this,MusicBackground.class));
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isApplicationGoingToBackground(final Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                Log.i("App b","a3");
                return true;
            }

        }
        Log.i("App b","b3");
        return false;
    }

}