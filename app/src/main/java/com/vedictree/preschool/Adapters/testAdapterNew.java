package com.vedictree.preschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vedictree.preschool.DownloadHomeworkNew;
import com.vedictree.preschool.POJO.RemarkPojo;
import com.vedictree.preschool.POJO.RemarkResponse;
import com.vedictree.preschool.R;
import com.vedictree.preschool.UploadTestNew;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class testAdapterNew extends RecyclerView.Adapter <testAdapterNew.ViewHolder>{
    private ArrayList<String> testDate;
    private ArrayList<String> testText;
    private ArrayList<String> testDownload;
    private ArrayList<String> homework_id_array;
    private ArrayList<String> class_array;
    private ArrayList<String> fees_array;
    private ArrayList<Integer> bg_arr;

    private Context context;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    View v ;

    public testAdapterNew(Context context, ArrayList<String> testDate, ArrayList<String> testText, ArrayList<String> testDownload, ArrayList<String> homework_id_array, ArrayList<String> fees_array_id, ArrayList<String> class_id_array) {
        super();
        this.context = context;
        this.testDate=testDate;
        this.testText=testText;
        this.testDownload=testDownload;
        this.homework_id_array=homework_id_array;
        this.class_array=class_id_array;
        this.fees_array=fees_array_id;


        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            v= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.test_item_new, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        int num=i + 1 ;

//        viewHolder.mIndex.setText(String.valueOf(num));
        viewHolder.mDateTest.setText("Date:-"+ testDate.get(i));
        viewHolder.mTestDetail.setText(testText.get(i));
        String str_image = testDownload.get(i);

        str_image = str_image.substring(1, str_image.length() - 1);
        str_image = str_image.replaceAll("\\s", "");
        String[] strSplit = str_image.split(",");

        ArrayList<String> strList = new ArrayList<String>(
                Arrays.asList(strSplit));
        for (int j=0;j<strList.size();j++){
            Log.i("Download Test", String.valueOf(strList.get(j)));

        }
        viewHolder.mDownload.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String download_str=gson.toJson(strList);
                editor.putString("DOWNLOAD_IMAGE",download_str);
                editor.putString("DOWNLOAD_Text",testText.get(i));
                editor.commit();

                Intent intent = new Intent(view.getContext(), DownloadHomeworkNew.class);
                view.getContext().startActivity(intent);


            }
        });

        viewHolder.mSubmitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UploadTestNew.class);
                editor.putString("START_TIME",testDate.get(i));
                editor.putString("HOMEWORK_ID",homework_id_array.get(i));
                editor.putString("HOME_WORK_TITLE",testText.get(i));
                editor.putString("FEES_ID",fees_array.get(i));
                editor.putString("CLASS_ID",class_array.get(i));

                editor.commit();
                view.getContext().startActivity(intent);
            }
        });

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<RemarkPojo> call = service.homewrokRemark(homework_id_array.get(i));
        call.enqueue(new Callback<RemarkPojo>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<RemarkPojo> call, Response<RemarkPojo> response) {
                if (response.body() != null) {
                    RemarkPojo remarkPojo = response.body();
                    List<RemarkResponse> remarkResponseList = remarkPojo.getRes();
                    if (!remarkResponseList.isEmpty()){
                    if (remarkResponseList.get(0).getRemark().equals("")) {

                    } else {
                        viewHolder.mRemark.setText(remarkResponseList.get(0).getRemark());
                    }
                }
                } else {

                }
            }

            @Override
            public void onFailure(Call<RemarkPojo> call, Throwable t) {
            }
        });
//        viewHolder.ll.setBackgroundResource(bg_arr.get(i));

    }

    @Override
    public int getItemCount() {
        return testText.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
//        TextView mIndex;
        TextView mDateTest;
        TextView mTestDetail;
        ImageView mDownload;
        CardView test_ll;
        ImageView mSubmitTest;
        TextView mRemark;
        RoundedImageView imageView;
        LinearLayout ll;
        ViewHolder(View itemView) {
            super(itemView);
//            mIndex=itemView.findViewById(R.id.test_index_new);
            mDateTest=itemView.findViewById(R.id.test_date_new);
            mTestDetail=itemView.findViewById(R.id.test_text_new);
            mDownload=itemView.findViewById(R.id.test_download_new);
            test_ll=itemView.findViewById(R.id.test_layout_new);
            ll=itemView.findViewById(R.id.home_item_bg);
            mSubmitTest=itemView.findViewById(R.id.add_test_new);
            mRemark=itemView.findViewById(R.id.homework_remark_new);
        }
    }
}