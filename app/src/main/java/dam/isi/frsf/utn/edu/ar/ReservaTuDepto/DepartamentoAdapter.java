package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.DepartamentoDAO;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;

public class DepartamentoAdapter extends ArrayAdapter<Departamento> {
    private Context ctx;
    private List<Departamento> datos;
    private DepartamentoDAO departamentoDAO;

    private OnDeptoListener listenerOnDepto;

    public interface OnDeptoListener {
        public void mostrarMapa(int id);
        public void reservar(int id);
    }

    public void setListenerOnDepto(OnDeptoListener listener){
        listenerOnDepto = listener;
    }

    public DepartamentoAdapter(Context contexto, List<Departamento> items) {
        super(contexto, R.layout.fila_depto, items);
        this.ctx = contexto;
        this.datos = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        departamentoDAO = MyDatabase.getInstance(this.ctx).getDepartamentoDAO();
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        DecimalFormat df = new DecimalFormat("#.##");
        View row = convertView;
        if (row == null){
            row = inflater.inflate(R.layout.fila_depto, parent, false);
        }
        final Departamento departamento = (Departamento) super.getItem(position);
        TextView txtCiudad = (TextView) row.findViewById(R.id.ciudad);
        txtCiudad.setText("Ciudad: " + departamento.getCiudad().getNombre());
        TextView txtDescripcion = (TextView) row.findViewById(R.id.descripcion);
        txtDescripcion.setText("Descripción: " + departamento.getDescripcion());
        TextView txtPrecio = (TextView) row.findViewById(R.id.precio);
        txtPrecio.setText("Precio: $" + (df.format(departamento.getPrecio())));
        TextView txtCapacidad = (TextView) row.findViewById(R.id.capacidadMax);
        txtCapacidad.setText("Capacidad: " + departamento.getCapacidadMaxima());
        TextView txtDireccion = (TextView) row.findViewById(R.id.direccion);
        txtDireccion.setText("Dirección: " + departamento.getDireccion());
        TextView txtCantHab = (TextView) row.findViewById(R.id.cantHabitaciones);
        txtCantHab.setText("Cantidad de habitaciones: " + departamento.getCantidadHabitaciones());
        TextView txtCantCamas = (TextView) row.findViewById(R.id.cantCamas);
        txtCantCamas.setText("Cantidad de camas: " + departamento.getCantidadCamas());
        TextView txtFumador = (TextView) row.findViewById(R.id.fumador);
        if(departamento.getNoFumador()) {
            txtFumador.setText("Permitido fumar: Sí");
        }
        else {
            txtFumador.setText("Permitido fumar: No");
        }
        TextView txtTelProp = (TextView) row.findViewById(R.id.telProp);
        txtTelProp.setText("Teléfono propietario: " + departamento.getTelefonoPropietario());
        Button btnVerEnMapa = (Button) row.findViewById(R.id.btnVerEnMapa);
        btnVerEnMapa.setTag(departamento.getId());
        btnVerEnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.valueOf(view.getTag().toString());
                listenerOnDepto.mostrarMapa(id);
            }
        });
        Button btnReservar = (Button) row.findViewById(R.id.btnReservar);
        btnReservar.setTag(departamento.getId());
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.valueOf(view.getTag().toString());
                listenerOnDepto.reservar(id);
            }
        });

        return (row);
    }
}
