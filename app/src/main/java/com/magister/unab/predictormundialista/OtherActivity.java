package com.magister.unab.predictormundialista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        String[][] arreglo = (String[][])getIntent().getExtras().get("arreglo");
        for(String[] ar: arreglo){
            for (String a: ar){
                System.out.println(a);
            }
        }
    }
}
