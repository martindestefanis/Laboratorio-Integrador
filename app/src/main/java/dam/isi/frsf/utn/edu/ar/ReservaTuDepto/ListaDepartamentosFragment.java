package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

//import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.BuscarDepartamentosTask;
import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.BuscarDepartamentosTask;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils.FormBusqueda;

public class ListaDepartamentosFragment extends Fragment implements BusquedaFinalizadaListener<Departamento> {

    private TextView tvEstadoBusqueda;
    private ListView listaAlojamientos;
    private DepartamentoAdapter departamentosAdapter;
    private List<Departamento> lista;

    public ListaDepartamentosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_departamentos, container, false);

        lista = new ArrayList<>();
        listaAlojamientos = (ListView) v.findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) v.findViewById(R.id.estadoBusqueda);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*Intent intent = getIntent();
        Boolean esBusqueda = intent.getExtras().getBoolean("esBusqueda");
        if(esBusqueda){
            FormBusqueda fb = (FormBusqueda ) intent.getSerializableExtra("frmBusqueda");
            //new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        }else{
            tvEstadoBusqueda.setVisibility(View.GONE);
            //lista=Departamento.getAlojamientosDisponibles();
        }
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);
        listaAlojamientos.setAdapter(departamentosAdapter);*/

        Boolean esBusqueda = false;
        Bundle args = getArguments();
        if(args != null) {
            esBusqueda = args.getBoolean("esBusqueda",false);
        }
        /*if(esBusqueda){
            FormBusqueda fb = (FormBusqueda ) intent.getSerializableExtra("frmBusqueda");
            new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        }else{
            tvEstadoBusqueda.setVisibility(View.GONE);
            lista=Departamento.getAlojamientosDisponibles();
        }
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);
        listaAlojamientos.setAdapter(departamentosAdapter);*/
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
