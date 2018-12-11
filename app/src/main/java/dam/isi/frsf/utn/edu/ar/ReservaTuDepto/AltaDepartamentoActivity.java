package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AltaDepartamentoActivity extends AppCompatActivity {
    private Button btnMapa;
    private EditText edtDireccion;
    private TextView txtCoord;
    private OnNuevoLugarListener listener;

    public interface OnNuevoLugarListener {
        public void obtenerCoordenadas();
    }

    public void setListener(OnNuevoLugarListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_departamento);

        btnMapa = (Button) findViewById(R.id.btnMapa);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenerCoordenadas();

            }
        });


    }

    /*public void obtenerCoordenadas() {
        String tag="mapa";
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment==null) {
            fragment = new MapaFragment();
            ((MapaFragment) fragment).setListener(this);
        }

        Bundle bundle = new Bundle();
        bundle.putInt("tipo_mapa", 1);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenido, fragment,tag)
                .addToBackStack(null)
                .commit();
    }*/
}


