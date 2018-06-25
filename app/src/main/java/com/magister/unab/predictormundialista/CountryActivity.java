package com.magister.unab.predictormundialista;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private boolean[] ganadorGroupoLista = new boolean[8];
    private String [][] ganadoresLista = new String[8][2];
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_country);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        context = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        /*
        String[][] test = new String[2][2];
        test[0][0] = "Hola1";
        test[1][0] = "Hola2";
        test[0][1] = "Hola3";
        test[1][1] = "Hola4";
        Intent i = new Intent(CountryActivity.this, OtherActivity.class);
        i.putExtra("arreglo",test);
        startActivity(i);
        */

        loadFromFirebase();
    }

    private TextView[] getTextViews(int posicionGrupo){
        TextView[] textViews = new TextView[2];
        switch(posicionGrupo){
            case 0:
                textViews[0] = findViewById(R.id.primero_a);
                textViews[1] = findViewById(R.id.segundo_a);
                break;
            case 1:
                textViews[0] = findViewById(R.id.primero_b);
                textViews[1] = findViewById(R.id.segundo_b);
                break;
            case 2:
                textViews[0] = findViewById(R.id.primero_c);
                textViews[1] = findViewById(R.id.segundo_c);
                break;
            case 3:
                textViews[0] = findViewById(R.id.primero_d);
                textViews[1] = findViewById(R.id.segundo_d);
                break;
            case 4:
                textViews[0] = findViewById(R.id.primero_e);
                textViews[1] = findViewById(R.id.segundo_e);
                break;
            case 5:
                textViews[0] = findViewById(R.id.primero_f);
                textViews[1] = findViewById(R.id.segundo_f);
                break;
            case 6:
                textViews[0] = findViewById(R.id.primero_g);
                textViews[1] = findViewById(R.id.segundo_g);
                break;
            case 7:
                textViews[0] = findViewById(R.id.primero_h);
                textViews[1] = findViewById(R.id.segundo_h);
                break;
        }
        return textViews;
    }

    public void sendValue(View view){
        ImageButton boton = (ImageButton) view;
        View layout = (View) boton.getParent();
        String grupo = layout.getTag().toString();
        int posicionGroupo = grupo.charAt(0)-'a';
        int posicion = ganadorGroupoLista[posicionGroupo]?1:0;
        ganadorGroupoLista[posicionGroupo] = !ganadorGroupoLista[posicionGroupo];
        (getTextViews(posicionGroupo)[posicion]).setText(boton.getTag().toString());
        ganadoresLista[posicionGroupo][posicion] = boton.getTag().toString();
    }

    public void terminarPrediccion(View view){
        if(!validateDataToSend()){
            Toast.makeText(this, "Debes predecr TODOS los grupos", Toast.LENGTH_LONG).show();
            return;
        }
        sendDataToFirebase();
        Toast.makeText(this, "Enviado a firebase!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 9){
            return;
        }
        if(position == 8){
            setResults();
            return;
        }
        if(ganadoresLista[position][0] != null)
            getTextViews(position)[0].setText(ganadoresLista[position][0]);
        if(ganadoresLista[position][1] != null)
            getTextViews(position)[1].setText(ganadoresLista[position][1]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void setResults(){
        if(ganadoresLista[0][0] != null) ((TextView)findViewById(R.id.a1)).setText(ganadoresLista[0][0]);
        if(ganadoresLista[0][1] != null) ((TextView)findViewById(R.id.a2)).setText(ganadoresLista[0][1]);
        if(ganadoresLista[1][0] != null) ((TextView)findViewById(R.id.b1)).setText(ganadoresLista[1][0]);
        if(ganadoresLista[1][1] != null) ((TextView)findViewById(R.id.b2)).setText(ganadoresLista[1][1]);
        if(ganadoresLista[2][0] != null) ((TextView)findViewById(R.id.c1)).setText(ganadoresLista[2][0]);
        if(ganadoresLista[2][1] != null) ((TextView)findViewById(R.id.c2)).setText(ganadoresLista[2][1]);
        if(ganadoresLista[3][0] != null) ((TextView)findViewById(R.id.d1)).setText(ganadoresLista[3][0]);
        if(ganadoresLista[3][1] != null) ((TextView)findViewById(R.id.d2)).setText(ganadoresLista[3][1]);
        if(ganadoresLista[4][0] != null) ((TextView)findViewById(R.id.e1)).setText(ganadoresLista[4][0]);
        if(ganadoresLista[4][1] != null) ((TextView)findViewById(R.id.e2)).setText(ganadoresLista[4][1]);
        if(ganadoresLista[5][0] != null) ((TextView)findViewById(R.id.f1)).setText(ganadoresLista[5][0]);
        if(ganadoresLista[5][1] != null) ((TextView)findViewById(R.id.f2)).setText(ganadoresLista[5][1]);
        if(ganadoresLista[6][0] != null) ((TextView)findViewById(R.id.g1)).setText(ganadoresLista[6][0]);
        if(ganadoresLista[6][1] != null) ((TextView)findViewById(R.id.g2)).setText(ganadoresLista[6][1]);
        if(ganadoresLista[7][0] != null) ((TextView)findViewById(R.id.h1)).setText(ganadoresLista[7][0]);
        if(ganadoresLista[7][1] != null) ((TextView)findViewById(R.id.h2)).setText(ganadoresLista[7][1]);
    }

    private void sendDataToFirebase(){
        DatabaseReference resultsDB = FirebaseDatabase.getInstance().getReference().child("resultados");

        List<List<String>> listaGanadores = new ArrayList<>();
        for(String[] grupo:ganadoresLista){
            listaGanadores.add(Arrays.asList(grupo));
        }

        String key = resultsDB.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, listaGanadores);
        resultsDB.updateChildren(childUpdates);
    }

    private boolean validateDataToSend(){
        for(String[] grupo: ganadoresLista){
            for(String equipo: grupo){
                if(equipo == null) return false;
            }
        }
        return true;
    }

    private void loadFromFirebase(){
        DatabaseReference resultsDB = FirebaseDatabase.getInstance().getReference().child("resultados");

        resultsDB.addValueEventListener(new ValueEventListener() {
        //resultsDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot resultados) {
                for (DataSnapshot respuesta: resultados.getChildren()) {
                    System.out.println("-------------Respuesta-------------");
                    for(DataSnapshot grupo: respuesta.getChildren()){
                        System.out.println("------Grupo------");
                        for(DataSnapshot equipo : grupo.getChildren()){
                            String valor = (String)equipo.getValue();
                            System.out.println(valor);
                        }
                        System.out.println("----Fin Grupo----");
                    }
                    System.out.println("-----------Fin Respuesta----------");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
