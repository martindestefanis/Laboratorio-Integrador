package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.ReservaDAO;

public class ListaReservasFragment extends Fragment {

    private TextView tvEstadoBusqueda;
    private ListView listarReservas;
    private ReservaAdapter reservaAdapter;
    private List<Reserva> lista;
    private ReservaDAO reservaDAO;

    public ListaReservasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista_reservas, container, false);

        listarReservas = (ListView) v.findViewById(R.id.listarReservas);
        tvEstadoBusqueda = (TextView) v.findViewById(R.id.estadoBusquedaReserva);
        reservaDAO = MyDatabase.getInstance(this.getActivity()).getReservaDAO();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                lista = reservaDAO.getAll();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(lista.isEmpty()) {
                            tvEstadoBusqueda.setText("No se encontraron resultados");
                        }
                        else {
                            tvEstadoBusqueda.setVisibility(View.GONE);
                            Runnable r1 = new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            reservaAdapter = new ReservaAdapter(getActivity().getApplicationContext(), lista);
                                            listarReservas.setAdapter(reservaAdapter);
                                        }
                                    });
                                }
                            };
                            Thread t1 = new Thread(r1);
                            t1.start();
                        }
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
