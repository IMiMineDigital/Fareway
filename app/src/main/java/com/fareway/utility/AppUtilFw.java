package com.fareway.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.fareway.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AppUtilFw {
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");


    public static final Pattern USER_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,20}$");
    Context context;
    static Context context1;

    public AppUtilFw(Context context)
    {
        this.context=context;
        context1=context;

    }
    public static boolean isEmailCorrect(String email)
    {

        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    /*
     * setting data in shared preferences
     */
    public void setPrefrence(String key, String value) {
        SharedPreferences prefrence = context.getSharedPreferences(
                context.getString(R.string.app_name), 0);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*
     * retreiving the data from shared preferences     */
    public String getPrefrence(String key) {
        SharedPreferences prefrence = context.getSharedPreferences(
                context.getString(R.string.app_name), 0);
        String data = prefrence.getString(key, "");
        return data;
    }

    public  void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

 /*  public ArrayAdapter<String> makeAdapter(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        adapter.notifyDataSetChanged();
        return adapter;
    }

    public ArrayAdapter<String> makeAdapter_small_font(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item_small, list);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        adapter.notifyDataSetChanged();
        return adapter;
    }*/

    public void deletePrefrence() {
        SharedPreferences prefrence = context.getSharedPreferences(
                context.getString(R.string.app_name), 0);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.clear();
        editor.commit();
    }
}
