package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.HttpDataHandler;

public class AltaDepartamentoActivity extends AppCompatActivity {
    private Button btnMapa;
    private EditText edtDireccion;
    private TextView txtCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_departamento);

        btnMapa = (Button) findViewById(R.id.btnMapa);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}


