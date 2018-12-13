package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Reserva;


public class ReservaAdapter extends ArrayAdapter<Reserva> {
    private LayoutInflater inflater;
    private Context contexto;

    public ReservaAdapter(Context contexto, List<Reserva> items) {
        super(contexto, R.layout.fila_reservas, items);
        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

        View row = convertView;
        if (row == null) row = inflater.inflate(R.layout.fila_reservas, parent, false);

        Reserva reserva = this.getItem(position);

        Log.d("fechaFinAdapter: ", dt.format(reserva.getFechaFin()));
        Log.d("fechaInicioAdapter: ", dt.format(reserva.getFechaFin()));

        TextView fechaFinTextView = (TextView) row.findViewById(R.id.fechaFinTextView);
        fechaFinTextView.setText("Fin: " + dt.format(reserva.getFechaFin()));
        TextView fechaInicioTextView = (TextView) row.findViewById(R.id.fechaInicioTextView);
        fechaInicioTextView.setText("Inicio: " + dt.format(reserva.getFechaInicio()));
        TextView departamentoTextView = (TextView) row.findViewById(R.id.departamentoTextView);
        departamentoTextView.setText("Ciudad: "+ reserva.getDepartamento().getCiudad().getNombre());
        TextView precioTextView = (TextView) row.findViewById(R.id.precioTextView);
        precioTextView.setText("Precio: " + df.format(reserva.getPrecio()));
        CheckBox confirmadaCheckBox = (CheckBox) row.findViewById(R.id.confirmadaCheckBox);
        confirmadaCheckBox.setChecked(reserva.getConfirmada());

        return (row);
    }
}