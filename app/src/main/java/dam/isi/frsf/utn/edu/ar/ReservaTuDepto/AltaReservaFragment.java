package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;

public class AltaReservaFragment extends Fragment  {
    private Departamento selected;
    private DatePicker startDatePicker, endDatePicker;

    public AltaReservaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alta_reserva, container, false);
        Bundle argumentos = getArguments();
        selected = (Departamento) argumentos.getSerializable("departamentoSeleccionado");

        startDatePicker = (DatePicker) v.findViewById(R.id.startDatePicker);
        endDatePicker = (DatePicker) v.findViewById(R.id.endDatePicker);
        Button makeReservationButton = (Button) v.findViewById(R.id.makeReservationButton);

        makeReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date startDate = new Date(startDatePicker.getYear(), startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
                Date endDate = new Date(endDatePicker.getYear(), endDatePicker.getMonth(), endDatePicker.getDayOfMonth());



            }
        });

        return v;
    }
}
