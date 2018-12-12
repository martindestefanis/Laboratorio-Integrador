package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public DepartamentoAdapter(Context contexto, List<Departamento> items) {
        super(contexto, R.layout.fila, items);
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
            row = inflater.inflate(R.layout.fila, parent, false);
        }
        final Departamento departamento = (Departamento) super.getItem(position);
        TextView txtCiudad = (TextView) row.findViewById(R.id.ciudad);
        txtCiudad.setText(departamento.getCiudad().getNombre());
        TextView txtDescripcion = (TextView) row.findViewById(R.id.descripcion);
        txtDescripcion.setText(departamento.getDescripcion());
        TextView txtPrecio = (TextView) row.findViewById(R.id.precio);
        txtPrecio.setText("$" + (df.format(departamento.getPrecio())));
        TextView txtCapacidad = (TextView) row.findViewById(R.id.capacidadMax);
        txtCapacidad.setText(departamento.getCapacidadMaxima()+".");
        return (row);
    }
}
