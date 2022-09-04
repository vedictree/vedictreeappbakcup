package com.vedictree.preschool.Utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RequiredFieldUtils {

    public static boolean isEditTextEmpty(EditText editText){
        if(editText == null){
            return true;
        }
        if(TextUtils.isEmpty(editText.getText())){
            return true;
        }
        return false;
    }

    public  static boolean isRequiredFieldEmpty(ArrayList<EditText> editTextArrayList) {
        boolean isRequiredFielEmpty = false;

        for(EditText editText : editTextArrayList){
            if (isEditTextEmpty(editText)) {
                editText.setError("required Field");
                isRequiredFielEmpty = true;
            }else {
                editText.setError(null);
            }
        }

        return isRequiredFielEmpty;
    }
    public  static boolean isRequiredFieldSetEmpty(ArrayList<EditText> editTextArrayList) {
        boolean isRequiredFielEmpty = false;

        for(EditText editText : editTextArrayList){
            if (isEditTextEmpty(editText)) {
                editText.setText("  ");
                isRequiredFielEmpty = true;
            }else {
                editText.setError(null);
            }
        }

        return isRequiredFielEmpty;
    }

    public static boolean isRequiredSpinner(ArrayList<String> spinnerArrayDefaultValueList, ArrayList<Spinner> spinnerArrayList) {
        boolean isRequiredFielEmpty = false;
        int itemIndex = 0;
        for(Spinner spinnerItem : spinnerArrayList){
            itemIndex ++;
            TextView textView = (TextView) spinnerItem.getSelectedView();
            if(spinnerItem.getVisibility() == View.VISIBLE) {
                String spinnerSelectedValue = textView.getText().toString();
                if (spinnerSelectedValue.equalsIgnoreCase(spinnerArrayDefaultValueList.get(itemIndex-1))) {
                    textView.setError("Error message");
                    isRequiredFielEmpty = true;
                } else {
                    textView.setError(null);
                }
            } else {
               /* if(textView != null) {
                    textView.setError(null);
                }*/
            }
        }
        return isRequiredFielEmpty;
    }

}
