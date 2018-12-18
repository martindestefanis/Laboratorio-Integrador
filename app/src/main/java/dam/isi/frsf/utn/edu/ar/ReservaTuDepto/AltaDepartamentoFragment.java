package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.CiudadDAO;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.DepartamentoDAO;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;

public class AltaDepartamentoFragment extends Fragment {

    private Button btnMapa, btnAceptar, btnCancelar;
    private EditText edtDireccion, edtCiudad, edtPrecio, edtDescripcion, edtCapacidad, edtHabitaciones, edtCamas, edtTelefono;
    private TextView tvCoord;
    private CheckBox chbFumador;
    private OnNuevoLugarListener listener;
    private DepartamentoDAO departamentoDAO;
    private CiudadDAO ciudadDAO;
    private Departamento departamento;
    private Ciudad ciudad;
    private Boolean hayCiudad = false;

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

        getActivity().setTitle("Nuevo departamento");

        btnMapa = (Button) v.findViewById(R.id.btnMapa);
        tvCoord = (TextView) v.findViewById(R.id.textView9);
        edtCiudad = (EditText) v.findViewById(R.id.edtCiudad);
        edtDireccion = (EditText) v.findViewById(R.id.edtDireccion);
        edtPrecio = (EditText) v.findViewById(R.id.edtPrecio);
        edtDescripcion = (EditText) v.findViewById(R.id.edtDescripcion);
        edtCapacidad = (EditText) v.findViewById(R.id.edtCapacidad);
        chbFumador = (CheckBox) v.findViewById(R.id.chbFumador);
        edtHabitaciones = (EditText) v.findViewById(R.id.edtHabitaciones);
        edtCamas = (EditText) v.findViewById(R.id.edtCamas);
        edtTelefono = (EditText) v.findViewById(R.id.edtTelefono);
        btnAceptar = (Button) v.findViewById(R.id.btnAceptar);
        btnCancelar = (Button) v.findViewById(R.id.btnCancelar);

        departamento = new Departamento();
        departamentoDAO = MyDatabase.getInstance(this.getActivity()).getDepartamentoDAO();
        ciudadDAO = MyDatabase.getInstance(this.getActivity()).getCiudadDAO();

        if(getArguments() != null){
            tvCoord.setText(getArguments().getString("latLng", "0;0"));
        }
        else {
            tvCoord.setText("0;0");
        }

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.obtenerCoordenadas();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    departamento.setCantidadCamas(Integer.valueOf(edtCamas.getText().toString()));
                    departamento.setCantidadHabitaciones(Integer.valueOf(edtHabitaciones.getText().toString()));
                    departamento.setCapacidadMaxima(Integer.valueOf(edtCapacidad.getText().toString()));
                    departamento.setDireccion(edtDireccion.getText().toString());
                    departamento.setPrecio(Double.valueOf(edtPrecio.getText().toString()));
                    departamento.setDescripcion(edtDescripcion.getText().toString());
                    departamento.setTelefonoPropietario(edtTelefono.getText().toString());
                    departamento.setNoFumador(chbFumador.isChecked());
                    if (tvCoord.getText().toString().length() > 0 && tvCoord.getText().toString().contains(";")) {
                        String[] coordenadas = tvCoord.getText().toString().split(";");
                        departamento.setLatitud(Double.valueOf(coordenadas[0]));
                        departamento.setLongitud(Double.valueOf(coordenadas[1]));
                    }

                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            List<Ciudad> ciudades = ciudadDAO.getAll();
                            if(!ciudades.isEmpty()){
                                for(Ciudad c : ciudades){
                                    if(c.getNombre().equals(edtCiudad.getText().toString().toUpperCase())){
                                        ciudad = ciudadDAO.buscarPorID(c.getId());
                                        departamento.setCiudad(ciudad);
                                        hayCiudad = true;
                                        break;
                                    }
                                    else {
                                        hayCiudad = false;
                                    }
                                }
                            }
                            else{
                                ciudad = new Ciudad();
                                ciudad.setNombre(edtCiudad.getText().toString().toUpperCase());
                                long id = ciudadDAO.insert(ciudad);
                                ciudad = ciudadDAO.buscarPorID(id);
                                departamento.setCiudad(ciudad);
                            }

                            if(!hayCiudad){
                                ciudad = new Ciudad();
                                ciudad.setNombre(edtCiudad.getText().toString().toUpperCase());
                                long id = ciudadDAO.insert(ciudad);
                                ciudad = ciudadDAO.buscarPorID(id);
                                departamento.setCiudad(ciudad);
                            }

                            departamentoDAO.insert(departamento);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvCoord.setText("");
                                    edtCiudad.setText("");
                                    edtDireccion.setText("");
                                    edtPrecio.setText("");
                                    edtDescripcion.setText("");
                                    edtCapacidad.setText("");
                                    chbFumador.setChecked(false);
                                    edtHabitaciones.setText("");
                                    edtCamas.setText("");
                                    edtTelefono.setText("");
                                }
                            });
                        }
                    };
                    Thread t = new Thread(r);
                    t.start();

                    Toast.makeText(getActivity().getApplicationContext(), "Departamento guardado correctamente", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    private boolean validar(){
        if(tvCoord.getText().toString().equals("0;0")){
            Toast.makeText(getActivity().getApplicationContext(), "Debe seleccionar una ubicación en el mapa", Toast.LENGTH_LONG).show();
            return false;
        }
        if(edtHabitaciones.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar una cantidad de habitaciones", Toast.LENGTH_LONG).show();
            return false;
        }
        if(edtCapacidad.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar una capacidad", Toast.LENGTH_LONG).show();
            return false;
        }
        if(edtPrecio.getText().toString().isEmpty() || Integer.parseInt(edtPrecio.getText().toString()) > 30000){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar un precio y debe ser menor a $30000", Toast.LENGTH_LONG).show();
            return false;
        }
        if(edtCamas.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar una cantidad de camas", Toast.LENGTH_LONG).show();
            return false;
        }
        if(edtDireccion.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar una dirección", Toast.LENGTH_LONG).show();
            return false;
        }
        if(edtCiudad.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar una ciudad", Toast.LENGTH_LONG).show();
            return false;
        }
        if(edtTelefono.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar un teléfono", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
