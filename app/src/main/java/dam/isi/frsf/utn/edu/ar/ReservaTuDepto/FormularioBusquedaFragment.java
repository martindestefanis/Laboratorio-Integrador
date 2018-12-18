package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.CiudadDAO;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.FormBusqueda;

public class FormularioBusquedaFragment extends Fragment {

    private Button btnBuscar;
    private Spinner cmbCiudad;
    private ArrayAdapter<Ciudad> adapterCiudad;
    private SeekBar skPrecioMin;
    private TextView tvPrecioMinimo;
    private TextView tvPrecioMaximo;
    private SeekBar skPrecioMax;
    private EditText txtHuespedes;
    private Switch swFumadores;
    private FormBusqueda frmBusq;
    private CiudadDAO ciudadDAO;
    private List<Ciudad> ciudades;

    public FormularioBusquedaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_formulario_busqueda, container, false);

        getActivity().setTitle("ReservaTuDepto");

        ciudadDAO = MyDatabase.getInstance(getContext()).getCiudadDAO();

        frmBusq = new FormBusqueda();
        txtHuespedes = (EditText) v.findViewById(R.id.cantHuespedes);
        skPrecioMin = (SeekBar) v.findViewById(R.id.precioMin);
        skPrecioMin.setOnSeekBarChangeListener(listenerSB);

        skPrecioMax = (SeekBar) v.findViewById(R.id.precioMax);
        skPrecioMax.setOnSeekBarChangeListener(listenerSB);

        swFumadores = (Switch) v.findViewById(R.id.aptoFumadores);

        cmbCiudad = (Spinner) v.findViewById(R.id.comboCiudad);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ciudades = ciudadDAO.getAll();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterCiudad = new ArrayAdapter<Ciudad>(getContext(), android.R.layout.simple_spinner_item, ciudades);
                        cmbCiudad.setAdapter(adapterCiudad);
                        cmbCiudad.setOnItemSelectedListener(comboListener);
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();

        tvPrecioMinimo = (TextView) v.findViewById(R.id.txtPrecioMin);
        tvPrecioMaximo = (TextView) v.findViewById(R.id.txtPrecioMax);

        btnBuscar = (Button) v.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(btnBusarListener);

        return v;
    }

    private View.OnClickListener btnBusarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(swFumadores.isChecked()){
                frmBusq.setPermiteFumar(true);
                Log.d("asd", "fumar: si");
            }else{
                frmBusq.setPermiteFumar(false);
                Log.d("asd", "fumar: no");
            }
            Integer huespedes = null;
            try {
                huespedes = Integer.parseInt(txtHuespedes.getText().toString());
            } catch (NumberFormatException e) {
                huespedes = null;
            }
            frmBusq.setHuespedes(huespedes);
            Fragment f = new ListaDepartamentosFragment();
            Bundle args = new Bundle();
            args.putBoolean("esBusqueda", true);
            args.putSerializable("frmBusqueda", frmBusq);
            f.setArguments(args);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenido, f)
                    .addToBackStack("@string/tagformBusqueda")
                    .commit();
        }
    };

    private AdapterView.OnItemSelectedListener comboListener = new  AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Ciudad item = (Ciudad) parent.getItemAtPosition(pos);
            frmBusq.setCiudad(item);
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private SeekBar.OnSeekBarChangeListener listenerSB =  new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(seekBar.getId()==R.id.precioMin) {
                tvPrecioMinimo.setText("Precio Minimo: $" + progress);
                frmBusq.setPrecioMinimo(Double.valueOf(progress));
            }
            if(seekBar.getId()==R.id.precioMax) {
                tvPrecioMaximo.setText("Precio Maximo: $" + progress);
                frmBusq.setPrecioMaximo(Double.valueOf(progress));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
}
