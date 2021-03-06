package com.bikash.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddMemo extends AppCompatActivity {

    EditText et1;
    EditText et2;
    SharedPreferences sharedPreferences;

    int key;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);
        sharedPreferences = this.getSharedPreferences("com.bikash.moneymanager", Context.MODE_PRIVATE);

        et1 = (EditText) findViewById(R.id.descriptionTextView);
        et2 = (EditText) findViewById(R.id.spentAmountTextView);

        Intent i = getIntent();
        int k = i.getIntExtra("Id", -1);
        key = k%10;
        index = k/10-1;
        Log.i("Oncreate", String.valueOf(key));

        if(index>=0){
            if(key==1){
                et1.setText(MainActivity.incomeArrayList.get(index).getMemoName());
                et2.setText(MainActivity.incomeArrayList.get(index).getCost().toString());
            } else if(key==2){
                et1.setText(MainActivity.spentArrayList.get(index).getMemoName());
                et2.setText(MainActivity.spentArrayList.get(index).getCost().toString());
            }
        }
    }

    public void SaveButton(View view){
        Toast.makeText(getBaseContext(), "Saved" , Toast.LENGTH_SHORT).show();


        String str1 = et1.getText().toString();
        String str2 = et2.getText().toString();

        if(str1.equals("")){
            str1 = " ";
        }

        if(str2.equals("")){
            str2 = "0.0";
        }

        Log.i("String", str1);
        Log.i("String", str2);

        Log.i("String", String.valueOf(key));
        Log.i("String", String.valueOf(index));

        if(key == 1){

            Double l = Double.valueOf(str2);

            Memo m = new Memo(str1, l);

            if(index<0){
                MainActivity.incomeArrayList.add(m);

                MainActivity.remaining +=l;
                sharedPreferences.edit().putString("remaining", MainActivity.remaining.toString()).apply();
            }

            else {

                double diff = l - MainActivity.incomeArrayList.get(index).getCost();
                Log.d("string_", Double.toString(diff));

                MainActivity.remaining += diff;

                MainActivity.incomeArrayList.set(index, m);
                sharedPreferences.edit().putString("remaining", MainActivity.remaining.toString()).apply();
            }

        }


        if(key == 2){

            Double l = Double.valueOf(str2);

            Memo m = new Memo(str1, l);

            if(index<0){
                MainActivity.spentArrayList.add(m);

                MainActivity.remaining -=l;
                sharedPreferences.edit().putString("remaining", MainActivity.remaining.toString()).apply();
            } else {

                double diff = l - MainActivity.spentArrayList.get(index).getCost();
                Log.d("string_", Double.toString(diff));

                MainActivity.remaining -= diff;

                MainActivity.spentArrayList.set(index, m);
                sharedPreferences.edit().putString("remaining", MainActivity.remaining.toString()).apply();
            }

        }



        finish();
    }
}
