package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AltaDepartamentoFragment extends Fragment {

    private Button btnMapa;
    private EditText edtDireccion;
    private TextView tvCoord;
    private OnNuevoLugarListener listener;

    public interface OnNuevoLugarListener {
        public void obtenerCoordenadas();
    }

    public void setListener(OnNuevoLugarListener listener) {
        this.listener = listener;
    }

    public AltaDepartamentoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alta_departamento, container, false);

        btnMapa = (Button) v.findViewById(R.id.btnMapa);
        edtDireccion = (EditText) v.findViewById(R.id.edtDireccion);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.obtenerCoordenadas();
            }
        });

        return v;
    }

}
