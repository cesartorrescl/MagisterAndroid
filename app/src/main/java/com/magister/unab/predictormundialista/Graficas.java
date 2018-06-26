package com.magister.unab.predictormundialista;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graficas extends AppCompatActivity {


    private PieChart mChart;

    private String[][] resultadosP = new String[8][4];
    private int[][] puntos = new int[8][4];

    private int grupoN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);

        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"A", "B", "C","D","E","F","G","H"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                grupoN = i;
                loadFromFirebase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        agregarGrupos();

        loadFromFirebase();




    }

    private void loadFromFirebase(){
        DatabaseReference resultsDB = FirebaseDatabase.getInstance().getReference().child("resultados");

        resultsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot resultados) {
                for (DataSnapshot respuesta: resultados.getChildren()) {
                    for(DataSnapshot grupo: respuesta.getChildren()){
                        if (grupo.getKey().equalsIgnoreCase(String.valueOf(grupoN)))
                        for(DataSnapshot equipo : grupo.getChildren()){
                                if(equipo.getKey().equalsIgnoreCase("1"))
                                agregarPuntos((String)equipo.getValue(),grupoN);
                        }
                    }
                }

                mChart = findViewById(R.id.chart1);
                mChart.getDescription().setEnabled(false);
                mChart.setUsePercentValues(true);
                mChart.setCenterText(generateCenterSpannableText());
                Legend l = mChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setDrawInside(false);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(0f);
                l.setYOffset(0f);
                mChart.animateY(1400, Easing.EasingOption.EaseInBack);
                ArrayList<PieEntry> data = new ArrayList<PieEntry>();
                data.add(new PieEntry((float) puntos[grupoN][0],resultadosP[grupoN][0]));
                data.add(new PieEntry((float) puntos[grupoN][1],resultadosP[grupoN][1]));
                data.add(new PieEntry((float) puntos[grupoN][2],resultadosP[grupoN][2]));
                data.add(new PieEntry((float) puntos[grupoN][3],resultadosP[grupoN][3]));
                PieDataSet pieDataSet = new PieDataSet(data, "Países");
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


    public void agregarPuntos(String pais, int grupoN){

        for (int i = 0; i<4; i++){
            if (resultadosP[grupoN][i].equalsIgnoreCase(pais)){

                puntos[grupoN][i] =  puntos[grupoN][i] +1;
            }
        }
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Porcentajes\nsegundo lugar en su grupo");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 11, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 11, s.length() - 12, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 11, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(.8f), 11, s.length(), 0);
        return s;
    }


    public void agregarGrupos(){
        resultadosP[0][0] = "Rusia";
        resultadosP[0][1] = "Egipto";
        resultadosP[0][2] = "Arabia";
        resultadosP[0][3] = "Uruguay";
        resultadosP[1][0] = "Portugal";
        resultadosP[1][1] = "España";
        resultadosP[1][2] = "Marruecos";
        resultadosP[1][3] = "Iran";
        resultadosP[2][0] = "Francia";
        resultadosP[2][1] = "Australia";
        resultadosP[2][2] = "Dinamarca";
        resultadosP[2][3] = "Perú";
        resultadosP[3][0] = "Argentina";
        resultadosP[3][1] = "Islandia";
        resultadosP[3][2] = "Croacia";
        resultadosP[3][3] = "Nigeria";
        resultadosP[4][0] = "Brasil";
        resultadosP[4][1] = "Suiza";
        resultadosP[4][2] = "Costa Rica";
        resultadosP[4][3] = "Serbia";
        resultadosP[5][0] = "Alemania";
        resultadosP[5][1] = "México";
        resultadosP[5][2] = "Suecia";
        resultadosP[5][3] = "Corea del Sur";
        resultadosP[6][0] = "Bélgica";
        resultadosP[6][1] = "Panamá";
        resultadosP[6][2] = "Túnez";
        resultadosP[6][3] = "Inglaterra";
        resultadosP[7][0] = "Polonia";
        resultadosP[7][1] = "Senegal";
        resultadosP[7][2] = "Colombia";
        resultadosP[7][3] = "Japón";
    }





}
