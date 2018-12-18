package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.UsuarioConReservas;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.UsuarioDAO;

public class ListaReservasFragment extends Fragment {

    private TextView tvEstadoBusqueda;
    private ListView listarReservas;
    private ReservaAdapter reservaAdapter;
    private UsuarioConReservas usuarioConReservas;
    private ReservaDAO reservaDAO;
    private UsuarioDAO usuarioDAO;
    private List<Reserva> unaReserva;

    public ListaReservasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista_reservas, container, false);

        getActivity().setTitle("Lista de reservas");

        listarReservas = (ListView) v.findViewById(R.id.listarReservas);
        tvEstadoBusqueda = (TextView) v.findViewById(R.id.estadoBusquedaReserva);
        reservaDAO = MyDatabase.getInstance(this.getActivity()).getReservaDAO();
        usuarioDAO = MyDatabase.getInstance(this.getActivity()).getUsuarioDAO();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Bundle argumentos = getArguments();
                if(argumentos != null) {
                    int idReserva = argumentos.getInt("idReserva",0);
                    unaReserva = reservaDAO.buscarPorIDLista(idReserva);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reservaAdapter = new ReservaAdapter(getActivity().getApplicationContext(), unaReserva);
                            listarReservas.setAdapter(reservaAdapter);
                        }
                    });
                }
                else{
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String email = prefs.getString("correo_preference","");
                    String nombre = prefs.getString("nombre_preference", "");
                    usuarioConReservas = usuarioDAO.buscarUsuarioConReservas(email, nombre);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(usuarioConReservas == null || usuarioConReservas.reservas.isEmpty()) {
                                tvEstadoBusqueda.setText("No se encontraron resultados");
                            }
                            else {
                                tvEstadoBusqueda.setVisibility(View.GONE);
                                reservaAdapter = new ReservaAdapter(getActivity().getApplicationContext(), usuarioConReservas.reservas);
                                listarReservas.setAdapter(reservaAdapter);
                            }
                        }
                    });
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
