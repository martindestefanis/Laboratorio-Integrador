package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    private BusquedaFinalizadaListener<Departamento> listener;

    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener){
        this.listener = dListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Departamento> departamentos) {
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada("departamento "+values[0]);


    }

    @Override
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {
        List<Departamento> todos = Departamento.getAlojamientosDisponibles();
        List<Departamento> resultado = new ArrayList<Departamento>();
        int contador = 0;
        Ciudad ciudadBuscada = busqueda[0].getCiudad();
        // TODO implementar: buscar todos los departamentos del sistema e ir chequeando las condiciones 1 a 1.
        // si cumplen las condiciones agregarlo a los resultados.
        return resultado;
    }
}
