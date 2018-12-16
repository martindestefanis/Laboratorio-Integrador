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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.UsuarioConReservas;
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
                //TODO:ACA SE LLAMA
               // List<Calendar[]> disabledDays1 = calendarDeFechasADeshabilitar();
                //dpd.setDisabledDays(disabledDays1.get(0));

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
                            Log.d("fechaReservaI",unaReserva.getFechaInicio().toString());
                            Log.d("fechaReservaF",unaReserva.getFechaFin().toString());
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
            fecha.setYear(year-1900);
            fecha.setMonth(monthOfYear);
            fecha.setDate(dayOfMonth);
            unaReserva.setFechaInicio(fecha);
            Log.d("fecha", "inicio: "+fecha.getDate()+"/"+fecha.getMonth()+"/"+fecha.getYear());
        }
        else{
            Date fecha = new Date();
            fecha.setYear(year-1900);
            fecha.setMonth(monthOfYear);
            fecha.setDate(dayOfMonth);
            unaReserva.setFechaFin(fecha);
            Log.d("fecha", "fin: "+fecha.getDate()+"/"+fecha.getMonth()+"/"+fecha.getYear());
        }
    }

    public boolean validarPreferencias(String email, String nombre){
        boolean hayPrefs = false;

        if(!email.equals(" ") || !nombre.equals(" ")){
            hayPrefs = true;
        }

        return hayPrefs;
    }


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
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    /*private List<Calendar[]> calendarDeFechasADeshabilitar(){
        Calendar calendar1 = Calendar.getInstance();
        List<Reserva> listaReservas = new ArrayList<>();
        List<UsuarioConReservas> listaUsuarioConReservas = new List<UsuarioConReservas>;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                List<UsuarioConReservas> listaUsuarioConReservas = usuarioDAO.buscarUsuarioConReservas();
            }
        };
        Thread t = new Thread(r);
        t.start();

        for(UsuarioConReservas listUsrConRes : listaUsuarioConReservas){
            listaReservas = listUsrConRes.reservas;
        }
        List<Date> listaDates = new ArrayList<Date>();
        List<Calendar[]> disabledDays1 = new ArrayList<Calendar[]>();

        for(Reserva reserva:listaReservas){
            if(reserva.getDepartamento().getId().equals(selected.getId())){
                listaDates = getDates(reserva.getFechaInicio(),reserva.getFechaFin());
                for(Date date:listaDates){
                    calendar1 = dateToCalendar(date);
                    List<Calendar> listaCalendar = new ArrayList<>();
                    listaCalendar.add(calendar1);
                    disabledDays1.add(listaCalendar.toArray(new Calendar[listaCalendar.size()]));
                   // Calendar[] disabledDays1 = listaCalendar.toArray(new Calendar[listaCalendar.size()]);
                }
            }
        }

        /* //TODO:PROBANDO LO DE LAS FECHAS DISABLE
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] a = {"20/12/2018","21/12/2018","22/12/2018"};
        java.util.Date date = null;
        for (int i = 0;i < a.length; i++) {

            try {
                date = sdf.parse(a[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar1 = dateToCalendar(date);
            System.out.println(calendar1.getTime());

            List<Calendar> listaCalendar = new ArrayList<>();
            listaCalendar.add(calendar1);
            Calendar[] disabledDays1 = listaCalendar.toArray(new Calendar[listaCalendar.size()]);
            dpd.setDisabledDays(disabledDays1);
        }
        //TODO: ACÁ TERMINA
        */

      /* return disabledDays1;*/
    }

//}
