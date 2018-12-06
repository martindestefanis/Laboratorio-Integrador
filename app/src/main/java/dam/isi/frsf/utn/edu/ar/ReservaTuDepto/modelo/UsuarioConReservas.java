package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class UsuarioConReservas {
    @Embedded
    public Usuario usuario;

    @Relation(parentColumn = "ID_USUARIO", entityColumn = "usu_ID_USUARIO", entity = Reserva.class)
    public List<Reserva> reservas;
}
