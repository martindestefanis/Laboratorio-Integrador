package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils;

import java.util.List;

/**
 * Created by martdominguez on 22/09/2016.
 */
public interface BusquedaFinalizadaListener<T> {
    public void busquedaFinalizada(List<T> lRes);
    public void busquedaActualizada(String mensaje);
}
