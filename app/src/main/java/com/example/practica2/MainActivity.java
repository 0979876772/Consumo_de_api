package com.example.practica2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.practica2.Interfaces.ProcesaListaBanco;
import com.example.practica2.WebServices.Asynchtask;
import com.example.practica2.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask {
    TextView txtVis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtVis = (TextView) findViewById(R.id.txtVis);
    }


     public void btnEnviarWs(View view){
        TextView txtUsuario=(TextView) findViewById(R.id.txtUsuario);
        TextView txtClaves=(TextView) findViewById(R.id.txtClave);

         Map<String, String> datos = new HashMap<String, String>();
         WebService ws= new WebService(
                 "https://revistas.uteq.edu.ec/ws/login.php?"
                         + "usr=" +txtUsuario.getText().toString()
                         + "&pass=" + txtClaves.getText().toString(),
                 datos, MainActivity.this, MainActivity.this);
         ws.execute("GET");

     }
    @Override
    public void processFinish(String result) throws JSONException {

        txtVis.setText(result);

    }

    public void btnIngreVolly(View view){
        TextView txtUsuario=(TextView) findViewById(R.id.txtUsuario);
        TextView txtClaves=(TextView) findViewById(R.id.txtClave);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://revistas.uteq.edu.ec/ws/login.php?" +
                "usr=" +txtUsuario.getText().toString()
                + "&pass=" + txtClaves.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        txtVis.setText("Respuesta: "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        txtVis.setText("That didn't work!");
                    }
                });
        queue.add(stringRequest);

    }

    public void getListaBancos(View vies){

        Map<String, String> datos = new HashMap<String, String>();

        WebService ws= new
                WebService("https://api-uat.kushkipagos.com/transfer/v1/bankList",
                datos, MainActivity.this, new ProcesaListaBanco(txtVis));

        ws.execute("GET","Public-Merchant-Id","84e1d0de1fbf437e9779fd6a52a9ca18");
    }
}