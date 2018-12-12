package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.BuscarDepartamentosTask;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.DepartamentoDAO;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.FormBusqueda;

public class ListaDepartamentosFragment extends Fragment implements BusquedaFinalizadaListener<Departamento> {

    private TextView tvEstadoBusqueda;
    private ListView listaAlojamientos;
    private DepartamentoAdapter departamentosAdapter;
    private List<Departamento> departamentos;
    private DepartamentoDAO departamentoDAO;

    public ListaDepartamentosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_departamentos, container, false);

        listaAlojamientos = (ListView) v.findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) v.findViewById(R.id.estadoBusqueda);

        departamentoDAO = MyDatabase.getInstance(this.getActivity()).getDepartamentoDAO();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Boolean esBusqueda = false;
        Bundle args = getArguments();
        if(args != null) {
            esBusqueda = args.getBoolean("esBusqueda",false);
        }
        if(esBusqueda){
            FormBusqueda fb = (FormBusqueda) args.getSerializable("frmBusqueda");
            new BuscarDepartamentosTask(ListaDepartamentosFragment.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        }
        else{
            tvEstadoBusqueda.setVisibility(View.GONE);
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    departamentos = departamentoDAO.getAll();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            departamentosAdapter = new DepartamentoAdapter(getActivity().getApplicationContext(), departamentos);
                            listaAlojamientos.setAdapter(departamentosAdapter);
                        }
                    });
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    @Override
    public void busquedaFinalizada(List<Departamento> listaDepartamento) {
        //TODO implementar
    }

    @Override
    public void busquedaActualizada(String msg) {
        tvEstadoBusqueda.setText(" Buscando..."+msg);
    }

}
