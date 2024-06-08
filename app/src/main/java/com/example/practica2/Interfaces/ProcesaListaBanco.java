package com.example.practica2.Interfaces;

import android.widget.TextView;

import com.example.practica2.R;
import com.example.practica2.WebServices.Asynchtask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProcesaListaBanco implements Asynchtask {
    TextView txtVis;
    public ProcesaListaBanco(TextView txtVis){
        this.txtVis=txtVis;
    }
    @Override
    public void processFinish(String result) throws JSONException {
        //txtVis.setText("Respuesta:" + result);

        String Turismo="";
        JSONArray JSONlista = new JSONArray(result);
        for(int i=0; i< JSONlista.length();i++){
            JSONObject categ=  JSONlista.getJSONObject(i);
            Turismo += categ.getString("dt_rowid")
                    + "\n" + categ.getString("id")
                    + "\n" + categ.getString("descripcion")
                    + "\n\n" ;


        }
        txtVis.setText(Turismo);

    }
}
