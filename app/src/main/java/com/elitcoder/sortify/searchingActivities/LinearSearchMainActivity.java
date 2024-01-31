package com.elitcoder.sortify.searchingActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elitcoder.sortify.R;
import com.elitcoder.sortify.databinding.ActivityLinearSearchMainBinding;

public class LinearSearchMainActivity extends AppCompatActivity {

    private ActivityLinearSearchMainBinding linearBinding;
    private int[] array;
    private int key;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearBinding = ActivityLinearSearchMainBinding.inflate(getLayoutInflater());
        View view = linearBinding.getRoot();
        setContentView(view);

        handler = new Handler(Looper.getMainLooper());

        // লিনিয়ার বাটনের ফাংশানঃ
        linearBinding.btnLinearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLinearSearch();
            }
        });


    }

    //লিনিয়ার সার্চ আর ভিজুয়ালাইজঃ
    private void performLinearSearch() {
        int size = Integer.parseInt(linearBinding.edtTxtSize.getText().toString().trim());

        //সাইজ টেস্টিংঃ
        if(size<=0){
            Toast.makeText(this, "Array size should be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }
        //ঐ সাইজের ইন্টিজার অ্যাঁরে ও টেক্সফিল্ডের অ্যাঁরে তৈরি যেন ডাইনামিক্যালি সো করাতে পারিঃ
        array = new int[size];
        TextView[] arrayTextViews = new TextView[size];
        //এরে সাইজ আর ইনপুট নেয়া এরের সাইজ চেকিংঃ
        String[] arrayElements = linearBinding.edtTxtArray.getText().toString().split(" ");
        if(arrayElements.length!=size){
            Toast.makeText(this,"Please enter "+size+" array elements", Toast.LENGTH_SHORT).show();
            return;
        }
        //ইনপুট এরে এলিমেন্টগুলো আসল এরেতে নিলামঃ
        for (int i = 0; i < size; i++){
            array[i] = Integer.parseInt(arrayElements[i].trim());
            //ডাইনামিক্যালি টেক্সভিউ তৈরিঃ
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(array[i]));
            //যেই লিনিয়ার লেয়াউটে টেক্সভিউগুলো রাখবো তার হাইট আর উডথ ঠিক করলামঃ
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    150,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(5,0,5,0);
            textView.setLayoutParams(layoutParams);
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(this,R.color.black));
            textView.setBackgroundResource(R.drawable.array_elemet_bg); // Set a background drawable
            arrayTextViews[i] = textView;

            linearBinding.arrayContainer.addView(textView);
        }

        //কি নিলামঃ
        key = Integer.parseInt(linearBinding.edtTxtKey.getText().toString().trim());
        //Linear search calling and compare with delay :
        linearBinding.txtResult.setText("");
        searchWithDelay(0,arrayTextViews);
    }

    private void searchWithDelay(final int position, final TextView[] arrayTextViews) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(position<array.length){
                    arrayTextViews[position].setBackgroundResource(R.drawable.highlight_element_bg);
                    if(array[position]==key){
                        arrayTextViews[position].setBackgroundResource(R.drawable.found_element_bg);
                        linearBinding.txtResult.setText("Element found at positon: "+ position);
                        linearBinding.txtResult.append("\nSearch completed!");
                        return;
                    }else{
                        linearBinding.txtResult.setText("Element not found at position: " + position);
                    }
                    searchWithDelay(position+1,arrayTextViews);
                }else{
                    linearBinding.txtResult.append("\nSearch completed!");
                }
            }
        },1500);
    }
}