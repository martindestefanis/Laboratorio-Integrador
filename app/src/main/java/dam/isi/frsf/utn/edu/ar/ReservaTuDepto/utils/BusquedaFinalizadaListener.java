package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils;

import java.util.List;

public interface BusquedaFinalizadaListener<T> {
    public void busquedaFinalizada(List<T> lRes);
    public void busquedaActualizada(String mensaje);
}
