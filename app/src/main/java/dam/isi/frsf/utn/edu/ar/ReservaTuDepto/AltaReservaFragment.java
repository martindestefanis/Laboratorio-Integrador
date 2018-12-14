package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.ReservaDAO;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Usuario;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.UsuarioDAO;

public class AltaReservaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private Departamento selected;
    private Button btnReserva, btnFechaInicio, btnFechaFin;
    private Boolean esFechaInicio = true;
    private Reserva unaReserva;
    private Usuario unUsuario;
    private ReservaDAO reservaDAO;
    private UsuarioDAO usuarioDAO;

    public AltaReservaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alta_reserva, container, false);
        Bundle argumentos = getArguments();
        selected = (Departamento) argumentos.getSerializable("departamentoSeleccionado");

        btnReserva = (Button) v.findViewById(R.id.makeReservationButton);
        btnFechaInicio = (Button) v.findViewById(R.id.btnFechaInicio);
        btnFechaFin = (Button) v.findViewById(R.id.btnFechaFin);

        reservaDAO = MyDatabase.getInstance(this.getActivity()).getReservaDAO();
        usuarioDAO = MyDatabase.getInstance(this.getActivity()).getUsuarioDAO();

        unaReserva = new Reserva();

        //Toast.makeText(getContext(), "ATENCIÓN: Para poder realizar una reserva primero debe configurar su email y nombre de usuario en su perfil.", Toast.LENGTH_LONG).show();

        btnFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esFechaInicio = false;

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(AltaReservaFragment.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );

                dpd.setTitle("Ingrese la fecha de fin de la reserva");
                dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");

            }
        });

        btnFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esFechaInicio = true;

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(AltaReservaFragment.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );

                dpd.setTitle("Ingrese la fecha de inicio de la reserva");
                dpd.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                final String email = prefs.getString("correo_preference","");
                final String nombre = prefs.getString("nombre_preference", "");

                if(validarPreferencias(email, nombre)){
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            unUsuario = usuarioDAO.buscarPorCorreoNombre(email, nombre);
                            if(unUsuario == null){
                                Usuario nuevoUsuario = new Usuario();
                                nuevoUsuario.setCorreo(email);
                                nuevoUsuario.setNombre(nombre);
                                usuarioDAO.insert(nuevoUsuario);


                                unaReserva.setUsuario(nuevoUsuario);
                            }
                            else{
                                unaReserva.setUsuario(unUsuario);
                            }
                            unaReserva.setDepartamento(selected);
                            unaReserva.setPrecio(selected.getPrecio());
                            unaReserva.setEstado(Reserva.Estado.REALIZADO);
                            reservaDAO.insert(unaReserva);
                            List<Reserva> lista = reservaDAO.getAll();
                            for(Reserva re :lista) {
                                if (re.getEstado().equals(Reserva.Estado.REALIZADO)) {
                                    re.setEstado(Reserva.Estado.PENDIENTE);
                                    reservaDAO.update(re);
                                    Intent intent = new Intent(getActivity().getApplicationContext(), EstadoPedidoReceiver.class);
                                    intent.setAction(EstadoPedidoReceiver.ESTADO_PENDIENTE);
                                    intent.putExtra("idReserva", re.getId());
                                    getActivity().getApplicationContext().sendBroadcast(intent);
                                    getActivity().onBackPressed();
                                }
                            }
                        }
                    };
                    Thread t = new Thread(r);
                    t.start();
                }
                else{
                    Toast.makeText(getContext(), "Error: Para poder realizar una reserva primero debe configurar su email y nombre de usuario en su perfil.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(esFechaInicio){
            Date fecha = new Date();
            fecha.setYear(year);
            fecha.setMonth(monthOfYear);
            fecha.setDate(dayOfMonth);
            unaReserva.setFechaInicio(fecha);
            Log.d("fecha", "inicio: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
        else{
            Date fecha = new Date();
            fecha.setYear(year);
            fecha.setMonth(monthOfYear);
            fecha.setDate(dayOfMonth);
            unaReserva.setFechaFin(fecha);
            //Log.d("fecha", "fin: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
            Log.d("fecha", "fin: "+fecha.getDate()+"/"+fecha.getMonth()+"/"+fecha.getYear());
        }
    }

    public boolean validarPreferencias(String email, String nombre){
        boolean hayPrefs = false;

        if(!email.equals("") || !nombre.equals("")){
            hayPrefs = true;
        }

        return hayPrefs;
    }

    /*public Calendar[] calendarioValido(){
        Calendar[] calendario = new Calendar[];

        return calendario;
    }*/

    private static List<Date> getDates(Date date1, Date date2) {
        ArrayList<Date> dates = new ArrayList<Date>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
