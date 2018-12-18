package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;

public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    private BusquedaFinalizadaListener<Departamento> listener;
    private List<Departamento> todos;

    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener, List<Departamento> departamentos) {
        this.listener = dListener;
        this.todos = departamentos;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Departamento> departamentos) {
        listener.busquedaFinalizada(departamentos);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada("departamento " + values[0]);
    }

    @Override
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {
        Log.i("size departamentos", todos.size() + "");
        List<Departamento> resultado = new ArrayList<Departamento>();
        if(busqueda[0].getCiudad()==null) {
            return resultado;
        }else{
            FormBusqueda busquedaActual = busqueda[0];

            for(Departamento depto : todos){
                if ( busquedaActual.getHuespedes() != null && busquedaActual.getHuespedes() > depto.getCapacidadMaxima())
                    continue;
                if ( busquedaActual.getPrecioMinimo() != null && busquedaActual.getPrecioMinimo() > depto.getPrecio())
                    continue;
                if ( busquedaActual.getPrecioMaximo() != null && busquedaActual.getPrecioMaximo() < depto.getPrecio())
                    continue;
                if ( busquedaActual.getCiudad() != null && !busquedaActual.getCiudad().equals(depto.getCiudad()))
                    continue;
                if ( busquedaActual.getPermiteFumar() && depto.getNoFumador())
                    continue;
                resultado.add(depto);
            }
        }
        return resultado;
    }
}

