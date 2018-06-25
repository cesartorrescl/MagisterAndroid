package com.magister.unab.predictormundialista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Graficas extends AppCompatActivity {


    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);


        loadFromFirebase();



    }

    private void loadFromFirebase(){
        DatabaseReference resultsDB = FirebaseDatabase.getInstance().getReference().child("resultados");

        resultsDB.addValueEventListener(new ValueEventListener() {
            //resultsDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot resultados) {
                mChart = findViewById(R.id.chart1);
                mChart.animateY(1400, Easing.EasingOption.EaseInBack);
                ArrayList<PieEntry> data = new ArrayList<PieEntry>();
                data.add(new PieEntry((float) 4,"Primer Lugar"));
                data.add(new PieEntry((float) 4,"Segundo Lugar"));
                data.add(new PieEntry((float) 3,"Tercer lugar"));
                PieDataSet pieDataSet = new PieDataSet(data, "");
                ArrayList<Integer> colors = new ArrayList<Integer>();

                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);

                colors.add(ColorTemplate.getHoloBlue());

                pieDataSet.setColors(colors);
                PieData dataP = new PieData(pieDataSet);

                mChart.setData(dataP);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }





}
